package com.hanghaemini4.jaranghalram.service;

import com.hanghaemini4.jaranghalram.dto.CommentRequestDto;
import com.hanghaemini4.jaranghalram.dto.CommentResponseDto;
import com.hanghaemini4.jaranghalram.dto.ResponseDto;
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
    public ResponseDto<?> add(Long postId, CommentRequestDto requestDto, User user) {
        Post post = postRepository.findById(postId).orElseThrow(IllegalArgumentException::new);
        commentRepository.save(new Comment(requestDto.getContent(), post, user));
        return ResponseDto.success("댓글 등록 성공");
    }

    @Transactional
    public ResponseDto<?> update(Long postId, CommentRequestDto requestDto, User user) {
        Comment comment = commentRepository.findById(postId).orElseThrow(NullPointerException::new);
        if (comment.getUser().getUserName().equals(user.getUserName())) {
            comment.update(requestDto.getContent());
            return ResponseDto.success("댓글 수정 성공");
        } else {
            throw new IllegalArgumentException("작성자만 수정 가능");
        }
    }

    @Transactional
    public ResponseDto<?> delete(Long postId, User user) {
        Comment comment = commentRepository.findById(postId).orElseThrow(NullPointerException::new);
        if (comment.getUser().getUserName().equals(user.getUserName())) {
            commentRepository.deleteById(postId);
            return ResponseDto.success("댓글 삭제 성공");
        } else {
            throw new IllegalArgumentException("작성자만 삭제 가능");
        }
    }
}
