package com.moayo.moayoeats.backend.domain.userpost.entity;

import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class UserPostId implements Serializable {

    @EqualsAndHashCode.Include
    private Long user;

    @EqualsAndHashCode.Include
    private Long post;

}
