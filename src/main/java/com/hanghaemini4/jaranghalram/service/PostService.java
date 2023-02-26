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

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final S3Uploader s3Uploader;

    @Transactional
    public ResponseDto<List<PostResponseDto>> getPostList(int page, int size, String sortBy, User user) {

        Sort.Direction direction = Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Post> postPage = postRepository.findAll(pageable);


        Iterator<Post> iterator = postPage.iterator();
        List<PostResponseDto> dtoList = new ArrayList<>();

        while (iterator.hasNext()) {
            Post post = iterator.next();
            PostResponseDto responseDto = PostResponseDto.of(post);
            if(user != null) { // 로그인 했을 때 좋아요 여부 체크
                responseDto.setLiked(postLikeRepository.findByPostIdAndUserId(post.getId(),user.getId()).isPresent());
            }
            dtoList.add(responseDto);
        }
        return ResponseDto.success(dtoList);
    }

    @Transactional
    public ResponseDto<PostOneResponseDto> getPost(Long postId, User user) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new CustomException(ErrorCode.NotFoundPost));
        PostOneResponseDto postOneResponseDto = new PostOneResponseDto(post);
        if(user != null) {
            postOneResponseDto.setLiked(postLikeRepository.findByPostIdAndUserId(post.getId(),user.getId()).isPresent());
        }
        return ResponseDto.success(postOneResponseDto);
    }

    @Transactional
    public ResponseDto<List<PostResponseDto>> getMyList(User user) {
        List<Post> myPost = postRepository.findAllByUserId(user.getId());
        List<PostResponseDto> posts = new ArrayList<>();
        for (Post post : myPost) {
            posts.add(PostResponseDto.of(post));
        }
        return ResponseDto.success(posts);
    }

    public ResponseDto<?> addPost(PostRequestDto requestDto, User user) throws IOException {
        String imageUrl = s3Uploader.uploadFiles(requestDto.getMultipartFile(), "images");
        postRepository.save(new Post(requestDto, imageUrl, user));

        return ResponseDto.success("게시물 등록 성공");
    }

    @Transactional
    public ResponseDto<?> updatePost(Long postId, PostRequestDto requestDto, User user) throws IOException {
        Post post = postRepository.findById(postId).orElseThrow(() -> new CustomException(ErrorCode.NotFoundPost));
        String imageUrl = s3Uploader.uploadFiles(requestDto.getMultipartFile(), "images");
        if (user.getUserName().equals(post.getUser().getUserName())) {
            post.update(requestDto, imageUrl);
            return ResponseDto.success("게시물 수정 성공");
        } else {
            throw new CustomException(ErrorCode.NoModifyPermission);
        }
    }

    @Transactional
    public ResponseDto<?> deletePost(Long postId, User user) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new CustomException(ErrorCode.NotFoundPost));
        if (user.getUserName().equals(post.getUser().getUserName())) {
            postRepository.deleteById(postId);
            return ResponseDto.success("게시물 삭제 성공");
        } else {
            throw new CustomException(ErrorCode.NoDeletePermission);
        }
    }
}
