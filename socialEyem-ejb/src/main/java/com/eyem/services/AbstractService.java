/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eyem.services;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author maramec
 * @param <T>
 * @param <R>
 */

public abstract class AbstractService <T,R extends MongoRepository<T,?>>{
    
    public abstract R getRepository();
    
    public List<T> save(Iterable<T> elements){
        return getRepository().save(elements);
    }
    
    public List<T> findAll(){
        return getRepository().findAll();
    }

    public List<T> findAll(Sort sort){
        return getRepository().findAll(sort);
    }

    public T insert(T element){
        return getRepository().save(element);
    }

    public List<T> insert(Iterable<T> elements){
        return getRepository().save(elements);
    }
}
