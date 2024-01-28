/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.webserviceVoiture.webserviceVoiture.repository;

import com.webserviceVoiture.webserviceVoiture.voiture_model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author TOAVINA
 */

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

}
