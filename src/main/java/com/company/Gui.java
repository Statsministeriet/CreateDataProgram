package com.company;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Gui {

    public Gui(){
        String path = "resources/data.csv";
        String[] list ;
        String total = ""; //todo: insert the data as a string with new lines as ÅÅÅ
        list  = total.split("ÅÅÅ");


        for (int i = 0; i-1 < list.length; i=i+2) {
            int tmp = Integer.parseInt(list[i+1]);
            writeDataMany(tmp,list[i], path);
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
}

