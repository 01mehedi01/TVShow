package com.mehedi.user.tvshow.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mehedi.user.tvshow.GetTv_tv_id.GetTvIdResponse;
import com.mehedi.user.tvshow.GetTv_tv_id.GetTvService;
import com.mehedi.user.tvshow.R;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShowTvSearchDetails extends AppCompatActivity {
    private int TvId ;
    private GetTvService getTvService;
    private GetTvIdResponse getTvIdResponse;
    public static  final String BaseUrl = "https://api.themoviedb.org/3/tv/";
    private TextView ShowName;
    private TextView FirstAirDate;
    private TextView LastAirDate;
    private TextView EpisodRunTime;
    private TextView NumberOfepisodes;
    private Button NumberOfseasons;
    private TextView CreatedBy;
    private TextView Network;
    private TextView OriginalCountry;
    private ImageView CreaterImageView;
    private ImageView NetWorkImageView;
    private int SeasonNumber;
    private LinearLayout ShowWebView;



    private ConstraintSet layout1,loyout2;
    private ConstraintLayout constraintLayout;
    private boolean IsOpen = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_tv_search_details);
        initilizeView();
        Toolbar toolbar = findViewById(R.id.toolbar_search_details);
        setSupportActionBar(toolbar);
        if (getIntent().getExtras() != null) {

            Bundle p = getIntent().getExtras();
            TvId = p.getInt("me");
        }
      //  ShowWebView.setVisibility(View.GONE);



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //////////////////////////////// progressDaialog//////////////////////////////////////////
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading data......");
        progressDialog.show();
        /////////////////////////////////////////////////////////////////////


        //****************************** response ***********************************************

        getTvService = retrofit.create(GetTvService.class);
        String endurl = String.format("%d?api_key=%s&language=en-US",TvId,getString(R.string.Srearch_api_key));
        Call<GetTvIdResponse> call = getTvService.getTvResponse(endurl);

        call.enqueue(new Callback<GetTvIdResponse>() {
            @Override
            public void onResponse(Call<GetTvIdResponse> call, Response<GetTvIdResponse> response) {
                if (response.code()==200){
                    ShowWebView.setVisibility(View.VISIBLE);

                    progressDialog.dismiss();


                    getTvIdResponse = response.body();
                    initilizeInformationIntoActivity();

//                    try{
//                      String imageString = getTvIdResponse.getCreatedBy().get(0).getProfilePath();
//                      Picasso.with(ShowTvSearchDetails.this)
//                              .load(Uri.parse("http://image.tmdb.org/t/p/w185/"+imageString))
//                              .into(CreaterImageView);
//                  }catch (Exception e){
//                      CreaterImageView.setImageResource(R.drawable.noimage);
//                  }

                    try{
                        String networkImage = getTvIdResponse.getNetworks().get(0).getLogoPath();
                        Picasso.get()
                                .load(Uri.parse("http://image.tmdb.org/t/p/w185/"+networkImage))
                                .into(NetWorkImageView);
                    }catch (Exception e){
                        NetWorkImageView.setImageResource(R.drawable.noimage);
                    }



                }
            }

            @Override
            public void onFailure(Call<GetTvIdResponse> call, Throwable t) {

                Toast.makeText(ShowTvSearchDetails.this, "Nothing", Toast.LENGTH_SHORT).show();
            }
        });

/////////////////////////////***************** bar back button ***********************///////////////////////////////////////
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


//****************************** initiliza view  ***********************************************

    private void initilizeView() {
        ShowWebView = findViewById(R.id.details_webview);
        ShowName = findViewById(R.id.search_showName);
        FirstAirDate = findViewById(R.id.first_air_date);
        LastAirDate = findViewById(R.id.last_air_date);
        EpisodRunTime = findViewById(R.id.episode_run_time);
        NumberOfepisodes = findViewById(R.id.number_of_episode);
        NumberOfseasons = findViewById(R.id.number_of_season);
        // CreatedBy = findViewById(R.id.search_tv_creater_name);
        Network = findViewById(R.id.textView);
        OriginalCountry = findViewById(R.id.original_Country);
        // CreaterImageView = findViewById(R.id.Serch_tv_creater_image);
        NetWorkImageView = findViewById(R.id.networkImage);



    }



    //****************************** Out put set in the view  ***********************************************

    private void initilizeInformationIntoActivity() {
        try{
            ShowName.setText(getTvIdResponse.getName());
        }catch (Exception e){
            ShowName.setText(null);
        }
        try{
            FirstAirDate.setText(getTvIdResponse.getFirstAirDate());
        }catch(Exception e){
            FirstAirDate.setText("0000-00-00");
        }
        try{
            LastAirDate.setText(getTvIdResponse.getLastAirDate());
        }catch(Exception e){
            LastAirDate.setText("0000-00-00");
        }
        try{
            EpisodRunTime.setText(String.valueOf(getTvIdResponse.getEpisodeRunTime().get(0).intValue()));
        }catch(Exception e){
            EpisodRunTime.setText("0");
        }
        try{
            NumberOfepisodes.setText(String.valueOf(getTvIdResponse.getSeasons().get(0).getEpisodeCount().intValue()));
        }catch(Exception e){
            NumberOfepisodes.setText("0");
        }
        try{
            SeasonNumber = getTvIdResponse.getSeasons().get(0).getSeasonNumber().intValue();
            NumberOfseasons.setText("Season Number : "+String.valueOf(getTvIdResponse.getSeasons().get(0).getSeasonNumber().intValue()));
        }catch(Exception e){
            NumberOfseasons.setText("0");
        }
//        try{
//            CreatedBy.setText(getTvIdResponse.getCreatedBy().get(0).getName());
//        }catch(Exception e){
//            CreatedBy.setText(null);
//        }
        try{
            OriginalCountry.setText("Country : "+getTvIdResponse.getOriginCountry().get(0));
        }catch(Exception e){
            OriginalCountry.setText(null);
        }
        try{
            Network.setText(getTvIdResponse.getNetworks().get(0).getName());
        }catch(Exception e){
            Network.setText("null");
        }


    }

    public void SeasonsDetails(View view) {

        if(SeasonNumber !=0) {
            Intent intent = new Intent(ShowTvSearchDetails.this, ShowSeasonsActivity.class);
            intent.putExtra("tv", TvId);
            intent.putExtra("se", SeasonNumber);
           // Toast.makeText(this, "Season & TV " + TvId + " &  " + SeasonNumber, Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }else{
            Toast.makeText(this, "No Season Available", Toast.LENGTH_SHORT).show();
        }
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
