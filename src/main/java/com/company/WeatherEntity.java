package com.company;

import java.util.ArrayList;

public class WeatherEntity {
    public int year;
    public int month;
    public Weekday weekday;
    public int timeEnd;
    public int timeStart;
    public ArrayList<Double> temperatureObservations;
    public ArrayList<Double> sunriseObservations;


    public WeatherEntity(int _year, int _month, Weekday _weekday, int _timeStart, Double temperatureValue){
        year = _year;
        month = _month;
        timeEnd = _timeStart+1;
        timeStart = _timeStart;

        temperatureObservations = new ArrayList<Double>();
        sunriseObservations = new ArrayList<Double>();

        weekday = _weekday;
        setTemperatureObservation(temperatureValue);
    }
    public void setTemperatureObservation(Double observation) {
        this.temperatureObservations.add(observation);
    }
    public void setSunriseObservations(Double observation){
        this.sunriseObservations.add(observation);
    }
    public double getAverageTemperature(){
        double sum=0;
        int cnt=0;
        for (double i : temperatureObservations) {
            if(i!=0.0){
                cnt++;
                sum+=i;
            }
        }
        return sum/cnt;
    }
    public double getTemperatureAccuray(){
        double sum=0;
        double cnt=0;
        double avg=getAverageTemperature();
        for (double i : temperatureObservations) {
            if(i!=0.0) {
                cnt++;
                sum += Math.abs(i - avg);
            }
        }
        return sum/cnt;
    }

    public int getAverageSunhours(){
        double sum=0;
        int cnt=0;
        for (double i : sunriseObservations) {
            if(i!=0.0){
                cnt++;
                sum+=i;
            }
        }
        if(cnt == 0 ||sum == 0 || (sum/cnt)*(100/60)<5) return 0;
        else return 1;
    }
    public double getSunhoursAccuray(){
        double sum=0;
        double cnt=0;
        double avg=getAverageSunhours();
        for (double i : sunriseObservations) {
            if(i!=0.0) {
                cnt++;
                sum += Math.abs(i - avg);
            }
        }
        if(cnt == 0 ||sum == 0) return 0;
        else return (sum/cnt);
    }
}
