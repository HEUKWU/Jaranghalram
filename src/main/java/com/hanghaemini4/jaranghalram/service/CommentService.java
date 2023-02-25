package com.hanghaemini4.jaranghalram.service;

import com.hanghaemini4.jaranghalram.dto.CommentRequestDto;
import com.hanghaemini4.jaranghalram.dto.CommentResponseDto;
import com.hanghaemini4.jaranghalram.entity.Comment;
import com.hanghaemini4.jaranghalram.entity.Post;
import com.hanghaemini4.jaranghalram.entity.User;
import com.hanghaemini4.jaranghalram.repository.CommentRepository;
import com.hanghaemini4.jaranghalram.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public CommentResponseDto add(Long postId, CommentRequestDto requestDto, User user) {
        Post post = postRepository.findById(postId).orElseThrow(IllegalArgumentException::new);
        Comment comment = commentRepository.save(new Comment(requestDto.getContent(), post, user));
        return new CommentResponseDto(comment);
    }

    @Transactional
    public void update(Long postId, CommentRequestDto requestDto, User user) {
        Comment comment = commentRepository.findById(postId).orElseThrow(NullPointerException::new);
        if (comment.getUser().getUserName().equals(user.getUserName())) {
            comment.update(requestDto.getContent());
        } else {
            throw new IllegalArgumentException("작성자만 수정 가능");
        }
    }

    @Transactional
    public void delete(Long postId, User user) {
        Comment comment = commentRepository.findById(postId).orElseThrow(NullPointerException::new);
        if (comment.getUser().getUserName().equals(user.getUserName())) {
            commentRepository.deleteById(postId);
        } else {
            throw new IllegalArgumentException("작성자만 삭제 가능");
        }
    }
}
