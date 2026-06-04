package dev.sid.arti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Map;


import java.net.*;

public class TodoMcpApp {

    private static final String BASE_URL =
            "https://api.todoist.com/api/v1";

    private static final String TOKEN =
            System.getenv("TODOLIST_API_TOKEN");

private static Map<String, String> getHeaders() {

    if (TOKEN == null || TOKEN.isEmpty()) {
        throw new RuntimeException("MISSING API TOKEN");
    }

    Map<String, String> headers = new HashMap<>();

    headers.put(
            "Authorization",
            "Bearer " + TOKEN
    );

    headers.put(
            "Content-Type",
            "application/json"
    );

    return headers;
}
		}




public class ArtiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArtiApplication.class, args);
	}

}
