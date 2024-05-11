package com.example.deletethisshit;

import java.util.ArrayList;

//This class contains lists with data retrieved from MunicipalityDataRetriever
public class MunicipalityData {

    private ArrayList years;
    private ArrayList headers;
    private int[][] stastisticsArrays;


    public MunicipalityData(ArrayList years, ArrayList headers, int[][] stastisticsArrays) {
        this.years = years;
        this.headers = headers;
        this.stastisticsArrays = stastisticsArrays;
    }

    public String getYear(int i) {
        String a = years.get(i).toString();
        return a;
    }
    public String getHeaders(int i) {
        String b = headers.get(i).toString();
        return b;
    }
    public int getYearsCount() {
        int b = years.size();
        return b;
    }

    public int getHeadersCount() {
        int b = headers.size();
        return b;
    }

    public String getItem(int i, int j) {
        Integer x = stastisticsArrays[i][j];
        String y = x.toString();
        return y;
    }
}
