package br.newton.AV2_AAW.controller;

import br.newton.AV2_AAW.model.OrderEntity;
import br.newton.AV2_AAW.model.ProductEntity;
import br.newton.AV2_AAW.model.UserEntity;
import br.newton.AV2_AAW.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public UserEntity register(@RequestBody UserEntity request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public String login(@RequestBody UserEntity request) {
        String token = authService.validateUser(request.getUsername(), request.getPassword());
        return token;
    }

    @GetMapping("/info/{token}")
    public String extractInfos(@PathVariable String token) {
        String infos = authService.extractInfos(token);
        return infos;
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/admin/users")
    public UserEntity manageUsers(@RequestBody UserEntity request) {
        return authService.register(request);
    }

    @Secured("ROLE_GERENTE")
    @PostMapping("/manage/products")
    public ProductEntity manageProducts(@RequestBody ProductEntity request){
        return authService.insertProduct(request);
    }

    @Secured("ROLE_VENDEDOR")
    @PostMapping("/seller/orders")
    public OrderEntity manageOrders(@RequestBody OrderEntity request){
        return authService.insertOrder(request);
    }

    @Secured("ROLE_CLIENTE")
    @GetMapping("/customer/products")
    public String acessProducts(Authentication authentication){
        return "Cliente: " + authentication.getName();
    }
}
