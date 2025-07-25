# What is HIRE?

**HIRE** (**H**uman **I**ntelligence **R**ecruitment **E**ngine) is a cutting-edge HR tech platform designed to **streamline** and **modernize** the **recruitment process** through **artificial intelligence**. It automates candidate
screening and evaluation, providing scalable and objective hiring tools for companies.

# Core Features

- **AI interviewing**: text-based chatbot conducts dynamic interviews tailored to predefined skill set. The AI adapts
  its questions based on the depth and clarity of candidate responses, simulating a realistic and contextual
  conversation.
- **Automated screening**: candidates are automatically assessed and filtered based on skill alignment, reducing manual
  recruiter effort and time-to-hire.
- **HR dashboard**: a user-friendly interface enables recruiters to manage job postings, monitor candidate progress and
  access detailed interview analytics.
- **Job listing interface**: front-end module allows candidates to browse and apply to open roles.

# AI Capabilities

- Customizable question generation based on **HR-defined skill sets**.
- **Context-aware questioning** and follow-ups to dig deeper into candidate competencies.
- **Real-time skill evaluation** with both soft and hard skill focus.
- Engaging candidate experience via **adaptive conversational UI**.

# Links

- Presentation: https://gamma.app/docs/HIRE-Human-Intelligence-Recruitment-Engine-rt665kwi8a0v93k
- Back-end: https://github.com/matteo-convertino/hire-api
- Front-end: https://github.com/matteo-convertino/hire-web

# API Endpoints

The APIs will be available at: http://localhost:8080/api/v1

- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI spec (JSON): http://localhost:8080/v3/api-docs

## Authentication

| Method | Path                         | Response           | Exceptions                                                |
|--------|------------------------------|--------------------|-----------------------------------------------------------|
| POST   | `/api/v1/auth/sign-up`       | `TokenResponseDTO` | `EntityDuplicateException`, `EntityCreationException`     |
| POST   | `/api/v1/auth/sign-up-guest` | `TokenResponseDTO` | `EntityCreationException`                                 |
| POST   | `/api/v1/auth/sign-in`       | `TokenResponseDTO` | `InvalidCredentialsException`,  `EntityNotFoundException` |
| GET    | `/api/v1/auth/user`          | `UserResponseDTO`  |                                                           |

## Web Authentication

| Method | Path                             | Response | Exceptions                                               |
|--------|----------------------------------|----------|----------------------------------------------------------|
| POST   | `/api/v1/web/auth/sign-up`       | `Void`   | `EntityDuplicateException`, `EntityCreationException`    |
| POST   | `/api/v1/web/auth/sign-up-guest` | `Void`   | `EntityCreationException`                                |
| POST   | `/api/v1/web/auth/sign-in`       | `Void`   | `InvalidCredentialsException`, `EntityNotFoundException` |
| GET    | `/api/v1/web/auth/sign-out`      | `Void`   |                                                          |

## Job Position

| Method | Path                         | Response                       | Exceptions                                                                    |
|--------|------------------------------|--------------------------------|-------------------------------------------------------------------------------|
| GET    | `/api/v1/job-positions`      | `List<JobPositionResponseDTO>` |                                                                               |
| GET    | `/api/v1/job-positions/{id}` | `JobPositionResponseDTO`       | `EntityNotFoundException`                                                     |
| GET    | `/api/v1/job-positions/user` | `List<JobPositionResponseDTO>` |                                                                               |
| POST   | `/api/v1/job-positions`      | `JobPositionResponseDTO`       | `EntityCreationException`                                                     |
| PUT    | `/api/v1/job-positions/{id}` | `JobPositionResponseDTO`       | `EntityNotFoundException`, `AccessDeniedException`, `EntityUpdateException`   |
| DELETE | `/api/v1/job-positions/{id}` | `JobPositionResponseDTO`       | `EntityNotFoundException`, `AccessDeniedException`, `EntityDeletionException` |

## Skill

| Method | Path                                          | Response                 | Exceptions                                                                    |
|--------|-----------------------------------------------|--------------------------|-------------------------------------------------------------------------------|
| GET    | `/api/v1/skills/{id}`                         | `SkillResponseDTO`       | `EntityNotFoundException`, `AccessDeniedException`                            |
| GET    | `/api/v1/skills/job-position/{jobPositionId}` | `List<SkillResponseDTO>` | `EntityNotFoundException`, `AccessDeniedException`                            |
| POST   | `/api/v1/skills`                              | `SkillResponseDTO`       | `EntityNotFoundException`, `AccessDeniedException`, `EntityCreationException` |
| PUT    | `/api/v1/skills/{id}`                         | `SkillResponseDTO`       | `EntityNotFoundException`, `AccessDeniedException`, `EntityUpdateException`   |
| DELETE | `/api/v1/skills/{id}`                         | `SkillResponseDTO`       | `EntityNotFoundException`, `AccessDeniedException`, `EntityDeletionException` |

## Interview

| Method | Path                                              | Response                     | Exceptions                                           |
|--------|---------------------------------------------------|------------------------------|------------------------------------------------------|
| GET    | `/api/v1/interviews/user`                         | `List<InterviewResponseDTO>` |                                                      |
| GET    | `/api/v1/interviews/{id}`                         | `InterviewResponseDTO`       | `EntityNotFoundException`, `AccessDeniedException`   |
| GET    | `/api/v1/interviews/job-position/{jobPositionId}` | `List<InterviewResponseDTO>` | `EntityNotFoundException`, `AccessDeniedException`   |
| POST   | `/api/v1/interviews`                              | `InterviewResponseDTO`       | `EntityNotFoundException`, `EntityCreationException` |

## Message

| Method | Path                                       | Response                   | Exceptions                |
|--------|--------------------------------------------|----------------------------|---------------------------|
| GET    | `/api/v1/messages/interview/{interviewId}` | `List<MessageResponseDTO>` | `EntityNotFoundException` |

## Report

| Method | Path                                      | Response                  | Exceptions                |
|--------|-------------------------------------------|---------------------------|---------------------------|
| GET    | `/api/v1/reports/{id}`                    | `ReportResponseDTO`       | `EntityNotFoundException` |
| GET    | `/api/v1/reports/user`                    | `List<ReportResponseDTO>` |                           |
| GET    | `/api/v1/reports/interview/{interviewId}` | `List<ReportResponseDTO>` |                           |

# WebSocket

The APIs will be available at: [ws://localhost:8080/ws](#)

## Topics (client → server)

| Destination                              | Description                          |
|------------------------------------------|--------------------------------------|
| `/app/messages/interview/${interviewId}` | Send message to a specific interview |

## Topics (server → client)

| Destination                            | Description                                           |
|----------------------------------------|-------------------------------------------------------|
| `/user/queue/errors`                   | Private queues for error messages                     |
| `/user/queue/interviews/{interviewId}` | Private queues for messages from a specific interview |
