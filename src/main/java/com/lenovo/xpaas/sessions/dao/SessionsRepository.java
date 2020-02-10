package com.lenovo.xpaas.sessions.dao;

import com.lenovo.xpaas.sessions.documents.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SessionsRepository extends MongoRepository<Session, String>, SessionsRepositoryCustom {


    Page<Session> findAll(Pageable pageable);

}
