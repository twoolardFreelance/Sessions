package com.company.xpaas.sessions.dao;

import com.company.xpaas.sessions.documents.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;


import java.util.List;

public class SessionsRepositoryImpl implements SessionsRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public SessionsRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Page<Session> query(Query query, Pageable pageable) {
        Query query1 = query.with(pageable);

        List<Session> list = mongoTemplate.find(query1, Session.class);
        return PageableExecutionUtils.getPage(
                list,
                pageable,
                () -> mongoTemplate.count(query1, Session.class));
    }
}
