package com.moayo.moayoeats.backend.domain.offer.entity;

import com.moayo.moayoeats.backend.domain.post.entity.*;
import com.moayo.moayoeats.backend.domain.user.entity.*;
import com.moayo.moayoeats.backend.global.entity.*;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tb_offer")
public class Offer extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    public Offer(User user, Post post) {
        this.user = user;
        this.post = post;
    }

}
