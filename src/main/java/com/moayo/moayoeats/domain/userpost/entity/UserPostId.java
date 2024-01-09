package com.moayo.moayoeats.domain.userpost.entity;

import com.moayo.moayoeats.domain.post.entity.Post;
import com.moayo.moayoeats.domain.user.entity.User;
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
