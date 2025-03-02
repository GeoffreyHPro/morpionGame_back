package com.example.demo.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "foodbatches")
public class FoodBatch {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idFoodBatch;

    private int quantity;

    private LocalDateTime expirationDate;

    @ManyToOne
    private Food food;

    public FoodBatch() {

    }

    public FoodBatch(int quantity, LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
        this.quantity = quantity;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public int getIdFoodBatch() {
        return idFoodBatch;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

}
