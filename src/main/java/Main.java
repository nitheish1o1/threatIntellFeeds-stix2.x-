import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

public class Main {
    private static HttpURLConnection connection;

    public static void main(String[] args) {
        BufferedReader reader;
        String line;
        StringBuilder responceContent = new StringBuilder();
        try {
            URL url = new URL("https://urlhaus-api.abuse.ch/v1/urls/recent/");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            while ((line = reader.readLine()) != null) {

                    responceContent.append(line);

            }
            reader.close();

            //System.out.println(responceContent);
            parse(responceContent.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }

    }

    public static void parse(String responceBody) {


        JSONObject feeds = new JSONObject(responceBody);
        JSONArray urls = feeds.getJSONArray("urls");
        System.out.println("no of feeds"+ urls.length()+"\n");
        for (int i = 0; i < urls.length(); i++) {
            JSONObject feed = urls.getJSONObject(i);
            System.out.println("id : "+feed.getString("id"));
            System.out.println("url : "+feed.getString("url"));
            System.out.println("status : "+feed.getString("url_status"));
            System.out.println("host : "+feed.getString("host"));
            System.out.println("timestamp : "+feed.getString("date_added"));
            System.out.println("threat type : "+feed.getString("threat"));
            System.out.println("reporter : "+feed.getString("reporter"));
            System.out.printf("\n\n\n");
        }
    }
}