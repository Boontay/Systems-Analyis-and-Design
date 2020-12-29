package com.online.store.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(schema = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String username;

    private String email;

    private String password;

    private String first_name;

    private String last_name;

    private String address;

    @OneToMany
    private List<Product> cart;

    public User() {
        cart = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // Cart related methods

    public List<Product> getCart() {
        return cart;
    }

    public int getCartSize() {
        return cart.size();
    }

    public void addCartItem(Product item) {
        this.cart.add(item);
    }

    public void removeCartItem(Product item) {
        this.cart.remove(item);
    }

    public boolean isItemInCart(Product item) {
        return this.cart.contains(item);
    }

    public Product getCartItem(int index) {
        if (this.cart.size() <= index) {
            return this.cart.get(index);
        }
        else {
            return null;
        }
    }

    public BigDecimal getTotalCartPrice() {
        BigDecimal totalCost = new BigDecimal(0);
        for (int i = 0; i < this.cart.size(); i++) {
            BigDecimal itemPrice = this.cart.get(i).getPrice();
            totalCost.add(itemPrice);
        }

        return totalCost;
    }
}
