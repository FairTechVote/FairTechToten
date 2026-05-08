package com.example.fairtechtoten.data.remote;

import com.example.fairtechtoten.data.remote.dto.LoginRequestDto;
import com.example.fairtechtoten.data.remote.dto.LoginResponseDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("api/v1/auth/coordinator")
    Call<LoginResponseDto> login(@Body LoginRequestDto loginRequestDto);

}
