package com.lenovo.dcg.truscale.sessions;

import com.lenovo.xpaas.sessions.documents.Session;
import com.lenovo.xpaas.sessions.service.SessionSearch;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

public class SessionsControllerIT extends Base {

    @Test
    public void createSessions() {
        Session session = new Session();
        session.setSessionId("a414b7a32876582eeb467ee767f49ebe");
        session.setApplication("Something");
        session.setScenarioName("I should not see new services for the following contract types");
        session.setBrowser("chrome");
        session.setStartDate(LocalDateTime.parse("2020-01-07T15:03:15"));
        session.setOperatingSystem("windows");
        session.setStatus("PASSED");
        session.setVideoURL("https://www.youtube.com/watch?v=of8J5AeJfRs");

        ResponseEntity<Session> sessionResponseEntity = restTemplate.exchange(createURLWithPort("/sessions"),
                HttpMethod.POST, new HttpEntity<>(session, headers), Session.class);

        assertNotNull(session);
        assertEquals(HttpStatus.OK, sessionResponseEntity.getStatusCode());
    }

    @Test
    public void getSessionById(){
        SessionSearch sessionSearch = generateGenericSession();
        generateSessions.createSession(sessionSearch, 1);
       List<Session> sessionList = mongoTemplate.findAll(Session.class);

        ResponseEntity<Session> sessionResponseEntity = restTemplate.exchange(createURLWithPort("/sessions/{id}"),
                HttpMethod.GET, new HttpEntity<>(headers), Session.class, sessionList.get(0).getSessionId());

        assertNotNull(sessionResponseEntity);
        assertEquals(HttpStatus.OK, sessionResponseEntity.getStatusCode());
    }

    @Test
    public void deleteSession(){
        SessionSearch sessionSearch = generateGenericSession();
        generateSessions.createSession(sessionSearch, 1);
        List<Session> sessionList = mongoTemplate.findAll(Session.class);

        ResponseEntity<Session> sessionResponseEntity = restTemplate.exchange(createURLWithPort("/sessions/{id}"),
                HttpMethod.DELETE, new HttpEntity<>(headers), Session.class, sessionList.get(0).getSessionId());

        assertNotNull(sessionResponseEntity);
        assertEquals(HttpStatus.NO_CONTENT, sessionResponseEntity.getStatusCode());
        assertTrue(mongoTemplate.findAll(Session.class).isEmpty());
    }


}
