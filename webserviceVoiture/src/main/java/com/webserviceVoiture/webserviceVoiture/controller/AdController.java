/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webserviceVoiture.webserviceVoiture.controller;

import com.webserviceVoiture.webserviceVoiture.service.AdService;
import com.webserviceVoiture.webserviceVoiture.service.BrandService;
import com.webserviceVoiture.webserviceVoiture.service.CategoryService;
import com.webserviceVoiture.webserviceVoiture.service.UserService;
import com.webserviceVoiture.webserviceVoiture.voiture_model.Ad;
import com.webserviceVoiture.webserviceVoiture.voiture_model.CustomResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author TOAVINA
 */

@RestController
@RequestMapping("/api")
public class AdController {

    @Autowired
    private AdService adService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private BrandService brandService;

    @PostMapping("/ads")
    public ResponseEntity<CustomResponse<Ad>> createAd(@RequestParam("userId") String userId,
                                                       @RequestParam("modeleId") Long modeleId,
                                                       @RequestParam("marqueId") Long marqueId,
                                                       @RequestParam("prix") double prix,
                                                       @RequestParam("kilometrage") double kilometrage,
                                                       @RequestParam("annee") int annee,
                                                       @RequestParam("description") String description,
                                                       @RequestParam("images") List<MultipartFile> images) {
        try {
            Ad ad = new Ad();
            ad.setUtilisateur(userService.findById(userId));
            ad.setModele(categoryService.findById(modeleId));
            ad.setMarque(brandService.findById(modeleId));
            ad.setPrix(prix);
            ad.setAnnee(annee);
            ad.setKilometrage(kilometrage);
            ad.setDescription(description);
            adService.createAd(ad, images);

            CustomResponse<Ad> response = new CustomResponse<>();
            response.setStatus("added");
            response.setMessage("Ad created successfully.");
            response.setData(ad);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            CustomResponse<Ad> response = new CustomResponse<>();
            response.setStatus("error");
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
     
    @GetMapping("/findAds/{page}")
    public ResponseEntity<Page<Ad>> getAllAds(@PathVariable int page) {
        Page<Ad> ads = adService.getAllAdsPaginated(page);
        return new ResponseEntity<>(ads, HttpStatus.OK);
    }
    
    @GetMapping("/findAdsByUserId/{userId}/{page}")
    public ResponseEntity<Page<Ad>> getAdsByUserId(@PathVariable long userId, @PathVariable int page) {
        Page<Ad> ads = adService.getAdsByUserIdPaginated(userId, page);
        return new ResponseEntity<>(ads, HttpStatus.OK);
    }
    
    @GetMapping("/findAdsByStatus/{status}/{page}")
    public ResponseEntity<Page<Ad>> getAdsByStatus(@PathVariable int status, @PathVariable int page) {
        Page<Ad> ads = adService.getAdsByStatus(status, page);
        return new ResponseEntity<>(ads, HttpStatus.OK);
    }
    
    @GetMapping("/findAdsByUserIdAndStatus/{userId}/{status}/{page}")
    public ResponseEntity<Page<Ad>> getAdsByUserIdAndStatus(@PathVariable long userId, @PathVariable int status, @PathVariable int page) {
        Page<Ad> ads = adService.getAdsByUserIdAndStatus(userId, status, page);
        return new ResponseEntity<>(ads, HttpStatus.OK);
    }
    
    @PutMapping("/markAdAsSold/{adId}")
    public ResponseEntity<?> markAdAsSold(@PathVariable Long adId) {
        return adService.markAd(adId,3);
    }
    
    //recherche multicriteres
    @GetMapping("/search")
    public ResponseEntity<Page<Ad>> searchAdsByCriteria(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long brandId,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Double minKilometrage,
            @RequestParam(required = false) Double maxKilometrage,
            @RequestParam(defaultValue = "1") int page) {

        Page<Ad> ads = adService.searchAdsByCriteria(categoryId, brandId, minPrice, maxPrice, minKilometrage, maxKilometrage, page);
        return new ResponseEntity<>(ads, HttpStatus.OK);
    }
}

