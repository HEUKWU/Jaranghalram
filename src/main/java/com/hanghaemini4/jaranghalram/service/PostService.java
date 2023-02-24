package com.hanghaemini4.jaranghalram.service;

import com.hanghaemini4.jaranghalram.dto.PostRequestDto;
import com.hanghaemini4.jaranghalram.dto.PostResponseDto;
import com.hanghaemini4.jaranghalram.dto.ResponseDto;
import com.hanghaemini4.jaranghalram.entity.Post;
import com.hanghaemini4.jaranghalram.entity.User;
import com.hanghaemini4.jaranghalram.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    public ResponseDto<List<PostResponseDto>> getPostList(int page, int size, String sortBy) {

        Sort.Direction direction = Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Post> postPage = postRepository.findAll(pageable);

        Iterator<Post> iterator = postPage.iterator();
        List<PostResponseDto> dtoList = new ArrayList<>();

        while (iterator.hasNext()) {
            dtoList.add(PostResponseDto.of(iterator.next()));
        }
        return ResponseDto.success(dtoList);
    }

    @Transactional
    public ResponseDto<String> updatePost(Long postId, PostRequestDto requestDto, User user) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NullPointerException("게시글이 없음"));
        if(user.getUserName().equals(post.getUser().getUserName())) {
            post.update(requestDto);
            return ResponseDto.success("수정 성공");
        }else {
            throw new IllegalArgumentException("작성자만 수정 가능");
        }
    }

    @Transactional
    public ResponseDto<String> deletePost(Long postId, User user) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NullPointerException("게시글이 없음"));
        if(user.getUserName().equals(post.getUser().getUserName())) {
            postRepository.deleteById(postId);
            return ResponseDto.success("삭제 성공");
        }else {
            throw new IllegalArgumentException("작성자만 삭제 가능");
        }
    }

    public ResponseDto<PostResponseDto> getPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NullPointerException("게시글이 없음"));
        return ResponseDto.success(PostResponseDto.of(post));
    }
}
