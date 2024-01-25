package com.moayo.moayoeats.backend.domain.chat.repository;

import com.moayo.moayoeats.backend.domain.chat.entity.ChatMessage;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
    List<ChatMessage> findByPostId(String postId);
}
