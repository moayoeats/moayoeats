package com.moayo.moayoeats.domain.post.entity;


import com.moayo.moayoeats.domain.menu.entity.Menu;
import com.moayo.moayoeats.domain.offer.entity.Offer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tb_post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String store;

    @Column(nullable = false)
    private Integer minPrice;

    @Column(nullable = false)
    private Integer deliveryCost;

    @Column
    private CategoryEnum category;

    @OneToMany(mappedBy = "post")
    private List<Menu> menus;

    @Column
    private Long sumPrice;

    @Column
    private LocalDateTime deadline;

    @OneToMany(mappedBy = "post")
    private List<Offer> offers;

    @Builder
    public Post(String address, String store, Integer minPrice, Integer deliveryCost, CategoryEnum category, LocalDateTime deadline){
        this.address = address;
        this.store = store;
        this.minPrice = minPrice;
        this.deliveryCost = deliveryCost;
        this.deadline = deadline;
        this.category = category;
    }

}
