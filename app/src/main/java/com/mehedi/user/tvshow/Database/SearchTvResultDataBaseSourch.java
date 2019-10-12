package com.mehedi.user.tvshow.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by User on 1/31/2018.
 */

public class SearchTvResultDataBaseSourch {
    public static final String TAG = "SearchTvResultDataBaseSourch.java";
    private SearchTvResultDataBaseHelper edh;
    private SQLiteDatabase db;

    public SearchTvResultDataBaseSourch(Context context) {
       edh = new SearchTvResultDataBaseHelper(context);
    }

    public void Open(){
        db = edh.getWritableDatabase();
    }
    public void Close(){
        db.close();
    }


    //****************************** Insert Tv Name  ***********************************************
   public  boolean InserteValues(PojoClass event) {
       this.Open();
       ContentValues values = new ContentValues();
       values.put(SearchTvResultDataBaseHelper.EVENT_NAME,event.getName());
       long Stsatus = db.insert(SearchTvResultDataBaseHelper.SUCCESS_RESULT_TABLE,null,values);
       this.Close();
       if(Stsatus>0)
       {
           return  true;
       }
       else{
           return false;
       }
    }

//    public ArrayList<PojoClass> getAllName_Like_search_name(String likename){
//
//        this.Open();
//
//        String sql = "SELECT * FROM " + SearchTvResultDataBaseHelper.SUCCESS_RESULT_TABLE + " WHERE " + SearchTvResultDataBaseHelper.EVENT_NAME + " LIKE '%" + likename + "%'";
//        Cursor cursor = db.rawQuery(sql, null);
//       // Cursor cursor = db.query(SearchTvResultDataBaseHelper.SUCCESS_RESULT_TABLE,sqlselect,"E_NAME LIKE ?",new String[]{"%"+likename+"%"},null,null,null);
//        ArrayList<PojoClass> events = new ArrayList<>();
//        if(cursor!=null && cursor.getCount()>0)
//        {      cursor.moveToFirst();
//            do{
//                int id = cursor.getInt(cursor.getColumnIndex(SearchTvResultDataBaseHelper.EVENT_ID));
//                String Name = cursor.getString(cursor.getColumnIndex(SearchTvResultDataBaseHelper.EVENT_NAME));
//
//
//                PojoClass event = new PojoClass(Name,id);
//                events.add(event);
//
//            }while(cursor.moveToNext());
//        }else{
//            //Toast.makeText(Context, "", Toast.LENGTH_SHORT).show();
//        }
//        cursor.close();
//        this.Close();
//        return events;
//    }

