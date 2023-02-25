package com.hanghaemini4.jaranghalram.service;

import com.hanghaemini4.jaranghalram.dto.PostOneResponseDto;
import com.hanghaemini4.jaranghalram.dto.PostRequestDto;
import com.hanghaemini4.jaranghalram.dto.PostResponseDto;
import com.hanghaemini4.jaranghalram.dto.ResponseDto;
import com.hanghaemini4.jaranghalram.entity.Post;
import com.hanghaemini4.jaranghalram.entity.PostLike;
import com.hanghaemini4.jaranghalram.entity.User;
import com.hanghaemini4.jaranghalram.repository.PostLIkeRepository;
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
    private final PostLIkeRepository postLIkeRepository;
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
            boolean isLiked = false;
            List<PostLike> postLikeList = postLIkeRepository.findAllByPostId(post.getId());
            if(user != null) { // 로그인 했을 때 좋아요 여부 체크
                isLiked = isLiked(postLikeList, user);
            }
            PostResponseDto responseDto = PostResponseDto.of(post);
            responseDto.setLiked(isLiked);
            dtoList.add(responseDto);
        }
        return ResponseDto.success(dtoList);
    }

    @Transactional
    public ResponseDto<PostOneResponseDto> getPost(Long postId, User user) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NullPointerException("게시글이 없음"));
        List<PostLike> postLikeList = postLIkeRepository.findAllByPostId(postId);
        boolean isLiked = false;
        if(user != null) {
            isLiked = isLiked(postLikeList, user);
        }
        PostOneResponseDto postOneResponseDto = new PostOneResponseDto(post);
        postOneResponseDto.setLiked(isLiked);
        return ResponseDto.success(postOneResponseDto);
    }

    public ResponseDto<String> addPost(PostRequestDto requestDto, User user) throws IOException {
        String imageUrl = s3Uploader.uploadFiles(requestDto.getMultipartFile(), "images");
        postRepository.save(new Post(requestDto, imageUrl, user));

        return ResponseDto.success("업로드 성공");
    }

    @Transactional
    public ResponseDto<String> updatePost(Long postId, PostRequestDto requestDto, User user) throws IOException {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NullPointerException("게시글이 없음"));
        String imageUrl = s3Uploader.uploadFiles(requestDto.getMultipartFile(), "images");
        if (user.getUserName().equals(post.getUser().getUserName())) {
            post.update(requestDto, imageUrl);
            return ResponseDto.success("수정 성공");
        } else {
            throw new IllegalArgumentException("작성자만 수정 가능");
        }
    }

    @Transactional
    public ResponseDto<String> deletePost(Long postId, User user) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NullPointerException("게시글이 없음"));
        if (user.getUserName().equals(post.getUser().getUserName())) {
            postRepository.deleteById(postId);
            return ResponseDto.success("삭제 성공");
        } else {
            throw new IllegalArgumentException("작성자만 삭제 가능");
        }
    }

    public boolean isLiked(List<PostLike> postLikeList, User user) {
        boolean isLiked = false;
        for(PostLike like : postLikeList) {
            if(like.getUser().getUserName().equals(user.getUserName())) {
                isLiked = true;
                break;
            }
        }
        return isLiked;
    }
}
