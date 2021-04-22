package com.company.todos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Optional;

@Configuration
public class AppProperties {

    @Autowired
    Environment env;

    public Optional<String> getAppMessage() {
        return Optional.ofNullable(env.getProperty("app.message"));
    }

    public Optional<String> getTokenSecret() {
        return Optional.ofNullable(env.getProperty("jwt.signing.key.secret"));
    }

    public Optional<String> getTokenExpirationTime() {
        return Optional.ofNullable(env.getProperty("jwt.token.expiration.in.seconds"));
    }
}
