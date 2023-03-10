package com.hanghaemini4.jaranghalram.service;

import com.hanghaemini4.jaranghalram.dto.PostOneResponseDto;
import com.hanghaemini4.jaranghalram.dto.PostRequestDto;
import com.hanghaemini4.jaranghalram.dto.PostResponseDto;
import com.hanghaemini4.jaranghalram.dto.ResponseDto;
import com.hanghaemini4.jaranghalram.entity.Post;
import com.hanghaemini4.jaranghalram.entity.User;
import com.hanghaemini4.jaranghalram.exceptionHandler.CustomException;
import com.hanghaemini4.jaranghalram.exceptionHandler.ErrorCode;
import com.hanghaemini4.jaranghalram.repository.PostLikeRepository;
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
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final S3Uploader s3Uploader;

    @Transactional
    public ResponseDto<List<PostResponseDto>> getPostList(int page, int size, String sortBy, User user) {

        Sort sort = Sort.by(Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Post> postPage = postRepository.findAll(pageable);

        List<Post> posts = postPage.getContent();
        List<PostResponseDto> dtoList = new ArrayList<>();

        for (Post post : posts) {
            boolean isLiked = false;
            PostResponseDto responseDto = PostResponseDto.of(post);
            if(user != null) { // 로그인 했을 때 좋아요 여부 체크
                isLiked = postLikeRepository.findByPostIdAndUserId(post.getId(),user.getId()).isPresent();
            }
            responseDto.setLiked(isLiked);
            dtoList.add(responseDto);
        }
        return ResponseDto.success(dtoList);
    }

    public ResponseDto<List<PostResponseDto>> getLikedPostList(User user) {
        List<Post> posts = postRepository.findAll();
        List<PostResponseDto> dto = new ArrayList<>();
        for (Post post : posts) {
            PostResponseDto responseDto = PostResponseDto.of(post);
            responseDto.setLiked(postLikeRepository.findByPostIdAndUserId(post.getId(), user.getId()).isPresent());
            dto.add(responseDto);
        }
        return ResponseDto.success(dto);
    }

    @Transactional
    public ResponseDto<PostOneResponseDto> getPost(Long postId, User user) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new CustomException(ErrorCode.NotFoundPost));
        PostOneResponseDto postOneResponseDto = new PostOneResponseDto(post);
        boolean isLiked = false;
        if(user != null) {
            isLiked = postLikeRepository.findByPostIdAndUserId(post.getId(),user.getId()).isPresent();
        }
        postOneResponseDto.setLiked(isLiked);
        return ResponseDto.success(postOneResponseDto);
    }

    @Transactional
    public ResponseDto<List<PostResponseDto>> getUserPost(User user) {
        List<Post> myPost = postRepository.findAllByUserId(user.getId());
        List<PostResponseDto> posts = new ArrayList<>();
        for (Post post : myPost) {
            PostResponseDto dto = PostResponseDto.of(post);
            dto.setLiked(postLikeRepository.findByPostIdAndUserId(post.getId(),user.getId()).isPresent());
            posts.add(dto);
        }
        return ResponseDto.success(posts);
    }

    public ResponseDto<String> create(PostRequestDto requestDto, MultipartFile multipartFile, User user) throws IOException {
        String imageUrl = s3Uploader.uploadFiles(multipartFile, "images");
        postRepository.save(new Post(requestDto, imageUrl, user));

        return ResponseDto.success("게시물 등록 성공");
    }

    @Transactional
    public ResponseDto<String> update(Long postId, PostRequestDto requestDto, MultipartFile multipartFile, User user) throws IOException {
        Post post = postRepository.findById(postId).orElseThrow(() -> new CustomException(ErrorCode.NotFoundPost));
        String imageUrl = s3Uploader.uploadFiles(multipartFile, "images");
        if (user.getUserName().equals(post.getUser().getUserName())) {
            post.update(requestDto, imageUrl);
            return ResponseDto.success("게시물 수정 성공");
        } else {
            throw new CustomException(ErrorCode.NoModifyPermission);
        }
    }

    @Transactional
    public ResponseDto<String> delete(Long postId, User user) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new CustomException(ErrorCode.NotFoundPost));
        if (user.getUserName().equals(post.getUser().getUserName())) {
            postRepository.deleteById(postId);
            return ResponseDto.success("게시물 삭제 성공");
        } else {
            throw new CustomException(ErrorCode.NoDeletePermission);
        }
    }
}
