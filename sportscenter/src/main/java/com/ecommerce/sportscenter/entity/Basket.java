package com.ecommerce.sportscenter.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
//@RedisHash("Basket")
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Basket")
public class Basket {
	
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @OneToMany(mappedBy = "basket", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
     private List<BasketItem> items = new ArrayList<>();
    
    public Basket(String id) {
        this.id = UUID.randomUUID().toString();
    }
    
   
}