    public ArrayList<PojoClass> getAll(){

        this.Open();

        String sql = "SELECT * FROM " + SearchTvResultDataBaseHelper.SUCCESS_RESULT_TABLE ;
        Cursor cursor = db.rawQuery(sql, null);
        // Cursor cursor = db.query(SearchTvResultDataBaseHelper.SUCCESS_RESULT_TABLE,sqlselect,"E_NAME LIKE ?",new String[]{"%"+likename+"%"},null,null,null);
        ArrayList<PojoClass> events = new ArrayList<>();
        if(cursor!=null && cursor.getCount()>0)
        {      cursor.moveToFirst();
            do{
                int id = cursor.getInt(cursor.getColumnIndex(SearchTvResultDataBaseHelper.EVENT_ID));
                String Name = cursor.getString(cursor.getColumnIndex(SearchTvResultDataBaseHelper.EVENT_NAME));


                PojoClass event = new PojoClass(Name,id);
                events.add(event);

            }while(cursor.moveToNext());
        }else{
            //Toast.makeText(Context, "", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
        this.Close();
        return events;
    }

    //****************************** Check Tv Name That alrady Save or not  ***********************************************
    public boolean MatchTvName(String Tvname){

        this.Open();
        boolean good = false;
        ArrayList<PojoClass> projoClasses = new ArrayList<>();

        // String[] projection = {DataBaseHelper.USERNAME ,DataBaseHelper.USERPASSWORD};
        String  selection = SearchTvResultDataBaseHelper.EVENT_NAME +" =?";
        String[] selectionargs = {Tvname};

        Cursor cursor = db.query(SearchTvResultDataBaseHelper.SUCCESS_RESULT_TABLE
                ,null
                , selection
                ,selectionargs
                ,null
                ,null
                ,null);


        if(cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();
            do{

                String username = cursor.getString(cursor.getColumnIndex(SearchTvResultDataBaseHelper.EVENT_NAME));

                if(Tvname.equals(username)){

                    good = true;
                    break;
                }
                else {
                    good = false;
                }

            }while (cursor.moveToNext());
        }
        cursor.close();
        this.Close();
        return good;
    }



    //****************************** Read all Tv name For Search Suggetion  ***********************************************
    public PojoClass[] read(String searchTerm) {
       this.Open();
        // select query

        String sql = "SELECT * FROM " + SearchTvResultDataBaseHelper.SUCCESS_RESULT_TABLE + " WHERE "
                + SearchTvResultDataBaseHelper.EVENT_NAME + " LIKE '%" + searchTerm + "%'"+ " ORDER BY "
                + SearchTvResultDataBaseHelper.EVENT_ID  + " DESC " + "LIMIT 0,5";

//        String sql = "";
//        sql += "SELECT * FROM " + SearchTvResultDataBaseHelper.SUCCESS_RESULT_TABLE;
//        sql += " WHERE " + SearchTvResultDataBaseHelper.EVENT_NAME + " LIKE '%" + searchTerm + "%'";
//        sql += " ORDER BY " + SearchTvResultDataBaseHelper.EVENT_ID  + " DESC ";
//        sql += " LIMIT 0,5";5



        // execute the query
        Cursor cursor = db.rawQuery(sql, null);

        int recCount = cursor.getCount();

        PojoClass[] ObjectItemData = new PojoClass[recCount];
        int x = 0;

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                String objectName = cursor.getString(cursor.getColumnIndex(SearchTvResultDataBaseHelper.EVENT_NAME));

                PojoClass myObject = new PojoClass(objectName);

                ObjectItemData[x] = myObject;

                x++;

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return ObjectItemData;

    }

    /*
    public boolean deleteE(int empId){
        this.Open();
        int deletedRow = db.delete(EventDataBaseHelper.EVENT_TABLE,EventDataBaseHelper.EVENT_ID+"="+empId,
                null);
        this.Close();
        if(deletedRow > 0){
            return true;
        }else{
            return false;
        }
    }

    public boolean updateEvent(Event event){
        this.Open();
        ContentValues values = new ContentValues();
        values.put(EventDataBaseHelper.EVENT_NAME,event.getEvent_Name());
        values.put(EventDataBaseHelper.EVENT_AMOUNT,event.getEvent_Amount());

        int updatedRow = db.update(EventDataBaseHelper.EVENT_TABLE,
                values,
                EventDataBaseHelper.EVENT_ID+"="+event.getEvent_ID()
                ,null);
        this.Close();
        if(updatedRow > 0){
            return true;
        }else{
            return false;
        }
    }

    public Event getEventById(int id){
        this.Open();
        Event event = null;
        Cursor cursor = db.query(EventDataBaseHelper.EVENT_TABLE,null,EventDataBaseHelper.EVENT_ID+"="+id,null,null,null,null);
        cursor.moveToFirst();
        if(cursor != null && cursor.getCount() > 0){
            String name = cursor.getString(cursor.getColumnIndex(EventDataBaseHelper.EVENT_NAME));
            String designation = cursor.getString(cursor.getColumnIndex(EventDataBaseHelper.EVENT_AMOUNT));
            event = new Event(id,name,designation);
        }
        return event;
    }*/
}