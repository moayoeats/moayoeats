package com.moayo.moayoeats.backend.domain.chat.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;

import com.moayo.moayoeats.backend.domain.chat.entity.ChatMessage;
import com.moayo.moayoeats.backend.domain.chat.repository.ChatMessageRepository;
import com.moayo.moayoeats.backend.domain.user.entity.User;
import com.moayo.moayoeats.backend.domain.user.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ChatMessageServiceImplTest {

    @Mock
    ChatMessageRepository chatMessageRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    ChatMessageServiceImpl chatMessageService;

    @DisplayName("메시지 저장")
    @Test
    void saveChatMessage() {

        // Given
        String postId = "postId";
        String sender = "nickname";
        String content = "hi";

        User user = User.builder()
            .email("test00@dot.com")
            .password("password")
            .nickname("nickname")
            .build();

        ChatMessage chatMessage = ChatMessage.builder()
            .postId(postId)
            .sender(sender)
            .content(content)
            .userId("userId")
            .build();

        given(userRepository.findByNickname(sender)).willReturn(Optional.of(user));
        given(chatMessageRepository.save(any(ChatMessage.class))).willReturn(chatMessage);

        // When
        ChatMessage savedChatMessage = chatMessageService.saveChatMessage(postId, sender, content);

        // Then
        assertNotNull(savedChatMessage);
        assertEquals(postId, savedChatMessage.getPostId());
        assertEquals(sender, savedChatMessage.getSender());
        assertEquals(content, savedChatMessage.getContent());
    }

}