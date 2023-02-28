package com.example.CLP_Case_Study.services;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public class ProductService {

    public List<Post> getAll() {
        return this.postRepository.findAll();
    }
    public Optional<Post> findById(int id) {
        return postRepository.findById(id);
    }
    @Transactional
    public Post addComment(Post post, Post comment) {
        List<Post> commentList = post.getComments();
        commentList.add(comment);
        post.setComments(commentList);
        return post;
    }

    @Transactional
    public Post deleteComment(Post post, Post comment) {
        List<Post> commentList = post.getComments();
        commentList.remove(comment);
        post.setComments(commentList);
        deletePost(comment.getId());
        return post;
    }
}
