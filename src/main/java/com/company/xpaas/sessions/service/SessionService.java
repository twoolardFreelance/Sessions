package com.company.xpaas.sessions.service;

import com.company.xpaas.sessions.dao.SessionsRepository;
import com.company.xpaas.sessions.documents.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class SessionService {
    private final Logger logger = LoggerFactory.getLogger(SessionService.class);

    private SessionsRepository sessionsRepository;

   @Autowired
   public SessionService(SessionsRepository sessionsRepository){ this.sessionsRepository = sessionsRepository;}

    public Page<Session> findAll(SessionSearch sessionSearch, @PageableDefault(sort = {"startDate"}, direction = Sort.Direction.ASC, value = 25) Pageable pageable) {

       Query dynamicQuery = new Query();
        final List<Criteria> criteria = new ArrayList<>();

        if(sessionSearch.getScenarioName() != null) {
            criteria.add(Criteria.where("scenarioName").regex(sessionSearch.getScenarioName()));
        }
        if(sessionSearch.getApplication() != null) {
            criteria.add(Criteria.where("application").regex(sessionSearch.getApplication()));
        }
        if(sessionSearch.getBrowser() != null) {
            criteria.add(Criteria.where("browser").regex(sessionSearch.getBrowser()));
        }
        if(sessionSearch.getOperatingSystem() != null) {
            criteria.add(Criteria.where("operatingSystem").regex(sessionSearch.getOperatingSystem()));
        }
        if(sessionSearch.getStatus().equals("PASSED") || sessionSearch.getStatus().equals("FAILED")) {
            criteria.add(Criteria.where("status").is(sessionSearch.getStatus()));
        }
        if(sessionSearch.getStartDate() != null) {
            criteria.add(Criteria.where("startDate").gte(sessionSearch.getStartDate()));
        }
        if(!criteria.isEmpty()) {
            dynamicQuery.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])));

          return  sessionsRepository.query(dynamicQuery, pageable);

        } else {

            return sessionsRepository.findAll(pageable);
        }

    }

    public Session findById(String id) {
        Optional<Session> result = sessionsRepository.findById(id);

        Session session;

        if (result.isPresent()) {
            session = result.get();
        } else {
            throw new RuntimeException("Did not find Session id - " + id);
        }

        return session;
    }

    public void save(Session session) {
        try {
            sessionsRepository.save(session);

        } catch(Exception e) {
            logger.error("MongoDB Insert Error: {}", e.getMessage());
        }
    }

    public void deleteById(String id) {
        try {
            sessionsRepository.deleteById(id);

        } catch(Exception e) {
            logger.error("MongoDB Delete Error: {}", e.getMessage());
        }
    }
}
