package com.example.demo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FoodTest {

    private Food food1;
    private FoodBatch foodBatch1;
    private FoodBatch foodBatch2;

    @BeforeEach
    public void setUp() {
        this.food1 = new Food("1502");
        this.foodBatch1 = new FoodBatch(1, LocalDateTime.of(2020, 12, 5, 0, 0));
        this.foodBatch2 = new FoodBatch(12, LocalDateTime.of(2010, 1, 5, 0, 0));
    }

    @Test
    public void testBareCodeId() {
        assertEquals(0, this.food1.getIdFood());
    }

    @Test
    public void testBareCodeChanged() {
        assertEquals("1502", this.food1.getBareCode());
        this.food1.setBareCode("000012");
        assertEquals("000012", this.food1.getBareCode());
    }

    @Test
    public void testAddFoodBatch() {
        this.food1.addFoodBatches(foodBatch1);
        assertEquals(1, this.food1.getFoodBatches().get(0).getQuantity());
        assertEquals(LocalDateTime.of(2020, 12, 5, 0, 0), this.food1.getFoodBatches().get(0).getExpirationDate());
        this.food1.addFoodBatches(foodBatch2);
        assertEquals(LocalDateTime.of(2010, 1, 5, 0, 0), this.food1.getFoodBatches().get(1).getExpirationDate());
        assertEquals(12, this.food1.getFoodBatches().get(1).getQuantity());
        assertEquals(2, food1.getFoodBatches().size());
    }
}
