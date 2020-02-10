package com.company.department.project.sessions;

import com.company.department.project.sessions.config.MongoConfig;
import com.company.xpaas.sessions.SessionsApplication;
import com.company.xpaas.sessions.service.GenerateSessions;
import com.company.xpaas.sessions.service.SessionSearch;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {SessionsApplication.class, MongoConfig.class}

)
@ActiveProfiles("integration-test")
@Ignore
public class Base {

    @LocalServerPort
    protected int port;

    HttpHeaders headers = new HttpHeaders();

    TestRestTemplate restTemplate = new TestRestTemplate();

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    GenerateSessions generateSessions;

    String createURLWithPort(String URI) {
        return "http://localhost:" + port  + URI;
    }

    SessionSearch generateGenericSession(){
        SessionSearch session = new SessionSearch();
        session.setApplication("something");
        session.setScenarioName("I should not see new services for the following contract types");
        session.setBrowser("chrome");
        session.setStartDate(LocalDateTime.now());
        session.setOperatingSystem("windows");
        session.setStatus("PASSED");
        return session;
    }


}
