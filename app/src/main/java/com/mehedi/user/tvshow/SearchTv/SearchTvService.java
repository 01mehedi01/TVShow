package com.mehedi.user.tvshow.SearchTv;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by User on 5/25/2018.
 */

public interface SearchTvService {
    @GET("")
    Call<SearchResponse> getSearchResponse(@Url String endurll);
}
