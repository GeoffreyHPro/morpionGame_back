package com.example.demo.repository.foodRepository;

import org.springframework.stereotype.Service;

import com.example.demo.model.Food;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class FoodRepositoryImpl implements CustomFoodRepository{
    @PersistenceContext
    private EntityManager em;

    public FoodRepositoryImpl(EntityManager emParam) {
        this.em = emParam;
    }

    @Override
    public boolean saveFood(Food food) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveFood'");
    }

    
}
