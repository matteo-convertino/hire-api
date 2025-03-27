package com.convertino.hire.service;


import com.convertino.hire.dto.request.MessageRequestDTO;
import com.convertino.hire.model.Interview;
import com.convertino.hire.model.Message;
import com.convertino.hire.model.User;

import java.util.List;


public interface GeminiService {
    MessageRequestDTO generateResponse(User user, Interview interview, List<Message> messagesHistory);
}
