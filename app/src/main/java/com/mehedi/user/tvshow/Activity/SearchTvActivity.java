package com.mehedi.user.tvshow.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.mehedi.user.tvshow.Database.PojoClass;
import com.mehedi.user.tvshow.Database.SearchTvResultDataBaseSourch;
import com.mehedi.user.tvshow.R;
import com.mehedi.user.tvshow.SearchTv.RecyclerSetOnItemClickListener;
import com.mehedi.user.tvshow.SearchTv.SearchResponse;
import com.mehedi.user.tvshow.SearchTv.SearchTvAdapter;
import com.mehedi.user.tvshow.SearchTv.SearchTvService;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchTvActivity extends AppCompatActivity {
    private static final  String BASEURL = "https://api.themoviedb.org/3/search/";
    private SearchTvService service;
    private String endurl;
    private RecyclerView ShowTvList;
    private List<SearchResponse> searchResponses ;
    private SearchTvAdapter adapter;
    public SearchResponse searchreRespo;
    private String SearchTvName;
    private String  q = "The big bang theory";
    String encodeedString;
    private  int TvNumber;
    public ExpandableTextView expTv1;
    private ExpandableTextView Expandimage;
    private  Boolean CheckValue = false;
    private SearchTvResultDataBaseSourch searchTvResultDataBaseSourch;
    private TextView notFountTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_tv);


        initialView();

        Toolbar toolbar = findViewById(R.id.toolbar_show);
        setSupportActionBar(toolbar);

        if (getIntent().getExtras() != null) {

            Bundle p = getIntent().getExtras();
            SearchTvName = p.getString("data");
        }
        if(SearchTvName.length() != 0) {

            try {
                encodeedString = URLEncoder.encode(SearchTvName, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        // Toast.makeText(this, ""+SearchTvName, Toast.LENGTH_SHORT).show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(SearchTvService.class);


  //****************************** Recycler view Singel OnItemTouchListener ***********************************************

        ShowTvList.addOnItemTouchListener(new RecyclerSetOnItemClickListener(this, new RecyclerSetOnItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                TvNumber = searchreRespo.getResults().get((position)).getId();

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(SearchTvActivity.this, ShowTvSearchDetails.class);
                        ///Bundle bundle= new Bundle();
                        // bundle.putInt("mes",TvNumber);
                        // Toast.makeText(SearchTvActivity.this, "Cool => " + TvNumber, Toast.LENGTH_SHORT).show();
                        intent.putExtra("me", TvNumber);
                        startActivity(intent);
                    }
                });


            }
        }));





/////////////////////////////***************** bar back button ***********************///////////////////////////////////////
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initialView() {
        ShowTvList = findViewById(R.id.recycler_view);
        notFountTextView = findViewById(R.id.notfound);

    }
    //****************************** Search Response  ***********************************************
    @Override
    protected void onStart() {

        super.onStart();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading data......");
        progressDialog.show();
        endurl = String.format("tv?api_key=%s&language=en-US&query=%s&page=1",getString(R.string.Srearch_api_key),encodeedString);
        Call<SearchResponse> call = service.getSearchResponse(endurl);
        call.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {

                if (response.code()==200) {

                    progressDialog.dismiss();
                    searchreRespo =  response.body();
                    adapter = new SearchTvAdapter(SearchTvActivity.this,searchreRespo.getResults());
                    LinearLayoutManager llm = new LinearLayoutManager(SearchTvActivity.this);
                    ShowTvList.setLayoutManager(llm);
                    ShowTvList.setAdapter(adapter);
                    //  Toast.makeText(SearchTvActivity.this, "Somthing happend \n" , Toast.LENGTH_SHORT).show();

                }else{
                    notFountTextView.setText("No Result Found");
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                //Toast.makeText(SearchTvActivity.this, "On Failure", Toast.LENGTH_SHORT).show();
            }
        });
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
