package com.mehedi.user.tvshow.SearceView;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.mehedi.user.tvshow.Database.PojoClass;
import com.mehedi.user.tvshow.Database.TvNameAdaptar;
import com.mehedi.user.tvshow.MainActivity;
import com.mehedi.user.tvshow.R;

public class CustomAutoCompleteTextChangedListener implements TextWatcher {

    public static final String TAG = "CustomAutoCompleteTextChangedListener.java";
    Context context;

    public CustomAutoCompleteTextChangedListener(Context context){
        this.context = context;
    }

    @Override
    public void afterTextChanged(Editable s) {
        // TODO Auto-generated method stub

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTextChanged(CharSequence userInput, int start, int before, int count) {

        try{

            // if you want to see in the logcat what the user types
            Log.e(TAG, "User input: " + userInput);

            MainActivity mainActivity = ((MainActivity) context);

            // update the adapater
            mainActivity.myAdapter.notifyDataSetChanged();

            // get suggestions from the database
            PojoClass[] myObjs = mainActivity.searchTvResultDataBaseSourch.read(userInput.toString());

            // update the adapter
            //mainActivity.myAdapter = new TvNameAdaptar(context, R.layout.list_view_row, myObjs);

            mainActivity.myAutoComplete.setAdapter(mainActivity.myAdapter);

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }





}