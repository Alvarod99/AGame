package com.example.agame;
import com.example.agame.models.Sport;

import java.util.List;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
public interface IbetRESTAPIService {
    @GET("/v4/sports/?apiKey=4ddd3c70740b3bbb6f6ad30cab32d2dc")
    Call<List<Sport>>getSports();
}


