package com.convertino.hire.dto.response;

import com.convertino.hire.model.Interview;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import swiss.ameri.gemini.api.Content;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponseDTO {
    private long id;
    private String text;
    private long interviewId;
    private Content.Role role;
}