package com.convertino.hire.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDTO {

    private LocalDateTime timestamp;
    private int status;
    private String error;
    private Object message; // String or Map<String, String> or Map<String, List<String>>
    private String path;
}