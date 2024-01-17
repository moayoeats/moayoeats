package com.moayo.moayoeats.backend.domain.chat.service;

import com.moayo.moayoeats.backend.domain.chat.dto.ChatMessageDTO;

public interface ChatMessageService {

    /**
     * @param postId 채팅방 id
     * @param sender 채팅 작성자
     * @param content 채팅 내용
     * @return
     */
    ChatMessageDTO saveChatMessage(Long postId, String sender, String content);

}
