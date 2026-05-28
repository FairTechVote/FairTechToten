package com.example.fairtechtoten.data.remote;

import com.example.fairtechtoten.data.remote.dto.CoordinatorDto;
import com.example.fairtechtoten.data.remote.dto.CoordinatorInstituteLisResponseDto;
import com.example.fairtechtoten.data.remote.dto.CoordinatorUpdateRequestDto;
import com.example.fairtechtoten.data.remote.dto.LoginRequestDto;
import com.example.fairtechtoten.data.remote.dto.LoginResponseDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    @POST("api/v1/auth/coordinator")
    Call<LoginResponseDto> login(@Body LoginRequestDto loginRequestDto);

    @GET("api/v1/institutes/coordinator/{coordinatorId}/institute")
    Call<CoordinatorInstituteLisResponseDto> getCoordinatorInstitutes(
            @Path("coordinatorId") Long coordinatorId
    );

    @GET("api/v1/coordinators/{id}")
    Call<CoordinatorDto> getCoordinator(@Path("id") long id);

    @PUT("api/v1/coordinators/{id}")
    Call<CoordinatorDto> updateCoordinator(
            @Path("id") long id,
            @Body CoordinatorUpdateRequestDto body
    );

}
