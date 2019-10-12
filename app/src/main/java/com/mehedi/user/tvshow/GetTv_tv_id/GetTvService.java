package com.mehedi.user.tvshow.GetTv_tv_id;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by User on 5/27/2018.
 */

public interface GetTvService {
    @GET("")
    Call<GetTvIdResponse> getTvResponse(@Url String endurl);
}
