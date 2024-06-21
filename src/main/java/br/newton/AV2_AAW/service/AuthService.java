package br.newton.AV2_AAW.service;

import br.newton.AV2_AAW.model.OrderEntity;
import br.newton.AV2_AAW.model.ProductEntity;
import br.newton.AV2_AAW.model.UserEntity;
import br.newton.AV2_AAW.repository.OrderRepository;
import br.newton.AV2_AAW.repository.ProductRepository;
import br.newton.AV2_AAW.repository.UserRepository;
import br.newton.AV2_AAW.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserEntity register(UserEntity user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public String validateUser(String username, String password) {
        UserEntity user = userRepository.findByUsername(username);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        return generateToken(user.getId(), user.getRole(), user.getEmail());
    }

    public String generateToken(String id, String role, String email){
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("id", id);
        userInfo.put("role", role);
        userInfo.put("email", email);

        String subject = "";

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            subject = objectMapper.writeValueAsString(userInfo);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        String token = JwtUtil.generateToken(subject);
        return token;
    }

    public String extractInfos(String token){
        String username = JwtUtil.extractUsername(token);
        return username;
    }

    public UserEntity atualizar(String id, UserEntity newUser) {
        UserEntity existingUser = userRepository.findById(id).orElse(null);

        if (existingUser != null) {
            existingUser.setUsername(newUser.getUsername());
            existingUser.setEmail(newUser.getEmail());
            existingUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
            existingUser.setRole(newUser.getRole());
            return userRepository.save(existingUser);
        } else {
            return null;
        }
    }

    public ProductEntity insertProduct(ProductEntity product){
        return productRepository.save(product);
    }

    public OrderEntity insertOrder(OrderEntity order){
        return orderRepository.save(order);
    }
}
