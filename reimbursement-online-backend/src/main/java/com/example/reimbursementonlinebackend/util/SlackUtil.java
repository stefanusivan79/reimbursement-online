package com.example.reimbursementonlinebackend.util;

import com.example.reimbursementonlinebackend.service.dto.SlackDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class SlackUtil {

    @Value("${slack.webhook.url}")
    private String slackWebhookUrl;

    public void sendMessage(String txt) {
        try {
            log.info("START SEND MESSAGE");
            RestTemplate restTemplate = new RestTemplate();

            HttpEntity<SlackDTO> request = new HttpEntity<>(new SlackDTO(txt));
            ResponseEntity<String> response = restTemplate.exchange(slackWebhookUrl, HttpMethod.POST, request, String.class);
            log.info("DONE SEND MESSAGE");
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }

    }
}
