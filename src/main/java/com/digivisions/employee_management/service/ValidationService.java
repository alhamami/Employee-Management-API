package com.digivisions.employee_management.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ValidationService {

    private static final Logger logger = LoggerFactory.getLogger(ValidationService.class);

    @Autowired
    private RateLimitingService rateLimitingService;

    private final WebClient emailWebClient;
    private final WebClient departmentWebClient;
    private final String apiKey = "03bf11bb0c377d7e79ff4ad421dc0e48";

    public ValidationService(WebClient.Builder webClientBuilder) {
        this.emailWebClient = webClientBuilder.baseUrl("https://apilayer.net/api").build();
        this.departmentWebClient = webClientBuilder.baseUrl("http://localhost:8080").build();
    }

    public boolean validateEmail(String email) {

        logger.info("ValidationService: validating email");

        if (rateLimitingService.Consume() == false) {
            logger.warn("Rate limit exceeded for validate email call");
            throw new RuntimeException("Rate limit exceeded, please try again");
        }

        String response = emailWebClient.get()
                .uri(uriBuilder -> uriBuilder.path("/check").queryParam("access_key", apiKey)
                        .queryParam("email", email).build()).retrieve()
                .bodyToMono(String.class).block();

        try {
            logger.info("response retrieved: "+response);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response);

            boolean isValidFormat = rootNode.path("format_valid").asText().equalsIgnoreCase("true");
            boolean isValidSmtp = rootNode.path("smtp_check").asText().equalsIgnoreCase("true");

            logger.info("Email Format validation status: "+isValidFormat);
            logger.info("Email Smtp validation status: "+isValidSmtp);

            logger.info("Email has been validated");
            return isValidFormat && isValidSmtp;

        } catch (Exception exception) {
            logger.error("Error while validating email and the error message: "+ exception.getMessage());
            throw new RuntimeException("Response parsing failed", exception);
        }
    }

    public boolean validateDepartment(String department) {

        logger.info("ValidationService: validating department");

        if (rateLimitingService.Consume() == false) {
            logger.warn("Rate limit exceeded for validate department call");
            throw new RuntimeException("Rate limit exceeded, please try again");
        }

        try {

            Boolean isValidDepartment = departmentWebClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/department")
                            .queryParam("department", department).build()).retrieve()
                    .bodyToMono(Boolean.class).block();

            logger.info("Department validation status: "+isValidDepartment);

            logger.info("Department has been validated");

            return isValidDepartment.booleanValue();

        } catch (Exception exception) {
            logger.error("Error while validating eepartment and the error message: "+ exception.getMessage());
            throw new RuntimeException("Response parsing failed", exception);
        }
    }
}
