package com.moayo.moayoeats.backend.domain.review.entity;

import com.moayo.moayoeats.backend.domain.user.entity.User;
import com.moayo.moayoeats.backend.global.entity.BaseTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tb_review")
public class Review extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column
    private Integer score;

    @Column
    private Integer goodmanner;

    @Column
    private Integer goodtime;

    @Column
    private Integer goodcomm;

    @Column
    private Integer badtime;

    @Column
    private Integer noshow;

    @Column
    private Integer nomoney;

    @Column
    private Integer badcomm;

    @Column
    private Integer badmanner;

}
