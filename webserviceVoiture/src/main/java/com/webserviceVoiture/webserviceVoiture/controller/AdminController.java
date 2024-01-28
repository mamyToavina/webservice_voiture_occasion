/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webserviceVoiture.webserviceVoiture.controller;

import com.webserviceVoiture.webserviceVoiture.service.AdService;
import com.webserviceVoiture.webserviceVoiture.voiture_model.CategoryStatistics;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author TOAVINA
 */

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdService adService;
    
    @PutMapping("/markAdAsValid/{adId}")
    public ResponseEntity<?> markAdAsValid(@PathVariable Long adId) {
        return adService.markAd(adId,2);
    }
    
    @GetMapping("/statistics")
    public ResponseEntity<Object> getSalesStatistics() {
        List<CategoryStatistics> statistics = adService.getSalesStatistics();
        long totalCarsSold = adService.getTotalCarsSold();
        return ResponseEntity.ok().body(prepareResponse(statistics, totalCarsSold));
    }

    private Object prepareResponse(List<CategoryStatistics> statistics, long totalCarsSold) {
        Map<String, Object> response = new HashMap<>();
        response.put("title", "Statistique par catégorie vendue");
        response.put("totalCarsSold", totalCarsSold);
        response.put("data", statistics);
        return response;
    }
}
