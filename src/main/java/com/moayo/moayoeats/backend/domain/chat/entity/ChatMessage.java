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
@Document(collection = "chat_message")
public class ChatMessage extends BaseTime {

    @Id
    private String id;

    @Field
    private String postId;

    @Field
    private String sender;

    @Field
    private String content;

    @Field
    private String userId;

    @Builder
    public ChatMessage(String postId, String sender, String content, String userId) {
        this.postId = postId;
        this.sender = sender;
        this.content = content;
        this.userId = userId;
    }

}
