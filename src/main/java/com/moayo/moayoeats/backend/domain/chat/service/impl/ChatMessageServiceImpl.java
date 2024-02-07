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

        ChatMessage chatMessage = ChatMessage.builder()
            .postId(postId)
            .sender(sender)
            .content(content)
            .userId(findByNickname(sender))
            .build();

        chatMessageRepository.save(chatMessage);

        return chatMessage;
    }

    @Override
    public ChatMessageResponse createRes(ChatMessageRequest req, ChatMessage msg) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String formattedTime = msg.getCreatedAt().format(formatter);

        return new ChatMessageResponse(req.content(), req.sender(), formattedTime);
    }

    @Override
    public List<ChatMessageResponse> getChatHistory(String postId) {
        List<ChatMessage> messages = chatMessageRepository.findByPostId(postId);
        return messages.stream()
            .map(message -> {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                String formattedTime = message.getCreatedAt().format(formatter);
                return new ChatMessageResponse(message.getContent(), message.getSender(),
                    formattedTime);
            })
            .collect(Collectors.toList());
    }

    private String findByNickname(String nickname) {
        User user = userRepository.findByNickname(nickname)
            .orElseThrow(() -> new GlobalException(UserErrorCode.UNAUTHORIZED_USER));

        return String.valueOf(user.getId());
    }

}
