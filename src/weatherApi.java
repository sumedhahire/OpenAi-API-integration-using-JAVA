
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class weatherApi {
    public static void main(String[] args) throws IOException {
        InputStreamReader r=new InputStreamReader(System.in);
        BufferedReader br=new BufferedReader(r);
        System.out.println("Prompt:");
        String text=br.readLine();
        HttpClient httpClient= HttpClientBuilder.create().build();
        try{
            //HttpResponse response=httpClient.execute(httpGet);
            HttpPost request = new HttpPost("https://api.openai.com/v1/completions");
            request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer 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");
            request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");

            JSONObject data = new JSONObject();
            data.put("model", "text-davinci-003");
            data.put("prompt", text);
            data.put("max_tokens", 4000);
            data.put("temperature", 1.0);
            // Set the request body
            JSONArray messagesArray = new JSONArray();

            // Create a message object
            JSONObject messageObject = new JSONObject();
            messageObject.put("role", "system");
            messageObject.put("content", "You are a helpful assistant.");

            // Add the message object to the messages array
            messagesArray.put(messageObject);

            //data.put("messages",messagesArray);
            StringEntity requestEntity = new StringEntity(data.toString());
            request.setEntity(requestEntity);

            HttpResponse response=httpClient.execute(request);
            int statusCode=response.getStatusLine().getStatusCode();
            System.out.println(statusCode);

            String responseBody= EntityUtils.toString(response.getEntity());
            // Print the formatted response body
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(responseBody, JsonObject.class);
            String answer = jsonObject.getAsJsonArray("choices").get(0).getAsJsonObject().get("text").getAsString();
            System.out.println(answer);
        }catch (Exception ex){
            System.out.println(ex);
        }
    }
}
