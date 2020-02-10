package com.lenovo.xpaas.sessions.controller;

import com.lenovo.xpaas.sessions.documents.Session;
import com.lenovo.xpaas.sessions.service.SessionSearch;
import com.lenovo.xpaas.sessions.service.SessionService;
import com.lenovo.xpaas.sessions.validation.Status;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
public class SessionController {

private SessionService sessionsService;



    @Autowired
    public SessionController(SessionService sessionsService){ this.sessionsService = sessionsService;}

    @GetMapping("/sessions")
    public ResponseEntity<Page<Session>> findAll(@RequestParam(value = "scenarioName", required = false) @ApiParam("Scenario name of the session") String scenarioName,
                                                 @RequestParam(value = "application", required = false) @ApiParam("Application that the test case is for") String application,
                                                 @RequestParam(value = "browser", required = false) @ApiParam("The browser used in scenario") String browser,
                                                 @RequestParam(value = "os", required = false) @ApiParam("The operating system used in scenario") String os,
                                                 @RequestParam(value = "status", required = false) @ApiParam("The pass fail status of scenario") Status status,
                                                 @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @ApiParam("The date the scenario was recorded") LocalDateTime startDate,
                                                 @ApiParam(value = "Pagination") Pageable pageable) {

        SessionSearch sessionSearch = buildSessionSearch(application, scenarioName, browser, os, status, startDate);
        return ResponseEntity.ok(sessionsService.findAll(sessionSearch, pageable));
    }


    @GetMapping("/sessions/{id}")
    public ResponseEntity<Session> getSession(@PathVariable("id") String sessionId) {

        Session theSession = sessionsService.findById(sessionId);

        if (theSession == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

       return ResponseEntity.ok(theSession);
    }

    @PostMapping("/sessions")
    public ResponseEntity<Session> addSession(@RequestBody Session theSession) {

        sessionsService.save(theSession);

        return ResponseEntity.ok(theSession);
    }

    @DeleteMapping("/sessions/{id}")
    public ResponseEntity<String> deleteSession(@PathVariable("id") String sessionId) {

        Session session = sessionsService.findById(sessionId);

        if (session == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        sessionsService.deleteById(sessionId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private SessionSearch buildSessionSearch(String application, String scenarioName, String browser, String os, Status status, LocalDateTime startDate){
        SessionSearch sessionSearch = new SessionSearch();
        sessionSearch.setApplication(application);
        sessionSearch.setScenarioName(scenarioName);
        sessionSearch.setBrowser(browser);
        sessionSearch.setOperatingSystem(os);
        sessionSearch.setStatus(String.valueOf(status));
        sessionSearch.setStartDate(startDate);

        return sessionSearch;
    }
}
