package com.example.api.repository;

import com.example.api.model.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends CrudRepository<Post, Integer> {

    Optional<Post> findById(Integer id);

    List<Post> findAll();
}
