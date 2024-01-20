package com.moayo.moayoeats.backend.domain.chat.service;

import com.moayo.moayoeats.backend.domain.chat.dto.ChatMessageDTO;
import com.moayo.moayoeats.backend.domain.user.entity.User;

public interface ChatMessageService {

    /**
     * @param postId 채팅방 id
     * @param sender 채팅 작성자
     * @param content 채팅 내용
     * @param user get userId
     * @return
     */
    ChatMessageDTO saveChatMessage(String postId, String sender, String content, User user);

}
