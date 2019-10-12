package com.mehedi.user.tvshow.Activity;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.mehedi.user.tvshow.GetTv_seasons.GetTv_seasonsResponse;
import com.mehedi.user.tvshow.GetTv_seasons.GetTv_seasons_Service;
import com.mehedi.user.tvshow.GetTv_seasons.SeasonAdeptar;
import com.mehedi.user.tvshow.R;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShowSeasonsActivity extends AppCompatActivity {
    private GetTv_seasons_Service getSeasonService;
    private GetTv_seasonsResponse getSeasonResponse;
    private int SeasonNumber;
    private int TvID;
    private ImageView PostarImage;
    private RecyclerView SeasonsList;
    private SeasonAdeptar seasonAdeptar;
    private LinearLayout Visibility;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_seasons);
        android.support.v7.widget.Toolbar toolba = findViewById(R.id.toolbar);
        setSupportActionBar(toolba);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if (getIntent().getExtras() != null) {

            Bundle p = getIntent().getExtras();
            SeasonNumber = p.getInt("se");
            TvID = p.getInt("tv");
        }

 //****************************** initiliza view  ***********************************************
        initilizeView();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading data......");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ShowTvSearchDetails.BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

//****************************** Response  ***********************************************
        getSeasonService = retrofit.create(GetTv_seasons_Service.class);
        String endurl = String.format("%d/season/%d?api_key=%s&language=en-US", TvID, SeasonNumber, getString(R.string.Srearch_api_key));
        Call<GetTv_seasonsResponse> call = getSeasonService.getTv_service(endurl);
        call.enqueue(new Callback<GetTv_seasonsResponse>() {
            @Override
            public void onResponse(Call<GetTv_seasonsResponse> call, Response<GetTv_seasonsResponse> response) {
                if(response.code()==200){

                    progressDialog.dismiss();
                    Visibility.setVisibility(View.VISIBLE);

                    getSeasonResponse = response.body();
                    seasonAdeptar = new SeasonAdeptar(ShowSeasonsActivity.this,getSeasonResponse.getEpisodes());
                    LinearLayoutManager llm = new LinearLayoutManager(ShowSeasonsActivity.this);
                    SeasonsList.setLayoutManager(llm);
                    SeasonsList.setAdapter(seasonAdeptar);
                   // Toast.makeText(ShowSeasonsActivity.this, "Success=> "+response.body(), Toast.LENGTH_SHORT).show();



                    //******************************************** Postar picture ****************************************
                    try{
                        String postarpath = getSeasonResponse.getPosterPath();

                        Dialog builder = new Dialog(ShowSeasonsActivity.this);
                        builder.  requestWindowFeature(Window.FEATURE_NO_TITLE);
                        builder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialogInterface) {
                                //nothing;
                            }
                        });
                        final ProgressDialog progressDialog = new ProgressDialog(ShowSeasonsActivity.this);
                        progressDialog.show();
                        progressDialog.setCancelable(false);
                        Picasso.get()
                                .load(Uri.parse("http://image.tmdb.org/t/p/w185/"+postarpath))
                                .into(PostarImage,new com.squareup.picasso.Callback() {
                                    @Override
                                    public void onSuccess() {

                                        // The image has loaded you can make the progress bar invisible
                                        if (progressDialog.isShowing())
                                            progressDialog.dismiss();
                                    }
                                    @Override
                                    public void onError(Exception e) {
                                        if (progressDialog.isShowing())
                                            progressDialog.dismiss();
                                    }

                                });

                        builder.addContentView(PostarImage, new RelativeLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT));
                        builder.show();
                        progressDialog.setCancelable(false);
                        progressDialog.show();

                    }catch (Exception e){
                        PostarImage.setImageResource(R.drawable.noimage);
                    }
                }
            }

            @Override
            public void onFailure(Call<GetTv_seasonsResponse> call, Throwable t) {
                //Toast.makeText(ShowSeasonsActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
        /////////////////////////////***************** bar back button ***********************///////////////////////////////////////
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initilizeView() {
        Visibility = findViewById(R.id.showseasonvisible);
        PostarImage = findViewById(R.id.showSeasonsimageId);
        SeasonsList = findViewById(R.id.showSeasonsRecyclerviewid);
    }

    /////////////////////////////***************** bar back button ***********************///////////////////////////////////////
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
