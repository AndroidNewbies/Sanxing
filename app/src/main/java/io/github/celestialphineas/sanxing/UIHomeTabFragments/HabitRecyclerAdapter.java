package io.github.celestialphineas.sanxing.UIHomeTabFragments;

/**
 * Created by apple on 2017/11/3.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.List;

import io.github.celestialphineas.sanxing.SanxingBackend.Habit;
import io.github.celestialphineas.sanxing.R;


public class HabitRecyclerAdapter
        extends RecyclerView.Adapter<HabitRecyclerAdapter.HabitViewHolder> {
    private String mArguments="l";
    private int count;
    private Context context;
    private List<Habit> mlist;
    HabitRecyclerAdapter() {
        // Empty constructor
    }

    HabitRecyclerAdapter(List<Habit> Habit_list, Context context_) {
        mlist = Habit_list;
        count = 2;
        context = context_;
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
        setAnimation(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        // TODO: Implement this, and return how many cards concerned
       return mlist==null?0:mlist.size();

    }

    private void setAnimation(View viewToAnimate, int position) {
        if(position > count) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            count = position;
        }
    }
}
