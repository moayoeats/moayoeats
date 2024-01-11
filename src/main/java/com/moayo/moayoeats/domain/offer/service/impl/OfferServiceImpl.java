package com.moayo.moayoeats.domain.offer.service.impl;

import com.moayo.moayoeats.domain.offer.dto.request.OfferRequest;
import com.moayo.moayoeats.domain.offer.dto.response.OfferResponse;
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
import com.moayo.moayoeats.domain.userpost.repository.UserPostRepository;
import com.moayo.moayoeats.global.exception.GlobalException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final UserPostRepository userPostRepository;

    public void applyParticipation(OfferRequest offerReq, User user) {
        Long userId = user.getId();
        Long postId = offerReq.postId();

        User findUser = checkUnauthorizedUser(userId);
        Post post = checkIfPostExists(postId);
        checkApplicationStatus(userId, postId);

        Offer offer = Offer.builder()
            .post(post)
            .user(findUser)
            .build();

        post.addOffer(offer);
        offerRepository.save(offer);
    }

    public void cancelParticipation(OfferRequest offerReq, User user) {
        Long userId = user.getId();
        Long postId = offerReq.postId();

        checkIfPostExists(postId);
        Offer offer = checkIfAlreadyApplied(userId, postId);

        offerRepository.delete(offer);
    }

    public List<OfferResponse> viewApplication(OfferRequest offerReq, User user) {
        Long userId = user.getId();
        Long postId = offerReq.postId();

        Post post = checkIfPostExists(postId);
        checkIfUserExistsAboutPost(userId);

        List<Offer> offers = post.getOffers();
        List<OfferResponse> offerResList = new ArrayList<>();
        offers.forEach(offer ->
            offerResList.add(
                OfferResponse.builder()
                    .postId(postId)
                    .userId(offer.getUser().getId())
                    .userNickname(offer.getUser().getNickname())
                    .build()
            ));
        return offerResList;
    }

    private User checkUnauthorizedUser(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new GlobalException(UserErrorCode.UNAUTHORIZED_USER));
    }

    private Post checkIfPostExists(Long postId) {
        return postRepository.findById(postId)
            .orElseThrow(() -> new GlobalException(PostErrorCode.NOT_FOUND_POST));
    }

    private void checkApplicationStatus(Long userId, Long postId) {
        if (offerRepository.existsByUserIdAndPostId(userId, postId)) {
            throw new GlobalException(OfferErrorCode.ALREADY_APPLIED_PARTICIPATION);
        }
    }

    private Offer checkIfAlreadyApplied(Long userId, Long postId) {
        return offerRepository.findByUserIdAndPostId(userId, postId)
            .orElseThrow(() -> new GlobalException(OfferErrorCode.NOT_FOUND_OFFER));
    }

    private void checkIfUserExistsAboutPost(Long userId) {
        if (!userPostRepository.existsByUserId(userId)) {
            throw new GlobalException(PostErrorCode.UNAUTHORIZED_USER_ABOUT_POST);
        }
    }

}
