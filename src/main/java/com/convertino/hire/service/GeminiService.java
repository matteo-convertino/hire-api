package com.convertino.hire.service;


import com.convertino.hire.config.GoogleAIStudioConfig;
import com.convertino.hire.dto.request.MessageRequestDTO;
import com.convertino.hire.dto.request.ReportRequestDTO;
import com.convertino.hire.model.Interview;
import com.convertino.hire.model.Message;
import com.convertino.hire.model.Skill;
import com.convertino.hire.model.User;
import com.convertino.hire.utils.GeminiPrompts;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import swiss.ameri.gemini.api.Content;
import swiss.ameri.gemini.api.GenAi;
import swiss.ameri.gemini.api.GenerativeModel;
import swiss.ameri.gemini.api.ModelVariant;
import swiss.ameri.gemini.gson.GsonJsonParser;
import swiss.ameri.gemini.spi.JsonParser;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@Transactional(rollbackOn = Exception.class)
public class GeminiService {

    private final String apiKey;
    private final InterviewService interviewService;
    private final ObjectMapper objectMapper;
    private final ReportService reportService;

    public GeminiService(GoogleAIStudioConfig config, InterviewService interviewService, ObjectMapper objectMapper, ReportService reportService) {
        this.apiKey = config.getApiKey();
        this.interviewService = interviewService;
        this.objectMapper = objectMapper;
        this.reportService = reportService;
    }

    public MessageRequestDTO generateResponse(User user, long interviewId, List<Message> messagesHistory) {
        Interview interview = interviewService.findEntityById(interviewId);

        JsonParser parser = new GsonJsonParser();

        try (GenAi genAi = new GenAi(apiKey, parser)) {
            GenerativeModel.GenerativeModelBuilder model = GenerativeModel.builder()
                    .modelName(ModelVariant.GEMINI_1_5_FLASH)
                    .addSystemInstruction(
                            GeminiPrompts.getSystemInstruction(
                                    interview.getJobPosition().getTitle(),
                                    interview.getJobPosition().getDescription(),
                                    interview.getJobPosition().getSkills().stream().map(Skill::getDescription).collect(Collectors.joining("\n")),
                                    interview.getJobPosition().getSkills().stream().map(skill -> skill.getId() + ": " + skill.getDescription()).collect(Collectors.joining("\n")),
                                    interview.getJobPosition().getEvaluationCriteria()
                            )
                    );

            addMessagesHistory(model, messagesHistory);

            GenAi.GeneratedContent response = genAi
                    .generateContent(model.build())
                    .get();

            if (checkAndSaveReport(user, interview, response.text())) {
                interviewService.setAsCompleted(interview);
                return new MessageRequestDTO(interview.getJobPosition().getEndMessage() == null ? GeminiPrompts.getDefaultEndMessage() : interview.getJobPosition().getEndMessage());
            }

            return new MessageRequestDTO(response.text());
        } catch (Exception e) {
            e.printStackTrace();
            throw new com.convertino.hire.exceptions.websocket.GeminiException();
        }
    }

    private void addMessagesHistory(GenerativeModel.GenerativeModelBuilder model, List<Message> messages) {
        messages.forEach(message -> model.addContent(new Content.TextContent(
                message.getRole().roleName(),
                message.getText()
        )));
    }

    private boolean checkAndSaveReport(User user, Interview interview, String response) {
        int reportStart = response.indexOf("REPORT{");

        if (reportStart == -1) return false;

        reportStart += "REPORT".length();
        String reportString = response.substring(reportStart).trim().replaceAll("\\\\\"", "\"");

        Map<Long, Integer> report;

        try {
            report = objectMapper.readValue(reportString, new TypeReference<>() {});
        } catch (Exception e) {
            e.printStackTrace();
            throw new com.convertino.hire.exceptions.websocket.GeminiException();
        }

        report.forEach((skillId, value) -> {
            ReportRequestDTO reportRequestDTO = new ReportRequestDTO(value, interview.getId(), skillId);
            reportService.save(user, reportRequestDTO);
        });

        interviewService.setAsCompleted(interview);

        return true;
    }

}
