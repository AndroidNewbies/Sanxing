package io.github.celestialphineas.sanxing;

/**
 * Created by apple on 2017/11/3.
 */

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;



public class HabitRecyclerAdapter
        extends RecyclerView.Adapter<HabitRecyclerAdapter.HabitViewHolder> {
    private String mArguments="l";
    private int count;
    private List<Habit> mlist;
    HabitRecyclerAdapter() {
        // Empty constructor
    }

    HabitRecyclerAdapter(List<Habit> Habit_list) {
        mlist = Habit_list;
        count = 2;
    }

    class HabitViewHolder extends RecyclerView.ViewHolder {
        TextView cardTitle;
        TextView cardSubheading;
        HabitViewHolder(View itemView) {
            super(itemView);
            cardTitle = itemView.findViewById(R.id.task_card_headline);
            cardSubheading = itemView.findViewById(R.id.task_card_subheading);
            cardTitle.setText(mArguments);
            cardSubheading.setText("Good night, my friend!");
        }
        HabitViewHolder(View itemView,String title) {
            super(itemView);

            cardTitle = itemView.findViewById(R.id.task_card_headline);
            cardSubheading = itemView.findViewById(R.id.task_card_subheading);
            cardTitle.setText(title);
        }
        HabitViewHolder(View itemView,List<Habit> list) {
            super(itemView);

            mlist = list;
            cardTitle = itemView.findViewById(R.id.task_card_headline);

        }
    }

    @Override
    public HabitViewHolder onCreateViewHolder(ViewGroup parent, int viewGroup) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_card, parent, false);

        if (!mArguments.equals("l")||mlist==null) return new HabitViewHolder(itemView,mArguments);
        return new HabitViewHolder(itemView,mlist);
    }

    @Override
    public void onBindViewHolder(HabitViewHolder holder, int position) {

        holder.cardTitle.setText(mlist.get(position).getContent());
        // TODO: Bind the view with data
    }

    @Override
    public int getItemCount() {
        // TODO: Implement this, and return how many cards concerned
       return mlist==null?0:mlist.size();

    }

}
