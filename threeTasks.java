package dev.sid.arti;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TodoistClient {

    private static final String BASE_URL = "https://api.todoist.com/api/v1";
    private static final String TOKEN = System.getenv("TODOLIST_API_TOKEN");

    // One HttpClient shared by everything
    private static final HttpClient CLIENT = HttpClient.newHttpClient();

    // The single place where HTTP happens
    private static String request(String method, String path, String body) throws Exception {
        if (TOKEN == null || TOKEN.isEmpty()) {
            throw new RuntimeException("MISSING API TOKEN: Set TODOLIST_API_TOKEN in environment variables");
        }

        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + path))
                .header("Authorization", "Bearer " + TOKEN)
                .header("Content-Type", "application/json")
                .method(method, body == null
                        ? HttpRequest.BodyPublishers.noBody()
                        : HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> res = CLIENT.send(req, HttpResponse.BodyHandlers.ofString());

        if (res.statusCode() >= 400) {
            throw new RuntimeException("Request failed (status " + res.statusCode() + "): " + res.body());
        }

        return res.body();
    }

    public static String createTask(String content, String description, String dueString) throws Exception {
        StringBuilder json = new StringBuilder("{\"content\":\"" + content + "\"");
        if (description != null) json.append(",\"description\":\"").append(description).append("\"");
        if (dueString != null) json.append(",\"due_string\":\"").append(dueString).append("\"");
        json.append("}");

        return request("POST", "/tasks", json.toString());
    }

    public static String deleteTask(String taskId) throws Exception {
        request("DELETE", "/tasks/" + taskId, null);
        return "Deleted task " + taskId;
    }
  
  public static String updateTask(String taskId , String content , String description , String dueString) throws Exception {
     String json = new StringBuilder();
      json.append("{");
      if(content != null){
        json.append("\"content\":\"").append(content).append("\",");
      }
      if(decription != null){
        json.append("\"description\":\"").append(description).append("\",");
      }
      if(dueString != null){
        json.append("\"due_string\":\"").append(dueString).append("\"");
      }
     json.append("}");
    return request("POST""/tasks/" + taskId,json);
}
}
