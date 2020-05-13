package com.company;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class LoadAccidentsWithDate {
    
    public LoadAccidentsWithDate(Weather weather){
        //StartLoadAccidentsWithDate(weather);
        StartLoadWeather(weather);
    }

    public void StartLoadAccidentsWithDate(Weather weather){
        String path = "resources/data.csv";

        String accidents[] = new DataStrings().accidents;

        int rows=0;
        for (String total: accidents) {
            String list[];
            list  = total.split("ÅÅÅ");
            for (String single:list) {
                try {
                    String element[] = single.split(",");

                    int time = Integer.parseInt(element[0]);
                    Weekday weekday = numberToWeekday(Integer.parseInt(element[1]));
                    int month = Integer.parseInt(element[2]);
                    int year = Integer.parseInt(element[3]);
                    int accidentAcc = Integer.parseInt(element[4]);
                    rows+=accidentAcc;
                    if(accidentAcc>0){
                        System.out.println("Time:            | " + time);
                        System.out.println("Weekday:         | " + weekday);
                        System.out.println("Month:           | " + month);
                        System.out.println("Year:            | " + year);
                        System.out.println("No of accidents: | " + accidentAcc);

                        WeatherEntity weatherEntity = weather.findEntity(year,month, weekday, time);
                        System.out.println("Avg temp:        | " + weatherEntity.getAverageTemperature());
                        System.out.println("Temp accuracy:   | " + weatherEntity.getTemperatureAccuray());
                        System.out.println("Avg sunh:        | " + weatherEntity.getAverageSunhours());
                        System.out.println("Sunh accuracy:   | " + weatherEntity.getSunhoursAccuray());

                        System.out.println('\n');
                        writeDataMany(accidentAcc,weatherEntity.getAverageTemperature()+","+weatherEntity.getAverageSunhours()+","+year,"resources/Weather.csv");
                    }
                }catch (NumberFormatException e){}
            }
        }
        System.out.println(rows);
    }

    public void StartLoadWeather(Weather weather){

        for (WeatherEntity e:weather.getWeatherData()) {
            if(e.getAverageSunhours()==0){
                int i = (int) Math.round(e.getAverageTemperature());
                writeDataMany(1,i+"","resources/sunUpWeather.csv");
            }
            else{
                int i = (int) Math.round(e.getAverageTemperature());
                writeDataMany(1,i+"","resources/sunDownWeather.csv");

            }
        }
    }
    
    private void writeDataMany(int times, String data, String strFilePath) {
        PrintWriter csvWriter;
        try {
            File file = new File(strFilePath);
            if (!file.exists()) {
                file = new File(strFilePath);
            }
            csvWriter = new PrintWriter(new FileWriter(file, true));
            for (int i = 0; i < times; i++) {
                csvWriter.println(data);
            }
            csvWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Weekday numberToWeekday(int i){
        switch (i){
            case 2: return Weekday.mandag;
            case 3: return Weekday.tirsdag;
            case 4: return Weekday.onsdag;
            case 5: return Weekday.torsdag;
            case 6: return Weekday.fredag;
            case 7: return Weekday.lørdag;
            default: return Weekday.søndag;
        }

    }
}

