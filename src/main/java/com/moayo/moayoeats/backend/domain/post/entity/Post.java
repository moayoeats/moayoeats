package com.moayo.moayoeats.backend.domain.post.entity;

import com.moayo.moayoeats.backend.domain.menu.entity.Menu;
import com.moayo.moayoeats.backend.domain.offer.entity.Offer;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
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
    @Enumerated(EnumType.STRING)
    private CategoryEnum category;

    @Column
    private Long sumPrice;

    @Column
    private LocalDateTime deadline;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Menu> menus;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Offer> offers;

    @Column
    @Enumerated(EnumType.STRING)
    private PostStatusEnum postStatus;

    @Builder
    public Post(String address, String store, Integer minPrice, Integer deliveryCost,
        CategoryEnum category, LocalDateTime deadline,PostStatusEnum postStatus) {
        this.address = address;
        this.store = store;
        this.minPrice = minPrice;
        this.deliveryCost = deliveryCost;
        this.deadline = deadline;
        this.category = category;
        this.postStatus = postStatus;
    }

    public void closeApplication(){
        this.postStatus = PostStatusEnum.CLOSED;
    }

}
