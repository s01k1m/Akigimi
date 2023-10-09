package com.kangkimleekojangcho.akgimi.common.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@RequiredArgsConstructor
@Getter
@ConfigurationProperties("notification.mattermost")
public class MattermostProperties {
    private final boolean mmEnabled;
    private final String webhookUrl;
    private final String channel;
    private final String pretext;
    private final String color;
    private final String authorName;
    private final String authorIcon;
    private final String title;
    private final String text;
    private final String footer;
}
