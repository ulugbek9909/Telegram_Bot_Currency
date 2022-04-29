package com.company;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Currency {
    public static double getCurrency(String cry) {
        LocalDate localDate = LocalDate.now();
        localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        try {
            URL url = new URL("https://cbu.uz/oz/arkhiv-kursov-valyut/json/" + cry + "/" + localDate + "/");
            URLConnection connection = url.openConnection();

            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder builder = new StringBuilder();
            String line = bufferedReader.readLine();
            while (line != null) {
                builder.append(line);
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
            Gson gson = new Gson();


            RateDLR[] usd = gson.fromJson(builder.toString(), RateDLR[].class);
            return usd[0].getRate();


        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
