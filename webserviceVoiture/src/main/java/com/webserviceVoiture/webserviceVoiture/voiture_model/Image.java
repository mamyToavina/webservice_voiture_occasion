/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webserviceVoiture.webserviceVoiture.voiture_model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 *
 * @author TOAVINA
 */
@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String chemin;
    
    @ManyToOne
    @JoinColumn(name = "ad_id")
    @JsonIgnore
    private Ad annonce;

    public Image() {
    }

    public Image(Long id, String chemin, Ad annonce) {
        this.id = id;
        this.chemin = chemin;
        this.annonce = annonce;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChemin() {
        return chemin;
    }

    public void setChemin(String chemin) {
        this.chemin = chemin;
    }

    public Ad getAnnonce() {
        return annonce;
    }

    public void setAnnonce(Ad annonce) {
        this.annonce = annonce;
    }
}

