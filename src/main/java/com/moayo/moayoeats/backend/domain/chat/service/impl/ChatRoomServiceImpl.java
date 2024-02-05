package com.moayo.moayoeats.backend.domain.chat.service.impl;


import com.moayo.moayoeats.backend.domain.chat.service.ChatRoomService;
import com.moayo.moayoeats.backend.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatRoomServiceImpl implements ChatRoomService {

    private final PostRepository postRepository;

    // 채팅방 조회
    @Override
    public boolean existsById(Long postId) {
        return postRepository.existsById(postId);
    }

}
