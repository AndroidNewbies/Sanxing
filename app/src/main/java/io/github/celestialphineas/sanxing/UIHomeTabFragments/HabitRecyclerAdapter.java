package io.github.celestialphineas.sanxing.UIHomeTabFragments;

/**
 * Created by apple on 2017/11/3.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.celestialphineas.sanxing.R;
import io.github.celestialphineas.sanxing.UIOperateItemActivities.EditItem.EditHabitActivity;
import io.github.celestialphineas.sanxing.UIOperateItemActivities.EditItem.EditTaskActivity;
import io.github.celestialphineas.sanxing.sxObject.Habit;


public class HabitRecyclerAdapter
        extends RecyclerView.Adapter<HabitRecyclerAdapter.HabitViewHolder> {
    private String mArguments="l";
    private int count;
    private Context context;
    private List<Habit> mlist;

    HabitRecyclerAdapter() { }

    HabitRecyclerAdapter(List<Habit> Habit_list, Context context_) {
        mlist = Habit_list;
        count = 2;
        context = context_;
    }

    class HabitViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.card_headline)       TextView taskTitle;
        @BindView(R.id.card_subheading)     TextView taskDueTime;
        @BindView(R.id.card_countdown)      TextView taskCountdown;
        @BindView(R.id.card_description)    TextView taskDescription;
        @BindView(R.id.card_button_edit)
        AppCompatButton buttonEdit;
        @BindView(R.id.card_button_delete)  AppCompatButton buttonDelete;
        HabitViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public HabitViewHolder onCreateViewHolder(ViewGroup parent, int viewGroup) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.home_card, parent, false);
        context = parent.getContext();
        return new HabitViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HabitViewHolder holder, int position) {

        holder.taskTitle.setText(mlist.get(position).getContent());
        // TODO: Bind the view with data
        // Button edit behavior
        holder.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditHabitActivity.class);
                // TODO: Send the object to edit via intent
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        // TODO: Implement this, and return how many cards concerned
       return mlist==null?0:mlist.size();

    }
}
