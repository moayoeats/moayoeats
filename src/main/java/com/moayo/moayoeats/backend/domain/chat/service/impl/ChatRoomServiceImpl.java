package com.moayo.moayoeats.backend.domain.chat.service.impl;


import com.moayo.moayoeats.backend.domain.chat.entity.ChatRoom;
import com.moayo.moayoeats.backend.domain.chat.repository.ChatRoomRepository;
import com.moayo.moayoeats.backend.domain.chat.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    // 채팅방 생성
    @Override
    public void createRoom(Long postId) {
        ChatRoom newRoom = new ChatRoom(postId);
        chatRoomRepository.save(newRoom);
    }

    // 채팅방 삭제
    @Override
    public void deleteRoom(Long postId) {
        chatRoomRepository.deleteById(postId);
    }

    // 채팅방 조회
    @Override
    public boolean existsById(Long postId) {
        return chatRoomRepository.existsById(postId);
    }

}
