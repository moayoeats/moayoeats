package com.moayo.moayoeats.domain.offer.service.impl;

import com.moayo.moayoeats.domain.offer.dto.request.OfferRequest;
import com.moayo.moayoeats.domain.offer.entity.Offer;
import com.moayo.moayoeats.domain.offer.exception.OfferErrorCode;
import com.moayo.moayoeats.domain.offer.repository.OfferRepository;
import com.moayo.moayoeats.domain.offer.service.OfferService;
import com.moayo.moayoeats.domain.post.entity.Post;
import com.moayo.moayoeats.domain.post.exception.PostErrorCode;
import com.moayo.moayoeats.domain.post.repository.PostRepository;
import com.moayo.moayoeats.domain.user.entity.User;
import com.moayo.moayoeats.domain.user.exception.UserErrorCode;
import com.moayo.moayoeats.domain.user.repository.UserRepository;
import com.moayo.moayoeats.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public void applyToParticipate(OfferRequest offerReq, User user) {
        Long userId = user.getId();
        Long postId = offerReq.postId();

        User findUser = userRepository.findById(userId)
            .orElseThrow(() -> new GlobalException(UserErrorCode.UNAUTHORIZED_USER));
        Post findPost = postRepository.findById(postId)
            .orElseThrow(() -> new GlobalException(PostErrorCode.NOT_FOUND_POST));

        if(offerRepository.existsByUserIdAndPostId(userId, postId)) {
            throw new GlobalException(OfferErrorCode.ALREADY_APPLIED_PARTICIPATION);
        }

        Offer offer = Offer.builder()
            .post(findPost)
            .user(findUser)
            .build();

        offerRepository.save(offer);
    }

}
