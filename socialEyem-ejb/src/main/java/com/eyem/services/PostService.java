/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eyem.services;

import com.eyem.entity.Post;
import com.eyem.repository.PostRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service

public class PostService {

    @Autowired
    private PostRepository postRepository;

    public List<Post> buscarTodos() {
        return postRepository.findAll();
    }

    public boolean crearPost(Post p) {
        postRepository.insert(p);
        return true;
    }

    public List<Post> findAllPostByEmailUser(String emailUser) {
        return postRepository.findAllPostByEmailUser(emailUser);
    }

    public List<Post> findPostUser(String emailUser, String tipo) {
        return postRepository.findPostUser(emailUser, tipo);
    }

    public List<Post> findPublicPost() {
        List<Post>lista = postRepository.findPublicPost();
        return lista;
        //return postRepository.findAll();
    }

    public List<Post> findPostReplicados(String email) {
        return postRepository.findPostReplicados(email);
    }
    
    public boolean replicarPost(Post po){
        postRepository.delete(po);
        postRepository.insert(po);
        return true;
    }
    public void deletePost(Post p){
    postRepository.delete(p);
    }
    public Post findPostById(Long postId){
    return postRepository.findPostById(postId);
    
    }
    public void deletePostById(Long postId){
    postRepository.delete(postRepository.findPostById(postId));
    
    }
}
