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

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentResponseDto add(Long id, CommentRequestDto requestDto, User user) {
        Post post = postRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        Comment comment = commentRepository.save(new Comment(requestDto.getContent(), post, user));
        return new CommentResponseDto(comment);
    }

    public void update(Long id, CommentRequestDto requestDto, User user) {
        Comment comment = commentRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        comment.update(requestDto.getContent());
    }

    public void delete(Long id, User user) {
        commentRepository.deleteById(id);
    }
}
