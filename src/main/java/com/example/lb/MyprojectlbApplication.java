package com.example.lb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@SpringBootApplication
public class MyprojectlbApplication{

	public static void main(String[] args) {
		if (!Desktop.isDesktopSupported()) {
			System.out.println("This app needs a desktop manager to run, exiting.");
			System.exit(1);
		}
		new SpringApplicationBuilder(MyprojectlbApplication.class).headless(false).run(args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void openBrowserAfterStartup() throws IOException, URISyntaxException {
		// open default browser after start:
		Desktop.getDesktop().browse(new URI("http://localhost:8080/log_in_system"));
	}



}
