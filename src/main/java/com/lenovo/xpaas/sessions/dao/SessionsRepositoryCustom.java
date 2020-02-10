package com.lenovo.xpaas.sessions.dao;

import com.lenovo.xpaas.sessions.documents.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionsRepositoryCustom {

    Page<Session> query(Query query, Pageable pageable);

}
