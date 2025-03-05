package com.example.demo.service.rest;

import com.example.demo.model.User;
import com.example.demo.reponses.TokenResponse;
import com.example.demo.repository.userRepository.UserRepository;
import com.example.demo.repository.userRepository.UserRepositoryImpl;
import com.example.demo.request.EmailPasswordRequest;
import com.example.exception.AlreadyExistException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
  @Autowired
  private UserRepository ourUserRepo;
  @Autowired
  private UserRepositoryImpl customUserRepository;

  @Autowired
  private JWTUtils jwtUtils;

  public void save(User user) throws AlreadyExistException {
    User userFoundByUsername = null;
    userFoundByUsername = (User) loadUserByUsername(user.getUsername());
    User userFoundByPseudo = null;
    userFoundByPseudo = (User) getUserByPseudo(user.getPseudo());

    if (userFoundByUsername == null && userFoundByPseudo == null) {
      customUserRepository.saveUser(user);
    } else {
      throw new AlreadyExistException();
    }
  }

  public TokenResponse getTokenResponse(EmailPasswordRequest content) {
    User user = (User) loadUserByUsername(content.getEmail());
    String jwt = jwtUtils.generateToken(user);
    return new TokenResponse(jwt, user.getRole());
  }

  public User loadUserByUsername(String username) throws UsernameNotFoundException {
    return ourUserRepo.findByEmail(username);
  }

  public User getUserByPseudo(String pseudo) throws UsernameNotFoundException {
    return ourUserRepo.findByUsername(pseudo);
  }
}
