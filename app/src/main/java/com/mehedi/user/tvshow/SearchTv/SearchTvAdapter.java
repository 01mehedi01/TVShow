package com.mehedi.user.tvshow.SearchTv;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mehedi.user.tvshow.Activity.ShowSeasonsActivity;
import com.mehedi.user.tvshow.R;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.squareup.picasso.Picasso;



import java.util.List;

/**
 * Created by User on 5/26/2018.
 */

public class SearchTvAdapter extends RecyclerView.Adapter<SearchTvAdapter.SearchTvViewHolder> {

    Bitmap PostarPasthIcone;



    private Context context;
    private List<SearchResponse.Result> searchResponses;

    public SearchTvAdapter(Context context, List<SearchResponse.Result> searchResponses) {
        this.context = context;
        this.searchResponses = searchResponses;
    }



    @NonNull
    @Override
    public SearchTvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.show_single_tv_list,parent,false);
        return new SearchTvViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchTvViewHolder holder, int position) {
      holder.Name.setText(searchResponses.get(position).getName());
      holder.language.setText(searchResponses.get(position).getOriginalLanguage());
      holder.Overview.setText(searchResponses.get(position).getOverview());




        Uri iconUri = Uri.parse("http://image.tmdb.org/t/p/w185"+searchResponses.get(position).getBackdropPath());
////        Uri iconUri = Uri.parse("http://image.tmdb.org/t/p/w185//xlJCThq1JTDsHRkdxz5mCLvp6Iv.jpg");

       if(searchResponses.get(position).getBackdropPath()!=null) {
           Picasso.get()
                   .load(iconUri)
                   .into(holder.PostarPath);
       }else{
           holder.PostarPath.setImageResource(R.drawable.noimage);
       }
      //  Toast.makeText(context, "Path"+searchResponses.get(position).getBackdropPath(), Toast.LENGTH_SHORT).show();

       // holder.PostarPath.setImageURI(Uri.parse("http://image.tmdb.org/t/p/w185"+searchResponses.get(position).getPosterPath()));
       // Toast.makeText(context, "Path"+searchResponses.get(position).getPosterPath(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return searchResponses.size();
    }

    public class SearchTvViewHolder extends RecyclerView.ViewHolder{
        TextView Name;
        TextView language;
        //TextView Overview;
        ExpandableTextView Overview;
        ImageView PostarPath;

        public SearchTvViewHolder(View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.single_tv_show_name);
            language = itemView.findViewById(R.id.single_tv_show_originalLanguage);
            Overview = itemView.findViewById(R.id.single_tv_show_overview);
            PostarPath = itemView.findViewById(R.id.single_tv_show_postar_pathId);


        }
    }
}
