package com.company;



import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Weather {

    private Set<WeatherEntity> weatherData;

    public Weather(){
        weatherData = new HashSet<WeatherEntity>();
        initTemperatureObservations("resources/weather.csv", true);
        initTemperatureObservations("resources/weatherSun.csv", false);
    }

    private void initTemperatureObservations(String csvFile, Boolean temperature){
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                // value,hour,date,month,year
                String[] row = line.split(cvsSplitBy);

                int year;
                int month;
                int date;
                int time;
                double value;

                try {
                    year = Integer.parseInt(row[4]);
                    month = calculateMonth(row[3]);
                    date = Integer.parseInt(row[2]);
                    time = Integer.parseInt(row[1].split(":")[0]);
                    value = Double.parseDouble(row[0]);
                    Weekday weekday = calculateWeekday(year, month, date);
                    if(year<2019) addEntity(year,month,weekday, time,value, temperature);
                } catch (NumberFormatException e){
                    //e.printStackTrace();
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        printData(temperature);
    }

    public Set<WeatherEntity> getWeatherData() {
        return weatherData;
    }

    public WeatherEntity findEntity(int _year, int _month, Weekday _weekday, int _timeStart){
        WeatherEntity found = null;
        for (WeatherEntity f : weatherData) {
            if (f.year==_year && f.weekday==_weekday && f.month==_month && f.timeStart==_timeStart) {

                    found = f;
                break;
            }
        }
        return found;
    }
    public boolean addEntity(int _year, int _month, Weekday date, int _timeStart, Double value, Boolean temperature){
        WeatherEntity existing = findEntity(_year, _month, date, _timeStart);
        if(existing!=null){
            if(temperature){
                existing.setTemperatureObservation(value);
            }else{
                existing.setSunriseObservations(value);
            }
            return true;
        }else return weatherData.add(new WeatherEntity(_year, _month, date, _timeStart, value));
    }
    private Weekday calculateWeekday(int _year, int _month, int _date){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, _year);
        calendar.set(Calendar.MONTH, _month);
        calendar.set(Calendar.DATE, _date);
        switch (calendar.get(Calendar.DAY_OF_WEEK)){
            case 2: return Weekday.mandag;
            case 3: return Weekday.tirsdag;
            case 4: return Weekday.onsdag;
            case 5: return Weekday.torsdag;
            case 6: return Weekday.fredag;
            case 7: return Weekday.lørdag;
            default: return Weekday.søndag;
        }
    }
    private int calculateMonth(String s){
        if(s.equals("Jan")) return 0;
        if(s.equals("Feb")) return 1;
        if(s.equals("Mar")) return 2;
        if(s.equals("Apr")) return 3;
        if(s.equals("Maj")) return 4;
        if(s.equals("Jun")) return 5;
        if(s.equals("Jul")) return 6;
        if(s.equals("Aug")) return 7;
        if(s.equals("Sep")) return 8;
        if(s.equals("Okt")) return 9;
        if(s.equals("Nov")) return 10;
        if(s.equals("Dec")) return 11;
        return 0;
    }

    private void printData(Boolean temperature){
        int acc = 0;
        for(WeatherEntity w : weatherData){
            if(temperature)acc+=w.temperatureObservations.size();
            else acc+=w.sunriseObservations.size();
        }
        if (temperature) System.out.println("Temperature size "+acc);
        else System.out.println("Sun data size "+acc);
    }
}
