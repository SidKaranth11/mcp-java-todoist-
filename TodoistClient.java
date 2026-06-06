package dev.sid.arti;


import java.util.*;
import java.net.*;
import java.io.*;

public class TodoistClient {



    private static final String BASE_URL =
            "https://api.todoist.com/api/v1";

    private static final String TOKEN =
            System.getenv("TODOLIST_API_TOKEN");

  //environemt check for token

    private static Map<String, String> getHeaders() {

        if (TOKEN == null || TOKEN.isEmpty()) {
            throw new RuntimeException(
                    "MISSING API TOKEN: Set TODOLIST_API_TOKEN in environment variables"
            );
        }

        Map<String, String> headers =
                new HashMap<>();

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

    // header to connection
   

    private static void applyHeaders(
            HttpURLConnection conn
    ) {

        Map<String, String> headers =
                getHeaders();

        for (Map.Entry<String, String> entry
                : headers.entrySet()) {

            conn.setRequestProperty(
                    entry.getKey(),
                    entry.getValue()
            );
        }
    }

    //create function

    public static String createTask(
            String content,
            String description,
            String dueString
    ) throws Exception {

        URL url =
                URI.create(BASE_URL + "/tasks")
                        .toURL();

        HttpURLConnection conn =
                (HttpURLConnection)
                        url.openConnection();

        conn.setRequestMethod("POST");
        conn.setDoOutput(true);

        applyHeaders(conn);

        String json =
                "{"
                        + "\"content\":\"" + content + "\""
                        + (description != null
                        ? ",\"description\":\"" + description + "\""
                        : "")
                        + (dueString != null
                        ? ",\"due_string\":\"" + dueString + "\""
                        : "")
                        + "}";

        OutputStream os =
                conn.getOutputStream();

        os.write(json.getBytes());
        os.close();

        BufferedReader reader =
                new BufferedReader(
                        new InputStreamReader(
                                conn.getInputStream()
                        )
                );

        StringBuilder response =
                new StringBuilder();

        String line;

        while ((line = reader.readLine()) != null) {
            response.append(line);
        }

        reader.close();

        return response.toString();
    }

//Delete function

    public static String deleteTask(
            String taskId
    ) throws Exception {

        URL url =
                URI.create(BASE_URL + "/tasks/" + taskId)
                        .toURL();

        HttpURLConnection conn =
                (HttpURLConnection)
                        url.openConnection();

        conn.setRequestMethod("DELETE");

        // 👇 SAME HEADER LOGIC USED HERE TOO
        applyHeaders(conn);

        int status =
                conn.getResponseCode();

        return "Deleted task "
                + taskId
                + " (status: "
                + status
                + ")";
    }
}