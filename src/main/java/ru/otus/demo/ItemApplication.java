package ru.otus.demo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class ItemApplication {

	private final RequestMappingHandlerMapping handlerMapping;

	public static void main(String[] args) {
		SpringApplication.run(ItemApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void logEndpoints() {
		log.info("===== REGISTERED HTTP ENDPOINTS =====");

		handlerMapping
			.getHandlerMethods()
			.forEach((key, value) -> log.info("{} {}", key.getMethodsCondition(), key.getPathPatternsCondition()));

		log.info("===== END ENDPOINTS =====");
	}

}
