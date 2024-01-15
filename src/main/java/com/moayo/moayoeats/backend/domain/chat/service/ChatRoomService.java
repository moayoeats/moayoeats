package com.moayo.moayoeats.backend.domain.chat.service;

public interface ChatRoomService {

    /**
     * @param postId : 채팅방 id로 사용할 postId
     */
    void createRoom(Long postId);

    /**
     * @param postId : 채팅방 삭제를 위한 postId
     */
    void deleteRoom(Long postId);
}
