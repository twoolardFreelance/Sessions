package com.lenovo.xpaas.sessions.documents;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lenovo.xpaas.sessions.validation.Enum;
import com.lenovo.xpaas.sessions.validation.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection = "sessions")
public class Session {

    @Id
    private String sessionId;
    @NotNull
    private String application;
    @NotNull
    private String scenarioName;
    @NotNull
    private String browser;
    @NotNull
    private String operatingSystem;
    @Enum(enumClass = Status.class, ignoreCase = true)
    private String status;
    @NotNull
    private LocalDateTime startDate;
    @NotNull
    private String videoURL;
}
