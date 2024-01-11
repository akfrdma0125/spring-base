package com.example.base.post.service;


import com.example.base.common.domain.exception.ResourceNotFoundException;
import com.example.base.common.service.port.ClockHolder;
import com.example.base.post.controller.port.PostService;
import com.example.base.post.domain.Post;
import com.example.base.post.domain.PostCreate;
import com.example.base.post.domain.PostUpdate;
import com.example.base.post.service.port.PostRepository;
import com.example.base.user.domain.User;
import com.example.base.user.service.port.UserRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Builder
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ClockHolder clockHolder;

    public Post getById(long id) {
        return postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Posts", id));
    }

    public Post create(PostCreate postCreate) {
        User user =userRepository.getById(postCreate.writerId());
        return postRepository.save(Post.from(postCreate, user, clockHolder));
    }

    public Post update(long id, PostUpdate postUpdate) {
        Post post = getById(id);
        post = post.update(postUpdate, clockHolder);
        return postRepository.save(post);
    }
}