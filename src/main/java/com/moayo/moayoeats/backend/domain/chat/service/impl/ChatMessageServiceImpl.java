package com.moayo.moayoeats.backend.domain.chat.service.impl;

import com.moayo.moayoeats.backend.domain.chat.dto.request.ChatMessageRequest;
import com.moayo.moayoeats.backend.domain.chat.dto.response.ChatMessageResponse;
import com.moayo.moayoeats.backend.domain.chat.entity.ChatMessage;
import com.moayo.moayoeats.backend.domain.chat.repository.ChatMessageRepository;
import com.moayo.moayoeats.backend.domain.chat.service.ChatMessageService;
import com.moayo.moayoeats.backend.domain.user.entity.User;
import com.moayo.moayoeats.backend.domain.user.exception.UserErrorCode;
import com.moayo.moayoeats.backend.domain.user.repository.UserRepository;
import com.moayo.moayoeats.backend.global.exception.GlobalException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;

    @Override
    public ChatMessage saveChatMessage(String postId, String sender, String content) {

        User user = findByNickname(sender);

        ChatMessage chatMessage = ChatMessage.builder()
            .postId(postId)
            .sender(sender)
            .content(content)
            .userId(String.valueOf(user.getId()))
            .build();

        chatMessageRepository.save(chatMessage);

        return chatMessage;
    }

    public List<ChatMessageResponse> getChatHistory(String postId) {
        List<ChatMessage> messages = chatMessageRepository.findByPostId(postId);
        return messages.stream()
            .map(message -> {
                return new ChatMessageResponse(message.getContent(), message.getSender());
            })
            .collect(Collectors.toList());
    }

    private User findByNickname(String nickname) {
        return userRepository.findByNickname(nickname)
            .orElseThrow(() -> new GlobalException(UserErrorCode.UNAUTHORIZED_USER));
    }

}
