package com.example.toma.androidsapt10.API;

import com.example.toma.androidsapt10.vo.AccountRequest;
import com.example.toma.androidsapt10.vo.AccountResponse;
import com.example.toma.androidsapt10.vo.GetTasksRequest;
import com.example.toma.androidsapt10.vo.Page;
import com.example.toma.androidsapt10.vo.TaskRequest;
import com.example.toma.androidsapt10.vo.TaskVO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface APIservice
{
    String MY_URL = "http://192.168.43.75:8080/";

    @POST("users")
    Call<AccountResponse> login(@Body AccountRequest post);

    @POST("tasks")
    Call<Page> getTasks(@Body GetTasksRequest getTasksRequest);

    @PUT("tasks")
    Call<TaskVO> update(@Body TaskRequest taskRequest);

}
