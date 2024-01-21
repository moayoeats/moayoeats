package com.moayo.moayoeats.backend.domain.chat.entity;

import com.moayo.moayoeats.backend.global.entity.BaseTime;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "tb_chatroom")
public class ChatRoom extends BaseTime {

    @Id
    private Long postId;

    public ChatRoom(Long postId) {
        this.postId = postId;
    }

}