package com.moayo.moayoeats.backend.domain.chat.entity;

import com.moayo.moayoeats.backend.global.entity.BaseTime;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@NoArgsConstructor
@Document
public class ChatMessage extends BaseTime {

    @Id
    private String id;

    @Field
    private Long postId;

    @Field
    private String sender;

    @Field
    private String content;

    @Builder
    public ChatMessage(Long postId, String sender, String content) {
        this.postId = postId;
        this.sender = sender;
        this.content = content;
    }

}
