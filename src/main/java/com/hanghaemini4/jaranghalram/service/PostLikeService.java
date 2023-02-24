package com.hanghaemini4.jaranghalram.service;

import com.hanghaemini4.jaranghalram.entity.Post;
import com.hanghaemini4.jaranghalram.entity.PostLike;
import com.hanghaemini4.jaranghalram.entity.User;
import com.hanghaemini4.jaranghalram.repository.PostLIkeRepository;
import com.hanghaemini4.jaranghalram.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostLikeService {
    private final PostRepository postRepository;
    private final PostLIkeRepository postLikeRepository;

    @Transactional
    public String likePost(Long postId, User user) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NullPointerException("존재하지 않는 댓글"));
        if (postLikeRepository.findByPostIdAndUserId(postId, user.getId()).isPresent()) {
            post.likeCheck((post.getPostLikeCount()) - 1);
            postLikeRepository.deletePostLikeByUserId(user.getId());
            return "좋아요 취소";
        }
        post.likeCheck((post.getPostLikeCount()) + 1);
        PostLike postlike = new PostLike(user, post);
        postLikeRepository.save(postlike);
        return "좋아요 성공";
    }
}
