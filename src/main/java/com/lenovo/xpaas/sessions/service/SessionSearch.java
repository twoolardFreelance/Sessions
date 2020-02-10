package com.lenovo.xpaas.sessions.service;

import com.lenovo.xpaas.sessions.validation.Enum;
import com.lenovo.xpaas.sessions.validation.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionSearch {
    private String application;
    private String scenarioName;
    private String browser;
    private String operatingSystem;
    private String status;
    private LocalDateTime startDate;
}
