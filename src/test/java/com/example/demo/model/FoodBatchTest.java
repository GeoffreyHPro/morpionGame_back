package com.example.demo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FoodBatchTest {

    private FoodBatch foodBatch1;

    @BeforeEach
    public void setUp() {
        this.foodBatch1 = new FoodBatch(1, LocalDateTime.of(2020, 12, 5, 0, 0));
    }

    @Test
    public void testBareCodeId() {
        assertEquals(0, this.foodBatch1.getIdFoodBatch());
    }

    @Test
    public void testAttributesChanged() {
        assertEquals(1, this.foodBatch1.getQuantity());
        assertEquals(LocalDateTime.of(2020, 12, 5, 0, 0), this.foodBatch1.getExpirationDate());
        this.foodBatch1.setQuantity(15);
        this.foodBatch1.setExpirationDate(LocalDateTime.of(2022, 12, 5, 0, 0));
        assertEquals(15, this.foodBatch1.getQuantity());
        assertEquals(LocalDateTime.of(2022, 12, 5, 0, 0), this.foodBatch1.getExpirationDate());
    }

}
