package com.example.restaurantmenuapi.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class GracefulShutdownLogger {
    @EventListener
    public void onShutdown(ContextClosedEvent event) {
        log.info("SIGTERM received. Starting graceful shutdown.");
    }
}
