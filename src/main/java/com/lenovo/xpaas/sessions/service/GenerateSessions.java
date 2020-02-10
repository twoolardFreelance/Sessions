package com.lenovo.xpaas.sessions.service;

import com.lenovo.xpaas.sessions.documents.Session;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class GenerateSessions {

    private MongoTemplate mongoTemplate;

    @Autowired
    public GenerateSessions (MongoTemplate mongoTemplate){ this.mongoTemplate = mongoTemplate;}


    public void createSession(SessionSearch sessionSearch, int count) {
        for (int i=0; i < count; i++){

            Session session = new Session();
            session.setSessionId(generateRandomId());
            session.setApplication(sessionSearch.getApplication());
            session.setScenarioName(sessionSearch.getScenarioName());
            session.setBrowser(sessionSearch.getBrowser());
            session.setStartDate(sessionSearch.getStartDate());
            session.setOperatingSystem(sessionSearch.getOperatingSystem());
            session.setStatus(sessionSearch.getStatus());
            session.setVideoURL("https://www.youtube.com/watch?v=of8J5AeJfRs");

            insertMongo(session);
        }
    }

    private void insertMongo(Session session) {
        mongoTemplate.save(session, "sessions");
    }

    private String generateRandomId(){
        return RandomStringUtils.randomAlphanumeric(32);
    }

}
