package com.moayo.moayoeats.backend.domain.offer.service.impl;

import com.moayo.moayoeats.backend.domain.menu.dto.response.MenuResponse;
import com.moayo.moayoeats.backend.domain.menu.repository.MenuRepository;
import com.moayo.moayoeats.backend.domain.notification.entity.NotificationType;
import com.moayo.moayoeats.backend.domain.notification.event.Event;
import com.moayo.moayoeats.backend.domain.offer.dto.request.OfferRelatedPostRequest;
import com.moayo.moayoeats.backend.domain.offer.dto.request.OfferRequest;
import com.moayo.moayoeats.backend.domain.offer.dto.response.OfferResponse;
import com.moayo.moayoeats.backend.domain.offer.dto.response.OfferRoleResponse;
import com.moayo.moayoeats.backend.domain.offer.entity.Offer;
import com.moayo.moayoeats.backend.domain.offer.exception.OfferErrorCode;
import com.moayo.moayoeats.backend.domain.offer.repository.OfferRepository;
import com.moayo.moayoeats.backend.domain.offer.service.OfferService;
import com.moayo.moayoeats.backend.domain.post.entity.Post;
import com.moayo.moayoeats.backend.domain.post.entity.PostStatusEnum;
import com.moayo.moayoeats.backend.domain.post.exception.PostErrorCode;
import com.moayo.moayoeats.backend.domain.post.repository.PostRepository;
import com.moayo.moayoeats.backend.domain.pushEvent.PushEventService;
import com.moayo.moayoeats.backend.domain.user.entity.User;
import com.moayo.moayoeats.backend.domain.user.exception.UserErrorCode;
import com.moayo.moayoeats.backend.domain.user.repository.UserRepository;
import com.moayo.moayoeats.backend.domain.userpost.entity.UserPost;
import com.moayo.moayoeats.backend.domain.userpost.entity.UserPostRole;
import com.moayo.moayoeats.backend.domain.userpost.exception.UserPostErrorCode;
import com.moayo.moayoeats.backend.domain.userpost.repository.UserPostRepository;
import com.moayo.moayoeats.backend.global.exception.GlobalException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final UserPostRepository userPostRepository;
    private final MenuRepository menuRepository;
    private final ApplicationEventPublisher publisher;
    private final PushEventService pushEventService;

    public void applyParticipation(OfferRelatedPostRequest offerRelatedPostReq, User user) {
        Long userId = user.getId();
        Long postId = offerRelatedPostReq.postId();

        User findUser = checkUnauthorizedUser(userId);
        Post post = checkIfPostExistsAndGet(postId);
        checkPostStatus(post);
        checkIfHostAndThrowException(userId, postId);
        checkApplicationStatus(userId, postId);

        Offer offer = Offer.builder()
            .post(post)
            .user(findUser)
            .build();

        //방장에게 알림
        User targetHost = userPostRepository.findByPostIdAndRole(postId, UserPostRole.HOST);
        publisher.publishEvent(new Event(targetHost, NotificationType.PARTICIPANT_JOIN_REQUEST));
        pushEventService.notifyApplyParticipation(targetHost.getId());

        offerRepository.save(offer);
    }

    public void cancelParticipation(OfferRequest offerReq, User user) {
        Long userId = user.getId();
        Long offerId = offerReq.offerId();

        Offer offer = checkIfAlreadyApplied(userId, offerId);

        offerRepository.delete(offer);
    }

    public OfferRoleResponse viewApplication(Long postId, User user) {

        Post post = checkIfPostExistsAndGet(postId);
        UserPost userpost = findUserPostByUserAndPost(user.getId(), postId);
        UserPostRole role = userpost == null ? null : userpost.getRole();

        List<OfferResponse> offerResponses;
        List<Offer> offers = offerRepository.findAllByPostId(postId);

        if (role == null) {
            offers = offers.stream()
                .filter(offer -> offer.getUser().getId().equals(user.getId())).toList();
        }
        offerResponses = getOfferResponsesByOffers(offers, post);
        return new OfferRoleResponse(offerResponses, role);
    }

    public void approveApplication(OfferRequest offerReq, User user) {
        Long offerId = offerReq.offerId();
        Long userId = user.getId();

        Offer offer = checkIfOfferExistsAndGet(offerId);
        checkIfNotHostAndThrowException(userId, offer.getPost().getId());

        UserPost userPost = UserPost.builder()
            .user(offer.getUser())
            .post(offer.getPost())
            .role(UserPostRole.PARTICIPANT)
            .build();

        //해당 참가자한테 알림
        User participant = offerRepository.findByOfferId(offerId);
        publisher.publishEvent(new Event(participant, NotificationType.PARTICIPANT_APPROVED));
        pushEventService.notifyApprove(participant.getId());
        userPostRepository.save(userPost);

        offerRepository.delete(offer);
    }

    public void rejectApplication(OfferRequest offerReq, User user) {
        Long userId = user.getId();
        Long offerId = offerReq.offerId();

        Offer offer = checkIfOfferExistsAndGet(offerId);
        checkIfNotHostAndThrowException(userId, offer.getPost().getId());

        //해당 참가자한테 알림
        User participant = offerRepository.findByOfferId(offerId);
        publisher.publishEvent(new Event(participant, NotificationType.PARTICIPANT_REJECTED));
        pushEventService.notifyReject(participant.getId());

        offerRepository.delete(offer);
    }

    public void cancelAfterApproval(OfferRelatedPostRequest offerRelatedPostReq, User user) {
        Long postId = offerRelatedPostReq.postId();

        Post post = checkIfPostExistsAndGet(postId);
        UserPost userPost = findUserPostIfParticipant(post, user);
        userPostRepository.delete(userPost);
    }

    private User checkUnauthorizedUser(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new GlobalException(UserErrorCode.UNAUTHORIZED_USER));
    }

    private Post checkIfPostExistsAndGet(Long postId) {
        return postRepository.findById(postId)
            .orElseThrow(() -> new GlobalException(PostErrorCode.NOT_FOUND_POST));
    }

    private Offer checkIfOfferExistsAndGet(Long offerId) {
        return offerRepository.findById(offerId).
            orElseThrow(() -> new GlobalException(OfferErrorCode.NOT_FOUND_OFFER));
    }

    private void checkIfPostExists(Long postId) {
        if (!postRepository.existsById(postId)) {
            throw new GlobalException(PostErrorCode.NOT_FOUND_POST);
        }
    }

    private void checkApplicationStatus(Long userId, Long postId) {
        if (offerRepository.existsByUserIdAndPostId(userId, postId)) {
            throw new GlobalException(OfferErrorCode.ALREADY_APPLIED_PARTICIPATION);
        }
    }

    private Offer checkIfAlreadyApplied(Long userId, Long offerId) {
        return offerRepository.findByUserIdAndId(userId, offerId)
            .orElseThrow(() -> new GlobalException(OfferErrorCode.NOT_FOUND_OFFER));
    }

    private void checkIfUserExistsAboutPost(Long userId, Long postId) {
        if (!userPostRepository.existsByUserIdAndPostId(userId, postId)) {
            throw new GlobalException(PostErrorCode.UNAUTHORIZED_USER_ABOUT_POST);
        }
    }

    private void checkIfHostAndThrowException(Long userId, Long postId) {
        if (userPostRepository.existsByUserIdAndPostIdAndRole(userId, postId, UserPostRole.HOST)) {
            throw new GlobalException(OfferErrorCode.ALREADY_PARTICIPATE);
        }
    }

    private void checkIfNotHostAndThrowException(Long userId, Long postId) {
        if (!userPostRepository.existsByUserIdAndPostIdAndRole(userId, postId, UserPostRole.HOST)) {
            throw new GlobalException(PostErrorCode.UNAUTHORIZED_USER_ABOUT_POST);
        }
    }

    private UserPost findUserPostIfParticipant(Post post, User user) {
        return userPostRepository
            .findByPostAndUserAndRoleEquals(post, user, UserPostRole.PARTICIPANT)
            .orElseThrow(() -> new GlobalException(UserPostErrorCode.NOT_FOUND_USERPOST));
    }

    private void checkPostStatus(Post post) {
        if (!post.getPostStatus().equals(PostStatusEnum.OPEN)) {
            throw new GlobalException(PostErrorCode.POST_ALREADY_CLOSED);
        }
    }

    private UserPost findUserPostByUserAndPost(Long userId, Long postId) {
        return userPostRepository.findByUserIdAndPostId(userId, postId).orElse(null);
    }

    private List<OfferResponse> getOfferResponsesByOffers(List<Offer> offers, Post post) {
        return offers.stream()
            .map(offer -> (OfferResponse.builder().offerId(offer.getId())
                .userId(offer.getUser().getId()).nickname(offer.getUser().getNickname())
                .menus(menuRepository.findAllByUserAndPost(offer.getUser(), post).stream()
                    .map(menu -> new MenuResponse(menu.getId(), menu.getMenuname(),
                        menu.getPrice())).toList()).build())).toList();
    }

}
