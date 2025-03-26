package com.convertino.hire.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterviewResponseDTO {
    private long id;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;
//    private List<MessageResponseDTO> messages;
    private long userId;
    private long jobPositionId;
}