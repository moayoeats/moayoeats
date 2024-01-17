package com.moayo.moayoeats.backend.domain.chat.service.impl;

import com.moayo.moayoeats.backend.domain.chat.dto.ChatMessageDTO;
import com.moayo.moayoeats.backend.domain.chat.entity.ChatMessage;
import com.moayo.moayoeats.backend.domain.chat.repository.ChatMessageRepository;
import com.moayo.moayoeats.backend.domain.chat.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    @Override
    public ChatMessageDTO saveChatMessage(Long postId, String sender, String content) {
        ChatMessage chatMessage = ChatMessage.builder()
            .postId(postId)
            .sender(sender)
            .content(content)
            .build();
        chatMessageRepository.save(chatMessage);

        return new ChatMessageDTO(postId, sender, content);
    }

}
