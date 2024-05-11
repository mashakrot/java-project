package com.example.deletethisshit;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//CityInfo fragment which presents general information about chosen municipality/city
public class CityInfoFragment extends Fragment {
    private String cityChoice;
    private TextView textViewTitle;
    private TextView textViewDescription;

    public CityInfoFragment (String cityChoice) {
        super();
        this.cityChoice = cityChoice;
    }


    //Retrieves information about the city from external source and presents it for a user
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_information, container, false);

        textViewDescription = (TextView) view.findViewById(R.id.cityInfo);
        textViewTitle = (TextView) view.findViewById(R.id.cityName);

        Context context = getContext();
        WeatherDataRetriever weatherDataRetriever = new WeatherDataRetriever();
        SelfSufficiencyRetriever selfSufficiencyRetriever = new SelfSufficiencyRetriever();
        WorkStatisticsRetriever workStatisticsRetriever = new WorkStatisticsRetriever();

        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(new Runnable() {
                            @Override
                            public void run() {
                                SelfSufficiencyRetriever.getMunicipalityCodesMap();
                                SelfSufficiencyData sufficiencyData = selfSufficiencyRetriever.getSelfSufficiencyData(context, cityChoice);
                                WeatherData weatherData = weatherDataRetriever.getData(cityChoice);
                                WorkStatisticsRetriever.getMunicipalityCodesMap();
                                WorkStatistics workStatistics = workStatisticsRetriever.getWorkStatisticsData(context, cityChoice);

                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        String weatherDataAsString = weatherData.getName() + "\n" +
                                                "Weather now: " + weatherData.getMain() + "(" + weatherData.getDescription() + ")\n" +
                                                "Temperature: " + convertTemperature(weatherData.getTemperature()) + " °C" + "\n" +
                                                "Wind speed: " + weatherData.getWindSpeed() + " m/s" + "\n";
                                        textViewTitle.setText(weatherDataAsString);

                                        int f = sufficiencyData.getHeadersCount();
                                        int d = workStatistics.getHeadersCount();
                                        String dataString2 = "";
                                        dataString2 = "Year" + "\t" + "\t";
                                        for (int a = 0; a < f; a++) {
                                            dataString2 = dataString2 + sufficiencyData.getHeaders(a) + "\t";
                                        }
                                        for (int b = 0; b < d; b++) {
                                            dataString2 = dataString2 + workStatistics.getHeaders(b) + "\t";
                                        }
                                        dataString2 = dataString2 + "\n";

                                        int w = workStatistics.getYearsCount();
                                        int h = sufficiencyData.getYearsCount();
                                        for (int q = 0; q < h; q++) {
                                            dataString2 = dataString2 + sufficiencyData.getYear(q) + "\t" + "\t"+ "\t";
                                            for (int c = 0; c < f; c++) {
                                                dataString2 = dataString2 + sufficiencyData.getItem(q, c) + "\t" + "\t"+ "\t";
                                                for (int e = 0; e < d; e++) {
                                                    dataString2 = dataString2 + workStatistics.getItem(q, e) + "\t" + "\t"+ "\t"    ;
                                                }
                                            }
                                            dataString2 = dataString2 + "\n";
                                        }

                                        textViewDescription.setText(dataString2);
                                    }
                                });                            }
                        }
        );

        return view;
    }


    private String convertTemperature (String temperature) {
        double numTemp = Double.parseDouble(temperature);
        double numResult = numTemp - 273.15;

        String result = Double.toString(Math.round(numResult));
        return result;
    }
}


