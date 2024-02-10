/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webserviceVoiture.webserviceVoiture.service;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.webserviceVoiture.webserviceVoiture.repository.AdRepository;
import com.webserviceVoiture.webserviceVoiture.repository.CategoryRepository;
import com.webserviceVoiture.webserviceVoiture.repository.ImageRepository;
import com.webserviceVoiture.webserviceVoiture.voiture_model.Ad;
import com.webserviceVoiture.webserviceVoiture.voiture_model.Category;
import com.webserviceVoiture.webserviceVoiture.voiture_model.CategoryStatistics;
import com.webserviceVoiture.webserviceVoiture.voiture_model.Image;
import com.webserviceVoiture.webserviceVoiture.voiture_model.ResponseMessage;
import jakarta.servlet.ServletContext;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author TOAVINA
 */

@Service
public class AdService {
    @Autowired
    private AdRepository adRepository;

    @Autowired
    private ImageRepository imageRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private ServletContext servletContext;
    
    

    public void createAd(Ad ad, List<MultipartFile> files) throws IOException {
        List<Image> images = uploadPhotos(files);
        adRepository.save(ad);
        for(Image image: images){
            image.setAnnonce(ad);
        }
        imageRepository.saveAll(images);
        ad.setImages(images);
    }
    
    public List<Ad> getAllAds() {
        return adRepository.findAll();
    }
    
    public Page<Ad> getAllAdsPaginated(int page) {
        Pageable pageable = PageRequest.of(page - 1, 5); // Affiche 5 annonces par page, page - 1 car la pagination commence à 0
        return adRepository.findAll(pageable);
    }
    
    public Page<Ad> getAdsByUserIdPaginated(long userId, int page) {
        Pageable pageable = PageRequest.of(page - 1, 5); // 5 annonces par page
        return adRepository.findAllByUtilisateurId(userId, pageable);
    }
    
    public Page<Ad> getAdsByStatus(int status, int page) {
        Pageable pageable = PageRequest.of(page - 1, 5);
        return adRepository.findAllByStatut(status, pageable);
    }
    
    public Page<Ad> getAdsByUserIdAndStatus(long userId, int status, int page) {
        Pageable pageable = PageRequest.of(page - 1, 5); // 5 annonces par page
        return adRepository.findAllByUtilisateurIdAndStatut(userId, status, pageable);
    }
    
    //recherche multicriteres
    public Page<Ad> searchAdsByCriteria(Long categoryId, Long brandId, Double minPrice, Double maxPrice, Double minKilometrage, Double maxKilometrage, int page) {
        Pageable pageable = PageRequest.of(page - 1, 10); // 10 annonces par page
        return adRepository.searchAdsByCriteria(categoryId, brandId, minPrice, maxPrice, minKilometrage, maxKilometrage, pageable);
    }
    
    public List<Image> uploadPhotos(List<MultipartFile> files) throws IOException {
        List<Image> images = new ArrayList<>();
        for (MultipartFile file : files) {
            String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            File fichier = this.convertToFile(file, uniqueFileName);     
            String fileUri = saveFile(fichier, uniqueFileName);
            Image image = new Image();
            image.setChemin(fileUri);
            images.add(image);
        }
        return images;
    }

    private String saveFile(File file, String fileName) throws IOException {
        BlobId blobId = BlobId.of("voiture-adb3e.appspot.com", fileName); // Replace with your bucker name
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
        InputStream inputStream = AdService.class.getClassLoader().getResourceAsStream("voiture-adb3e-firebase-adminsdk-xy24c-df550d9160.json"); // change the file name with your one
        Credentials credentials = GoogleCredentials.fromStream(inputStream);
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));

        String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/voiture-adb3e.appspot.com/o/%s?alt=media";
        return String.format(DOWNLOAD_URL, URLEncoder.encode(fileName, StandardCharsets.UTF_8));
    }
    
    
    private File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {
      File tempFile = new File(fileName);
      try (FileOutputStream fos = new FileOutputStream(tempFile)) {
          fos.write(multipartFile.getBytes());
          fos.close();
      }
      return tempFile;
    }
    

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    public ResponseEntity<?> markAd(Long adId,int valeur) {
        try {
            Ad ad = adRepository.findById(adId).orElseThrow(() -> new RuntimeException("Annonce non trouvée"));
            ad.setStatut(valeur);
            
            if(valeur == 2){
                ad.setDateVente(LocalDateTime.now());
            }
            
            if(valeur == 3){
                ad.setDateAcceptation(LocalDateTime.now());
            }
            
            adRepository.save(ad);
            return ResponseEntity.ok(new ResponseMessage("vendu", "Voiture vendue avec succès."));
        } catch (Exception e) {
            System.out.println("ito ny erreur a   : "+e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("error", e.getMessage()));
        }
    }
    
    public List<CategoryStatistics> getSalesStatistics() {
        List<CategoryStatistics> statistics = new ArrayList<>();

        List<Category> categories = categoryRepository.findAll();
        for (Category category : categories) {
            long numberOfCarsSold = adRepository.countByModeleAndStatut(category, 3);
            double percentage = calculatePercentage(numberOfCarsSold);

            CategoryStatistics categoryStatistics = new CategoryStatistics();
            categoryStatistics.setCategoryName(category.getNom());
            categoryStatistics.setNumberOfCarsSold(numberOfCarsSold);
            categoryStatistics.setPercentage(percentage);

            statistics.add(categoryStatistics);
        }

        return statistics;
    }

    private double calculatePercentage(long numberOfCarsSold) {
        long totalCars = adRepository.countByStatut(3);
        if (totalCars == 0) {
            return 0.0;
        }
        return (double) numberOfCarsSold / totalCars * 100;
    }
    
    public long getTotalCarsSold() {
        return adRepository.countByStatut(3);
    }

    
}