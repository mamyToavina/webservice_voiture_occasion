/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webserviceVoiture.webserviceVoiture.service;

import com.webserviceVoiture.webserviceVoiture.repository.BrandRepository;
import com.webserviceVoiture.webserviceVoiture.voiture_model.Brand;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author TOAVINA
 */

@Service
public class BrandService {
    
    @Autowired
    private BrandRepository brand_repo;
    
    
    public Brand findById(Long id){
        try {
            Optional<Brand> brandOptional =  brand_repo.findById(id);
            return brandOptional.orElse(null);
        } catch (NumberFormatException e) {
            System.out.println("L'ID n'est pas un nombre valide : " + id);
            return null;
        }
    }
    
    public void createBrand(Brand brand) {
        brand_repo.save(brand);
    }

    public void deleteBrand(Long brandId) {
        brand_repo.deleteById(brandId);
    }

    public List<Brand> getAllBrands() {
        return brand_repo.findAll();
    }

    public void updateBrand(Long brandId, Brand brand) {
        brand.setId(brandId);
        brand_repo.save(brand);
    }
}
