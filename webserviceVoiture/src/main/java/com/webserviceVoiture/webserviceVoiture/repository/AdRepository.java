/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.webserviceVoiture.webserviceVoiture.repository;

import com.webserviceVoiture.webserviceVoiture.voiture_model.Ad;
import com.webserviceVoiture.webserviceVoiture.voiture_model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author TOAVINA
 */

@Repository
public interface AdRepository extends JpaRepository<Ad, Long> {
    public Page<Ad> findAllByUtilisateurId(long id,Pageable pageable);
    public Page<Ad> findAllByStatut(long id,Pageable pageable);
    public Page<Ad> findAllByUtilisateurIdAndStatut(long userId, int status, Pageable pageable);
    @Query("SELECT ad FROM Ad ad WHERE "
            + "(:categoryId IS NULL OR ad.modele.id = :categoryId) AND "
            + "(:brandId IS NULL OR ad.marque.id = :brandId) AND "
            + "(:minPrice IS NULL OR ad.prix >= :minPrice) AND "
            + "(:maxPrice IS NULL OR ad.prix <= :maxPrice) AND "
            + "(:minKilometrage IS NULL OR ad.kilometrage >= :minKilometrage) AND "
            + "(:maxKilometrage IS NULL OR ad.kilometrage <= :maxKilometrage)")
    Page<Ad> searchAdsByCriteria(
            @Param("categoryId") Long categoryId,
            @Param("brandId") Long brandId,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("minKilometrage") Double minKilometrage,
            @Param("maxKilometrage") Double maxKilometrage,
            Pageable pageable);
    
    long countByModeleAndStatut(Category modele, int statut);
    long countByStatut(int statut);
}
