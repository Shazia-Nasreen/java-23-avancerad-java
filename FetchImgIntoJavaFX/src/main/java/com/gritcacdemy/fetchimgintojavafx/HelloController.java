package com.gritcacdemy.fetchimgintojavafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HelloController {

    @FXML
    private ImageView imageView;


    @FXML
    private Label welcomeText;

    @FXML
    void onClickRequest(ActionEvent event) {

    }
    public static void getRequests(String databasePath) {
        String databaseUrl = "https://mobilt-java-22-default-rtdb.europe-west1.firebasedatabase.app/";

        try {

            // Create the URL for the HTTP GET request
            URL url = new URL(databaseUrl + databasePath);
            //URL url = new URL("https://mobilt-java-22-default-rtdb.europe-west1.firebasedatabase.app/.json");
            //URL url = new URL("https://www.cheapshark.com/api/1.0/deals?storeID=1&upperPrice=0.5");  // Cheapshark
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
                //ta = new TextArea(response.toString());
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
