/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webserviceVoiture.webserviceVoiture.voiture_model;

import com.webserviceVoiture.webserviceVoiture.configuration.user_model.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author TOAVINA
 */

@Entity
public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private LocalDateTime dateDemande = LocalDateTime.now();
    
    @ManyToOne
    @JoinColumn(name = "categorie_id" , nullable = false)
    private Category modele;
    
    @ManyToOne
    @JoinColumn(name = "marque_id", nullable = false)
    private Brand marque;
    
    private String description;
    
    private double prix;
    
    //1 en attente 2: publie 3: vendu
    private int statut = 1;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User utilisateur;
    
    private int annee;
    private double kilometrage;

    private LocalDateTime dateAcceptation; 
    private LocalDateTime dateVente;
    
    @OneToMany(mappedBy = "annonce", cascade = CascadeType.ALL)
    private List<Image> images;

    public Ad() {
    }

    public Ad(Long id, String description, double prix, int statut, User utilisateur, Category modele, Brand marque, int annee, double kilometrage, LocalDateTime dateAcceptation, LocalDateTime dateVente) {
        this.id = id;
        this.description = description;
        this.prix = prix;
        this.statut = statut;
        this.utilisateur = utilisateur;
        this.modele = modele;
        this.marque = marque;
        this.annee = annee;
        this.kilometrage = kilometrage;
        this.dateAcceptation = dateAcceptation;
        this.dateVente = dateVente;
    }

    public Ad(Long id, String description, double prix, int statut, User utilisateur, Category modele, Brand marque, int annee, double kilometrage, LocalDateTime dateAcceptation, LocalDateTime dateVente, List<Image> images) {
        this.id = id;
        this.description = description;
        this.prix = prix;
        this.statut = statut;
        this.utilisateur = utilisateur;
        this.modele = modele;
        this.marque = marque;
        this.annee = annee;
        this.kilometrage = kilometrage;
        this.dateAcceptation = dateAcceptation;
        this.dateVente = dateVente;
        this.images = images;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public double getKilometrage() {
        return kilometrage;
    }

    public void setKilometrage(double kilometrage) {
        this.kilometrage = kilometrage;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public int getStatut() {
        return statut;
    }

    public void setStatut(int statut) {
        this.statut = statut;
    }

    public User getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(User utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Category getModele() {
        return modele;
    }

    public void setModele(Category modele) {
        this.modele = modele;
    }

    public Brand getMarque() {
        return marque;
    }

    public void setMarque(Brand marque) {
        this.marque = marque;
    }

    public LocalDateTime getDateDemande() {
        return dateDemande;
    }

    public void setDateDemande(LocalDateTime dateDemande) {
        this.dateDemande = dateDemande;
    }

    public LocalDateTime getDateAcceptation() {
        return dateAcceptation;
    }

    public void setDateAcceptation(LocalDateTime dateAcceptation) {
        this.dateAcceptation = dateAcceptation;
    }

    public LocalDateTime getDateVente() {
        return dateVente;
    }

    public void setDateVente(LocalDateTime dateVente) {
        this.dateVente = dateVente;
    }

}
