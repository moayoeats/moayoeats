package com.moayo.moayoeats.backend.domain.chat.service;

import com.moayo.moayoeats.backend.domain.chat.dto.request.ChatMessageRequest;
import com.moayo.moayoeats.backend.domain.chat.dto.response.ChatMessageResponse;
import com.moayo.moayoeats.backend.domain.chat.entity.ChatMessage;

public interface ChatMessageService {

    /**
     * @param postId  채팅방 id
     * @param sender  채팅 작성자
     * @param content 채팅 내용
     */
    void saveChatMessage(String postId, String sender, String content);

}
