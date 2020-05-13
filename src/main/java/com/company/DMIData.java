package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DMIData {

    public DMIData() {
        for (int i = 1; i <= 1; i++) {
            start(i);
        }
    }

    private void start(int index) {
        try {
            System.out.println(index);
            URL urlForGetRequest = new URL("https://www.dmi.dk/dmidk_obsWS/rest/archive/hourly/danmark/temperature/Hele%20landet/2019/April/"+index);
            String readLine = null;
            HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
            conection.setRequestMethod("GET");
            conection.setRequestProperty("userId", "a1bcdef"); // set userId its a sample here
            int responseCode = conection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(conection.getInputStream()));
                StringBuffer response = new StringBuffer();
                while ((readLine = in .readLine()) != null) {
                    response.append(readLine);
                } in .close();
                // print result
                System.out.println(response.toString());
                //GetAndPost.POSTRequest(response.toString());
            } else {
                System.out.println("GET NOT WORKED");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
