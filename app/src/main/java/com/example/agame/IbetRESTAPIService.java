package com.example.agame;
import com.example.agame.models.Event;
import com.example.agame.models.Sport;

import java.util.List;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
public interface IbetRESTAPIService {
    @GET("/v4/sports/?apiKey=7e01040b97e454831185d8a4559a57c5")
    Call<List<Sport>>getSports();
    @GET("/v4/sports/upcoming/odds/?regions=eu&markets=h2h&apiKey=7e01040b97e454831185d8a4559a57c5")
    Call<List<Event>>getUpcomingEvents();
    @GET("odds/?regions=eu&markets=h2h&apiKey=7e01040b97e454831185d8a4559a57c5")
    Call<List<Event>>getSportByEvents();
}


