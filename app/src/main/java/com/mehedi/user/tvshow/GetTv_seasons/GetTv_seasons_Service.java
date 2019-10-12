package com.mehedi.user.tvshow.GetTv_seasons;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by User on 9/10/2018.
 */

public interface GetTv_seasons_Service {
    @GET("")
    Call<GetTv_seasonsResponse> getTv_service(@Url String endUrl);
}
