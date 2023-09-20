package com.kangkimleekojangcho.akgimi.common.application;

import com.google.gson.Gson;
import com.kangkimleekojangcho.akgimi.common.application.response.MatterMostMessageDto.Attachment;
import com.kangkimleekojangcho.akgimi.common.application.response.MatterMostMessageDto.Attachments;
import com.kangkimleekojangcho.akgimi.common.config.MattermostProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
@RequiredArgsConstructor
@Log4j2
public class MattermostSender {
    private final MattermostProperties mattermostProperties;

    public void sendMessage(Exception exception, String uri, String params) {
        if (!mattermostProperties.isMmEnabled())
            return;

        try {
            Attachment attachment = Attachment.builder()
                    .channel(mattermostProperties.getChannel())
//                    .authorIcon(mattermostProperties.getAuthorIcon())
                    .authorName(mattermostProperties.getAuthorName())
//                    .color(mattermostProperties.getColor())
                    .pretext("[PRETEXT]")
                    .title(mattermostProperties.getTitle())
                    .text("[TEXT]")
                    .footer("[FOOTER]")
                    .build();

            attachment.addExceptionInfo(exception, uri, params);
            Attachments attachments = new Attachments(attachment);
            attachments.addProps(exception);
            String payload = new Gson().toJson(attachments);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-type", MediaType.APPLICATION_JSON_VALUE);

            HttpEntity<String> entity = new HttpEntity<>(payload, headers);
            new RestTemplate().postForEntity(mattermostProperties.getWebhookUrl(), entity, String.class);

        } catch (Exception e) {
            e.printStackTrace();
            log.error("MM Notification 에러 : {}", e.getMessage());
        }

    }
}
