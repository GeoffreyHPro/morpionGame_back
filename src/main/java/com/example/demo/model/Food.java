package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "foods")
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idFood;

    private String bareCode;

    @OneToMany
    private List<FoodBatch> foodBatches = new ArrayList<>();

    public Food() {

    }

    public Food(String bareCode) {
        this.bareCode = bareCode;
    }

    public String getBareCode() {
        return bareCode;
    }

    public void setBareCode(String bareCode) {
        this.bareCode = bareCode;
    }

    public int getIdFood() {
        return idFood;
    }

    public List<FoodBatch> getFoodBatches() {
        return foodBatches;
    }

    public void setFoodBatches(List<FoodBatch> foodBatches) {
        this.foodBatches = foodBatches;
    }

    public void addFoodBatches(FoodBatch foodBatch) {
        this.foodBatches.add(foodBatch);
    }

}
