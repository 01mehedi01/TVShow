package com.mehedi.user.tvshow.GetTv_seasons;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mehedi.user.tvshow.R;



import java.util.List;

/**
 * Created by User on 11/28/2018.
 */

public class SeasonAdeptar extends RecyclerView.Adapter<SeasonAdeptar.ShowSeasonViewHolder> {

    private Context context;
    private List<GetTv_seasonsResponse.Episode> responses ;

    public SeasonAdeptar(Context context, List<GetTv_seasonsResponse.Episode> responses) {
        this.context = context;
        this.responses = responses;
    }

    @NonNull
    @Override
    public ShowSeasonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.show_single_seasonlist,parent,false);
        return new ShowSeasonViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowSeasonViewHolder holder, int position) {

        holder.Airdate.setText(responses.get(position).getAirDate());
        if(position % 7 == 0){
           // holder.Airdate.setBackgroundColor(ContextCompat.getColor(context,R.color.green));
            holder.Airdate.setTextColor(ContextCompat.getColor(context, R.color.green));
        }else if(position % 7 == 1){
           // holder.Airdate.setBackgroundColor(ContextCompat.getColor(context,R.color.blue));
            holder.Airdate.setTextColor(ContextCompat.getColor(context, R.color.blue));
        }else if(position % 7 == 2){
           // holder.Airdate.setBackgroundColor(ContextCompat.getColor(context,R.color.purple));
            holder.Airdate.setTextColor(ContextCompat.getColor(context, R.color.purple));
        }else if(position % 7 == 3){
            //holder.Airdate.setBackgroundColor(ContextCompat.getColor(context,R.color.orange));
            holder.Airdate.setTextColor(ContextCompat.getColor(context, R.color.orange));
        }else if(position % 7 == 4){
           // holder.Airdate.setBackgroundColor(ContextCompat.getColor(context,R.color.darkblue));
            holder.Airdate.setTextColor(ContextCompat.getColor(context, R.color.darkblue));
        }else if(position % 7 == 5){
           // holder.Airdate.setBackgroundColor(ContextCompat.getColor(context,R.color.darkpurple));
            holder.Airdate.setTextColor(ContextCompat.getColor(context, R.color.darkpurple));
        }else if(position % 7 == 6){
           // holder.Airdate.setBackgroundColor(ContextCompat.getColor(context,R.color.darkgreen));
            holder.Airdate.setTextColor(ContextCompat.getColor(context, R.color.darkgreen));
        }

        //holder.Airdate.setText(responses.get(position).getAirDate());
        holder.episodName.setText(responses.get(position).getName());
        holder.seasonName.setText("Season "+ String.valueOf(responses.get(position).getSeasonNumber()));

    }

    @Override
    public int getItemCount() {
        return responses.size();
    }

    public class ShowSeasonViewHolder extends RecyclerView.ViewHolder{
         private TextView Airdate;
         private TextView episodName;
         private TextView seasonName;
        public ShowSeasonViewHolder(View itemView) {
            super(itemView);

            Airdate = itemView.findViewById(R.id.single_airdateid);
            episodName = itemView.findViewById(R.id.single_episodnameid);
            seasonName = itemView.findViewById(R.id.single_seasonNmaeid);
        }
    }
}
