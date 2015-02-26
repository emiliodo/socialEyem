/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eyem.services;

import com.eyem.entity.Post;
import com.eyem.repository.PostRepository;
import java.util.Collections;
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

    public List<Post> findPublicPost() {
        List<Post> res = postRepository.findPublicPost();
        
        if (res.isEmpty() || res.size() <= 0) {
            return null;
        } else {
            Collections.sort(res);
            return res;
        }
    }

    public List<Post> buscarPostGrupo(Long idGrupo) {
        List<Post> res = postRepository.buscarPostGrupo(idGrupo.toString());
        if (res.isEmpty() || res.size() <= 0) {
            return null;
        } else {
            Collections.sort(res);
            return res;
        }
    }

    public boolean replicarPost(Post po) {
        postRepository.delete(po);
        postRepository.insert(po);
        return true;
    }

    public void deletePost(Post p) {
        postRepository.delete(p);
    }

    public Post findPostById(Long postID) {
        return postRepository.findOne(postID);
    }

    public List<Post> findAllPostByEmailUser(String emailUser) {
        List<Post> res = postRepository.findAllPostByEmailUser(emailUser);
        if (res.isEmpty() || res.size() <= 0) {
            return null;
        } else {
            Collections.sort(res);
            return res;
        }
    }

    public List<Post> findPostUser(String emailUser, String tipo) {
        List<Post> res = postRepository.findPostUser(emailUser, tipo);
        if (res.isEmpty() || res.size() <= 0) {
            return null;
        } else {
            Collections.sort(res);
            return res;
        }
    }

    public List<Post> findPostReplicados(String email) {
        List<Post> res = postRepository.findPostReplicados(email);
        if (res.isEmpty() || res.size() <= 0) {
            return null;
        } else {
            Collections.sort(res);
            return res;
        }
    }

    public boolean reyemAnterior(Long idPost, String email) {
        List<String> reyem = postRepository.reyemAnterior(idPost, email);
        return !(reyem == null || reyem.isEmpty());
    }
}
