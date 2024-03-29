package com.moayo.moayoeats.backend.domain.chat.service;

import com.moayo.moayoeats.backend.domain.chat.dto.request.ChatMessageRequest;
import com.moayo.moayoeats.backend.domain.chat.dto.response.ChatMessageResponse;
import com.moayo.moayoeats.backend.domain.chat.entity.ChatMessage;
import java.util.List;

public interface ChatMessageService {

    /**
     * @param postId  채팅방 id
     * @param sender  채팅 작성자
     * @param content 채팅 내용
     */
    ChatMessage saveChatMessage(String postId, String sender, String content);

    /**
     * @param req
     * @param msg
     */
    ChatMessageResponse createRes(ChatMessageRequest req, ChatMessage msg);

    List<ChatMessageResponse> getChatHistory(String postId);
}
