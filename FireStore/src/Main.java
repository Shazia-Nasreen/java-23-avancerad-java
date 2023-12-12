
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;



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
        //firebaseRequests("name.json");
        putRequest("alrik.json");
    }






    public static void firebaseRequests(String databasePath){
        String databaseUrl = "https://mobilt-java-22-default-rtdb.europe-west1.firebasedatabase.app/";

        try {
            // Create the URL for the HTTP GET request
            //URL url = new URL(databaseUrl + databasePath);
            URL url = new URL("https://mobilt-java-22-default-rtdb.europe-west1.firebasedatabase.app/alrik.json");

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
            //URL url = new URL(databaseUrl + databasePath);
            URL url = new URL("https://mobilt-java-22-default-rtdb.europe-west1.firebasedatabase.app/alrikHe.json");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("PUT");

            connection.setDoOutput(true);

           // connection.setRequestProperty("Content-Type", "application/json"); //typen
            connection.setRequestProperty("Content-Type", "application/json"); //typen

            HashMap<String, Object> dataMap = new HashMap<>();
            dataMap.put("name", "Alrik");
            dataMap.put("age", 31);
            dataMap.put("creditcardNr", "1234 1234 8156 3123");
            dataMap.put("eyeColor", "black");
            dataMap.put("rememberLista", new String[]{"eat lunch","kom i tid ","rätta"});

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
}