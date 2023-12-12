
import com.google.gson.Gson;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Flow;


public class Main {

    static String databaseUrl = "https://mobilt-java-22-default-rtdb.europe-west1.firebasedatabase.app/";

    public static void main(String[] args) throws IOException {
    /*    URL joke = new URL("https://dog.ceo/api/breeds/image/random ");
        URLConnection yc = joke.openConnection();
        BufferedReader in = new BufferedReader( new InputStreamReader(yc.getInputStream()));
        String inputLine;
        do {
            inputLine = in.readLine();
            System.out.println(inputLine);
        }
        while (inputLine  != null);
        in.close();*/
        getRequests("name.json");
        //postRequest("alrik.json");
        //putRequest("alrik/name.json");
        //patchRequest("alrik/name.json");
        //deleteRequests("alrik/name/memo.json");
    }


    public static void getRequests(String databasePath) {
        String databaseUrl = "https://mobilt-java-22-default-rtdb.europe-west1.firebasedatabase.app/";

        try {

            // Create the URL for the HTTP GET request
            //URL url = new URL(databaseUrl + databasePath);
            URL url = new URL("https://mobilt-java-22-default-rtdb.europe-west1.firebasedatabase.app/.json");
            //URL url = new URL("https://www.cheapshark.com/api/1.0/deals?storeID=1&upperPrice=0.5");  // Cheapshark

            // Open a connection to the URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set the request method to GET
            connection.setRequestMethod("GET"); //POST , PATCH , DELETE

            // Get the response code t.ex 400, 404, 200 är ok
            int responseCode = connection.getResponseCode();
            //  System.out.println("response code:" +responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) { // ok är bra
                // Read the response from the InputStream
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Handle the response data
                System.out.println("Response from Firebase Realtime Database:");
                System.out.println(response);
               ta = new TextArea(response.toString());
            } else { //404 403 402 etc error koder
                // Handle the error response
                System.out.println("Error response code: " + responseCode);
            }

            // Close the connection
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void putRequest(String databasePath) {


        try {
            URL url = new URL(databaseUrl + databasePath);
            //URL url = new URL("https://mobilt-java-22-default-rtdb.europe-west1.firebasedatabase.app/alrikHe.json");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("PUT");

            connection.setDoOutput(true);

            // connection.setRequestProperty("Content-Type", "application/json"); //typen
            connection.setRequestProperty("Content-Type", "application/json"); //typen

            HashMap<String, Object> dataMap = new HashMap<>();

            dataMap.putAll(new HashMap<String, Object>() {
                {
                    put("memo", " köp mjölk!!");
                   // put("age", 33);
                    // put("creditcardNr", "8834 1234 8156 8888");
                   // put("eyeColor", "black");
                   // put("rememberLista", new String[]{"eat lunch", "kom i tid ", "rätta","närvaro check"});
                }
            });


            ArrayList<String> s= new ArrayList<>(){{ addAll(List.of("alrik","Joel","Sam","Linus","Ardi"));}};
/*          dataMap.put("name", "Alrik");
            dataMap.put("age", 31);
            dataMap.put("creditcardNr", "1234 1234 8156 3123");
            dataMap.put("eyeColor", "black");
            dataMap.put("rememberLista", new String[]{"eat lunch", "kom i tid ", "rätta"});*/

            String jsonInputString = new Gson().toJson(dataMap);
            //String jsonInputString = "{\"name\": \"Alrik\"}";

            // Write the data to the output stream
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Get the response code
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK)
                System.out.println("PUT request successful");
            else
                System.out.println("Error response code: " + responseCode);
            // Close the connection
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void patchRequest(String databasePath) {


        try {

            HashMap<String, Object> dataMap = new HashMap<>();

            dataMap.putAll(new HashMap<String, Object>() {
                {
                    put("memo", " köp mjölk!!");
                    // put("age", 33);
                    // put("creditcardNr", "8834 1234 8156 8888");
                    // put("eyeColor", "black");
                    // put("rememberLista", new String[]{"eat lunch", "kom i tid ", "rätta","närvaro check"});
                }
            });
            // kör nysaste protocoll https
            HttpClient httpClient = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_2)
                    .build();
            // json object
            String jsonInputString = new Gson().toJson(dataMap);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(databaseUrl + databasePath))
                    .method("PATCH", HttpRequest.BodyPublishers.ofString(jsonInputString))
                    .header("Content-Type", "application/json")
                    .build();
            HttpResponse response = httpClient.send(request,HttpResponse.BodyHandlers.ofString());
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void postRequest(String databasePath) {


        try {
            URL url = new URL(databaseUrl + databasePath);
            //URL url = new URL("https://mobilt-java-22-default-rtdb.europe-west1.firebasedatabase.app/alrikHe.json");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");

            connection.setDoOutput(true);

            // connection.setRequestProperty("Content-Type", "application/json"); //typen
            connection.setRequestProperty("Content-Type", "application/json"); //typen

            HashMap<String, Object> dataMap = new HashMap<>();

            dataMap.putAll(new HashMap<String, Object>() {
                {
                    put("memo", " köp mjölk!!");
                    // put("age", 33);
                    // put("creditcardNr", "8834 1234 8156 8888");
                    // put("eyeColor", "black");
                    // put("rememberLista", new String[]{"eat lunch", "kom i tid ", "rätta","närvaro check"});
                }
            });


            //ArrayList<String> s= new ArrayList<>(){{ addAll(List.of("alrik","Joel","Sam","Linus","Ardi"));}};
/*          dataMap.put("name", "Alrik");
            dataMap.put("age", 31);
            dataMap.put("creditcardNr", "1234 1234 8156 3123");
            dataMap.put("eyeColor", "black");
            dataMap.put("rememberLista", new String[]{"eat lunch", "kom i tid ", "rätta"});*/

            String jsonInputString = new Gson().toJson(dataMap);
            //String jsonInputString = "{\"name\": \"Alrik\"}";

            // Write the data to the output stream
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Get the response code
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK)
                System.out.println("PUT request successful");
            else
                System.out.println("Error response code: " + responseCode);
            // Close the connection
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void deleteRequests(String databasePath) {
        String databaseUrl = "https://mobilt-java-22-default-rtdb.europe-west1.firebasedatabase.app/";

        try {
            // Create the URL for the HTTP GET request
            URL url = new URL(databaseUrl + databasePath);
            //URL url = new URL("https://mobilt-java-22-default-rtdb.europe-west1.firebasedatabase.app/alrikHe.json");

            // Open a connection to the URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set the request method to GET
            connection.setRequestMethod("DELETE"); //POST , PATCH , DELETE

            // Get the response code t.ex 400, 404, 200 är ok
            int responseCode = connection.getResponseCode();
            //  System.out.println("response code:" +responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) { // ok är bra
                // Read the response from the InputStream
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Handle the response data
                System.out.println("Response from Firebase Realtime Database:");
                System.out.println(response);
            } else { //404 403 402 etc error koder
                // Handle the error response
                System.out.println("Error response code: " + responseCode);
            }

            // Close the connection
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}