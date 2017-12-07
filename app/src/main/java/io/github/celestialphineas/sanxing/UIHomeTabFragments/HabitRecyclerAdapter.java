package io.github.celestialphineas.sanxing.UIHomeTabFragments;

/**
 * Created by apple on 2017/11/3.
 */

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


import butterknife.BindInt;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.celestialphineas.sanxing.R;
import io.github.celestialphineas.sanxing.UIOperateItemActivities.Base.OperateHabitActivityBase;
import io.github.celestialphineas.sanxing.UIOperateItemActivities.EditItem.EditHabitActivity;
import io.github.celestialphineas.sanxing.sxObject.Habit;
import io.github.celestialphineas.sanxing.sxObject.Task;


public class HabitRecyclerAdapter
        extends RecyclerView.Adapter<HabitRecyclerAdapter.HabitViewHolder> {

    private int habitFreq = 3;
    private List<Habit> habitList;
    private Context context;

    HabitRecyclerAdapter() { }

    HabitRecyclerAdapter(List<Habit> Habit_list) {
        habitList = Habit_list;
    }

    public List<Habit> getHabitList() { return habitList; }
    public void setHabitList(List<Habit> habit_list) {
        habitList = habit_list;
        notifyDataSetChanged();
    }

    class HabitViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.card_headline)               TextView habitTitle;
        @BindView(R.id.card_subheading)             TextView habitFreq;
        @BindView(R.id.card_countdown)              TextView habitCount;
        @BindView(R.id.card_description)            TextView habitDescription;
        @BindView(R.id.card_button_edit)            AppCompatButton buttonEdit;
        @BindView(R.id.card_button_delete)          AppCompatButton buttonDelete;
        @BindView(R.id.card_button_check)           AppCompatButton buttonCheck;
        @BindString(R.string.thrice_a_day)          String thriceADay;
        @BindString(R.string.twice_a_day)           String twiceADay;
        @BindString(R.string.every_day)             String everyDay;
        @BindString(R.string.once_two_days)         String onceTwoDays;
        @BindString(R.string.once_three_days)       String onceThreeDays;
        @BindString(R.string.once_a_week)           String onceAWeek;
        @BindString(R.string.once_a_fortnight)      String onceAFortnight;
        @BindString(R.string.once_a_month)          String onceAMonth;
        @BindString(R.string.snack_already_checked)     String alreadyChecked;
        @BindInt(R.integer.snack_bar_timeout)           int snackBarTimeout;
        HabitViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        public String freqIntToString(int freq) {
            switch (freq) {
                case 0: return thriceADay;
                case 1: return twiceADay;
                case 2: return everyDay;
                case 3: return onceTwoDays;
                case 4: return onceThreeDays;
                case 5: return onceAWeek;
                case 6: return onceAFortnight;
                case 7: default: return onceAMonth;
            }
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
    public void onBindViewHolder(final HabitViewHolder holder, final int position) {
        holder.habitTitle.setText(habitList.get(position).getContent());
        // TODO: Set the frequency (in the form of integer, see OperateHabitActivity for definition)
        // TODO: Modify the habitFreq private variable
        holder.habitFreq.setText(holder.freqIntToString(habitFreq));
        // TODO: Bind the view with data
        holder.habitCount.setText("3 Days");
        // TODO: Get the description and print it out here
        holder.habitDescription.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                "Curabitur non mauris lorem. Mauris ac ex nec purus feugiat venenatis. Suspendisse fringilla.");
        // Button edit behavior
        holder.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditHabitActivity.class);
                // TODO: Send the object to edit via intent
                context.startActivity(intent);
            }
        });
        // Button delete behavior
        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Habit habit = habitList.get(position);
                remove(position);
                View.OnClickListener redo = new View.OnClickListener() {
                    @Override public void onClick(View view) {
                        add(habit, position);
                        // TODO: Restore the lazily deleted database entry
                    }
                };
                Snackbar.make(view, R.string.snack_one_item_deleted, R.integer.undo_timeout)
                        .setAction(R.string.undo, redo)
                        .show();
                // TODO: Lazy delete a database entry
            }
        });
        // Button check
        holder.buttonCheck.setVisibility(View.VISIBLE);
        holder.buttonCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean status = true;
                // TODO: Check the checking status
                if(status) {
                    // The user has already checked today
                    Snackbar.make(view, holder.alreadyChecked, holder.snackBarTimeout)
                            .show();
                } else {
                    // The user has not checked yet
                    // TODO: Check, write the database and refresh view
                }
            }
        });
    }

    @Override
    public int getItemCount() {
       return habitList == null ? 0 : habitList.size();
    }

    // Remove an entry
    public void remove(int position) {
        try { habitList.remove(position); } catch (Exception e) {
            Log.e("HabitRecyclerAdapter", "remove: " + position + ", " + habitList.size());
        }
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, habitList.size());
    }

    // Add an entry
    public void add(Habit habit, int position) {
        try { habitList.add(position, habit); } catch (Exception e) {
            Log.e("HabitRecyclerAdapter", "add: " + position + ", " + habitList.size());
        }
        notifyItemInserted(position);
        notifyItemRangeChanged(position, habitList.size());
    }
}
