package com.mehedi.user.tvshow;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.mehedi.user.tvshow.Activity.SearchTvActivity;
import com.mehedi.user.tvshow.Database.PojoClass;
import com.mehedi.user.tvshow.Database.SearchTvResultDataBaseSourch;
import com.mehedi.user.tvshow.Database.TvNameAdaptar;
import com.mehedi.user.tvshow.SearceView.CustomAutoCompleteTextChangedListener;
import com.mehedi.user.tvshow.SearceView.CustomAutoCompleteView;
import com.mehedi.user.tvshow.SearchTv.RecyclerSetOnItemClickListener;
import com.mehedi.user.tvshow.SearchTv.SearchResponse;
import com.mehedi.user.tvshow.SearchTv.SearchTvAdapter;
import com.mehedi.user.tvshow.SearchTv.SearchTvService;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private EditText SearchTv;
    private RecyclerView TvRecyclerView;
    private PojoClass pojoClass;
    private ArrayList<PojoClass> pojoClasses;
    private TvNameAdaptar tvNameAdaptar;
    public SearchTvResultDataBaseSourch searchTvResultDataBaseSourch;
    private String  Searching_Tv_Name;

    public AutoCompleteTextView myAutoComplete;
    public ArrayAdapter<String> myAdapter;

    private static final  String BASEURL = "https://api.themoviedb.org/3/search/";
    private SearchTvService service;
    private String endurl;
    private RecyclerView ShowTvList;
    private List<SearchResponse> searchResponses ;
    private SearchTvAdapter adapter;
    public SearchResponse searchreRespo;
    private String  q = "The big bang theory";
    String encodeedString;
    private  int TvNumber;
    public ExpandableTextView expTv1;
    private ExpandableTextView Expandimage;
    private  Boolean CheckValue = false;
    private TextView notFountTextView;
    private PojoClass[] ObjectItemData;
    private String[] Data ;
    private TextView text ;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initolize();
        searchTvResultDataBaseSourch =new SearchTvResultDataBaseSourch(this);
        pojoClass = new PojoClass();
        searchreRespo = new SearchResponse();



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(SearchTvService.class);


        //****************************** Get Data from database for showing Search Suggestion ***********************************************
        if(searchTvResultDataBaseSourch.getAll() != null){
            Scanner s1 = new Scanner(System.in);
            pojoClasses = searchTvResultDataBaseSourch.getAll();
            Data = new String[pojoClasses.size()];

            for(int i=0;i<pojoClasses.size();i++){
                Data[i] = pojoClasses.get(i).getName();

            }
        }

          myAutoComplete =  findViewById(R.id.myautocomplete);
          myAdapter = new ArrayAdapter<String>(this, R.layout.list_view_row,R.id.text_view_list_item,Data);
          myAutoComplete.setAdapter(myAdapter);

    }



    //****************************** initilize Views ***********************************************
    private void initolize() {


       // text = findViewById(R.id.list_id_show);
        myAutoComplete =  findViewById(R.id.myautocomplete);
        ObjectItemData = new PojoClass[0];
        pojoClasses = new ArrayList<>();
    }



    public void SrearchTv(View view) {

        JsonResponse();


    }
    //********** check Response successfull or not for save searce Tv Name in database for search suggetion *****************
    public void JsonResponse() {

        Searching_Tv_Name = myAutoComplete.getText().toString();

        if(Searching_Tv_Name.length() != 0) {

            try {
                encodeedString = URLEncoder.encode(Searching_Tv_Name, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
           // Toast.makeText(this, "Cool =>  "+encodeedString, Toast.LENGTH_SHORT).show();
        }


        final ProgressDialog progressDialog = new ProgressDialog(this);

        endurl = String.format("tv?api_key=%s&language=en-US&query=%s&page=1",getString(R.string.Srearch_api_key),encodeedString);
        Call<SearchResponse> call = service.getSearchResponse(endurl);
        call.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {

                if (response.code()==200) {
                    searchreRespo =  response.body();

                    if(searchreRespo.getTotalResults() > 0  && searchreRespo.getTotalPages() > 0  && encodeedString!= null){
                        //Toast.makeText(MainActivity.this, " success cool "+searchreRespo.getTotalPages(), Toast.LENGTH_SHORT).show();

                        searchTvResultDataBaseSourch = new SearchTvResultDataBaseSourch(MainActivity.this);



      //************************************ check if the tv name is save or not ***********************************
                        boolean statuscheck = searchTvResultDataBaseSourch.MatchTvName(encodeedString);

      //************************************  if  not  exist the tv name in the Database then save ***********************************
                        if(!statuscheck){
                            boolean status = searchTvResultDataBaseSourch.InserteValues(new PojoClass(Searching_Tv_Name));
                            if(status){
                                Intent intent = new Intent(MainActivity.this, SearchTvActivity.class);
                                intent.putExtra("data",Searching_Tv_Name);
                                startActivity(intent);
                            }else{
                                Toast.makeText(MainActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                            }

                        }
                        else{

                            Intent intent = new Intent(MainActivity.this, SearchTvActivity.class);
                            intent.putExtra("data",Searching_Tv_Name);
                            startActivity(intent);
                        }

                    }else{

                        Toast.makeText(MainActivity.this, "type a Valid Tv Name", Toast.LENGTH_SHORT).show();
                    }



                }else{
                    //notFountTextView.setText("No Result Found");
                    Toast.makeText(MainActivity.this, "type a Valid Tv Name", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Please a Valid Tv Name", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
