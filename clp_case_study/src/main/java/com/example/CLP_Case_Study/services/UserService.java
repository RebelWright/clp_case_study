package com.example.CLP_Case_Study.services;

import com.example.CLP_Case_Study.models.User;
import com.example.CLP_Case_Study.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findByCredentials(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    /*public List<User> getAll() {
        return userRepository.findAll();
    }*/

    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }

    /*public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }*/

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    //Modify this to add product to order. Then take the similar logic and do it in the OrderService
    @Transactional
    public List<User> addFollower(User followed, User follower) {
        List<User> followSuccess = new ArrayList();

        List<User> newFollowers = followed.getFollowers();
        newFollowers.add(follower);
        followed.setFollowers(newFollowers);

        List<User> newFollowing = follower.getFollowing();
        newFollowing.add(followed);
        follower.setFollowing(newFollowing);

        followSuccess.add(followed);
        followSuccess.add(follower);
        return followSuccess;
    }

    @Transactional
    public List<User> removeFollower(User followed, User follower) {
        List<User> followSuccess = new ArrayList();

        List<User> newFollowers = followed.getFollowers();
        newFollowers.remove(follower);
        followed.setFollowers(newFollowers);

        List<User> newFollowing = follower.getFollowing();
        newFollowing.remove(followed);
        follower.setFollowing(newFollowing);

        followSuccess.add(followed);
        followSuccess.add(follower);
        return followSuccess;
    }

    //modify this to be get products for order
    public List<Post> getFeedForUser(User user) {
        List<User> following = user.getFollowing();
        Optional<List<Post>> feedOptional = postService.getFeedForUser(following);
        if (!feedOptional.isPresent()) {
            return null;
        }
        return feedOptional.get();
    }

    public Optional<List<Post>> getAllPostsByAUser(User user) {
        return postService.getAllPostsByUser(user);
    }
}

