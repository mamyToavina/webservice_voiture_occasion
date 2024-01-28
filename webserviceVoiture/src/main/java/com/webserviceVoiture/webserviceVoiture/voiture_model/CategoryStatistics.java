/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webserviceVoiture.webserviceVoiture.voiture_model;

/**
 *
 * @author TOAVINA
 */
public class CategoryStatistics {
    private String categoryName;
    private long numberOfCarsSold;
    private double percentage;

    public CategoryStatistics() {
    }

    
    public CategoryStatistics(String categoryName, long numberOfCarsSold, double percentage) {
        this.categoryName = categoryName;
        this.numberOfCarsSold = numberOfCarsSold;
        this.percentage = percentage;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public long getNumberOfCarsSold() {
        return numberOfCarsSold;
    }

    public void setNumberOfCarsSold(long numberOfCarsSold) {
        this.numberOfCarsSold = numberOfCarsSold;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
}

