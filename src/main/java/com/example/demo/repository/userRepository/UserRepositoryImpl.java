package com.example.demo.repository.userRepository;

import org.springframework.stereotype.Service;

import com.example.demo.model.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserRepositoryImpl implements CustomUserRepository {
    @PersistenceContext
    private EntityManager em;

    public UserRepositoryImpl(EntityManager emParam) {
        this.em = emParam;
    }

    @Override
    public boolean saveUser(User user) {
        this.em.persist(user);
        return true;
    }

    @Override
    public boolean changePassword(User user, String password) {
        user.updatePassword(password);
        this.em.merge(user);
        return true;
    }

}
