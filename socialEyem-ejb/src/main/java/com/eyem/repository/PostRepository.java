/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eyem.repository;
import com.eyem.entity.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
/**
 *
 * @author YSF
 */
@Repository
public interface PostRepository extends MongoRepository<Post, Long>{
    
}
