package com.hanghaemini4.jaranghalram.service;

import com.hanghaemini4.jaranghalram.dto.ResponseDto;
import com.hanghaemini4.jaranghalram.entity.Post;
import com.hanghaemini4.jaranghalram.entity.PostLike;
import com.hanghaemini4.jaranghalram.entity.User;
import com.hanghaemini4.jaranghalram.exceptionHandler.PostLikeServiceException;
import com.hanghaemini4.jaranghalram.repository.PostLikeRepository;
import com.hanghaemini4.jaranghalram.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostLikeService {
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;

    @Transactional
    public ResponseDto<Boolean> likePost(Long postId, User user) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostLikeServiceException("존재하지 않는 댓글"));
        if (postLikeRepository.findByPostIdAndUserId(postId, user.getId()).isPresent()) {
            post.likeCheck((post.getPostLikeCount()) - 1);
            postLikeRepository.deletePostLikeByUserId(user.getId());
            return ResponseDto.fail(null);
        }
        post.likeCheck((post.getPostLikeCount()) + 1);
        PostLike postlike = new PostLike(user, post);
        postLikeRepository.save(postlike);
        return ResponseDto.success(null);
    }
}
