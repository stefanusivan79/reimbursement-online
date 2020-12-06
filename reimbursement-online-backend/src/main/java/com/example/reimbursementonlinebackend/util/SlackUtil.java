package com.example.reimbursementonlinebackend.util;

import com.example.reimbursementonlinebackend.service.dto.SlackDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.vault.core.VaultKeyValueOperationsSupport;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultResponse;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class SlackUtil {

    private VaultTemplate vaultTemplate;

    public SlackUtil(VaultTemplate vaultTemplate) {
        this.vaultTemplate = vaultTemplate;
    }

    public void sendMessage(String txt) {
        try {
            VaultResponse vault = vaultTemplate.opsForKeyValue("secret", VaultKeyValueOperationsSupport.KeyValueBackend.KV_2)
                    .get("eReimbursement");
            if (null != vault && null != vault.getData() && null != vault.getData().get("slack.webhook.url")) {
                String slackWebhookUrl = String.valueOf(vault.getData().get("slack.webhook.url"));
                log.info("START SEND MESSAGE");
                RestTemplate restTemplate = new RestTemplate();

                HttpEntity<SlackDTO> request = new HttpEntity<>(new SlackDTO(txt));
                ResponseEntity<String> response = restTemplate.exchange(slackWebhookUrl, HttpMethod.POST, request, String.class);
                log.info("DONE SEND MESSAGE");
            }
            else {
                log.error("URL NOT FOUND");
            }
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }

    }
}
