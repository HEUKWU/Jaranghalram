package com.hanghaemini4.jaranghalram.service;

import com.hanghaemini4.jaranghalram.dto.PostRequestDto;
import com.hanghaemini4.jaranghalram.dto.PostResponseDto;
import com.hanghaemini4.jaranghalram.dto.ResponseDto;
import com.hanghaemini4.jaranghalram.entity.Post;
import com.hanghaemini4.jaranghalram.entity.User;
import com.hanghaemini4.jaranghalram.repository.PostRepository;
import com.hanghaemini4.jaranghalram.s3.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final S3Uploader s3Uploader;
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

    public ResponseDto<String> addPost(PostRequestDto requestDto, MultipartFile multipartFile, User user) throws IOException {

        if (!multipartFile.isEmpty()) {
            String imageUrl = s3Uploader.uploadFiles(multipartFile, "images");
            postRepository.save(new Post(requestDto, imageUrl, user));
        }
        return ResponseDto.success("업로드 성공");
    }
}
