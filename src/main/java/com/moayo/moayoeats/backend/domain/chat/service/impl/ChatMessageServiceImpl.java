package com.moayo.moayoeats.backend.domain.chat.service.impl;

import com.moayo.moayoeats.backend.domain.chat.dto.ChatMessageDTO;
import com.moayo.moayoeats.backend.domain.chat.entity.ChatMessage;
import com.moayo.moayoeats.backend.domain.chat.repository.ChatMessageRepository;
import com.moayo.moayoeats.backend.domain.chat.service.ChatMessageService;
import com.moayo.moayoeats.backend.domain.user.entity.User;
import com.moayo.moayoeats.backend.domain.user.exception.UserErrorCode;
import com.moayo.moayoeats.backend.domain.user.repository.UserRepository;
import com.moayo.moayoeats.backend.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;

    @Override
    public ChatMessageDTO saveChatMessage(String postId, String sender, String content, User user) {

        String userId = String.valueOf(user.getId());

        ChatMessage chatMessage = ChatMessage.builder()
            .postId(postId)
            .sender(sender)
            .content(content)
            .userId(userId)
            .build();

        chatMessageRepository.save(chatMessage);

        return new ChatMessageDTO(postId, sender, content);
    }

     private User checkUserId(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new GlobalException(UserErrorCode.UNAUTHORIZED_USER));
     }


}
