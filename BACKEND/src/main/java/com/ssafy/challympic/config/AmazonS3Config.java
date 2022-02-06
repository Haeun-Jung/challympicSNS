package com.ssafy.challympic.util;

import lombok.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonS3Config {

    @Value("${cloud.aws.credentials.access-key")
    private String accessKey;
}
