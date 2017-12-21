package io.github.celestialphineas.sanxing.UIHomeTabFragments;

/**
 * Created by apple on 2017/11/3.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.transition.TransitionManager;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.threeten.bp.LocalDateTime;

import java.util.List;


import butterknife.BindInt;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.celestialphineas.sanxing.R;
import io.github.celestialphineas.sanxing.SanxingBackend.HabitRepo;
import io.github.celestialphineas.sanxing.UIOperateItemActivities.EditItem.EditHabitActivity;
import io.github.celestialphineas.sanxing.sxObject.Habit;
import io.github.celestialphineas.sanxing.timer.MyDuration;


public class HabitRecyclerAdapter
        extends RecyclerView.Adapter<HabitRecyclerAdapter.HabitViewHolder> {

    //private int habitFreq = 3;
    private List<Habit> habitList;
    private Context context;

    HabitRecyclerAdapter() { }

    HabitRecyclerAdapter(List<Habit> Habit_list) {
        habitList = Habit_list;
    }

    /*public List<Habit> getHabitList() { return habitList; }
    public void setHabitList(List<Habit> habit_list) {
        habitList = habit_list;
        notifyDataSetChanged();
    }*/

    class HabitViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.card_root_view)              CardView cardView;
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
        final Habit habit_at_position=habitList.get(position);
        habit_at_position.next_day();//renew deadline of the habit
        holder.habitTitle.setText(habit_at_position.getTitle());
        // Set the frequency (in the form of integer, see OperateHabitActivity for definition)
        // Modify the habitFreq private variable
        holder.habitFreq.setText(holder.freqIntToString(habit_at_position.getFrequency()));
        // Bind the view with data
        holder.habitCount.setText(habit_at_position.getRecordnumber()+"/"+habit_at_position.getNeednumber());
        //Get the description and print it out here
        holder.habitDescription.setText(habit_at_position.getContent());
        // Button edit behavior
        holder.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditHabitActivity.class);
                intent.putExtra("position",position);
                //  Send the object to edit via intent
                ((Activity)context).startActivityForResult(intent, 4);
            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TransitionManager.beginDelayedTransition(holder.cardView);
                if(holder.habitDescription.getVisibility() == View.VISIBLE) {
                    holder.habitDescription.setVisibility(View.GONE);
                } else {
                    holder.habitDescription.setVisibility(View.VISIBLE);
                }
            }
        });

        // Button delete behavior
        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final HabitRepo repo = new HabitRepo(context);
                long diff= MyDuration.durationFromAtoB(habit_at_position.getBeginLocalDate(),
                        LocalDateTime.now());//标准为7天,604800000
                remove(position);
                View.OnClickListener redo = new View.OnClickListener() {
                    @Override public void onClick(View view) {
                        add(habit_at_position, position);
                        habit_at_position.setState(1);
                        repo.update(habit_at_position);
                    }
                };
                if (diff>604800000&& habit_at_position.getRecord().size()>4)//习惯持续时间大于7天并且完整打卡次数大于4次
                {
                    Snackbar.make(view, R.string.snack_one_item_finished, R.integer.undo_timeout)
                            .setAction(R.string.undo, redo)
                            .show();
                    //Lazy delete a database entry
                    habit_at_position.setState(2);
                }
                else
                {
                    Snackbar.make(view, R.string.snack_one_item_deleted, R.integer.undo_timeout)
                            .setAction(R.string.undo, redo)
                            .show();
                    //Lazy delete a database entry
                    habit_at_position.setState(0);
                }
                repo.update(habit_at_position);
            }
        });
        // Button check
        holder.buttonCheck.setVisibility(View.VISIBLE);
        holder.buttonCheck.setText(R.string.button_check);
        holder.buttonCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HabitRepo repo = new HabitRepo(context);
                boolean status = true;
                // Check the checking status
                status=habit_at_position.getRecordnumber()==habit_at_position.getNeednumber();
                if(status) {
                    // The user has already checked today
                    Snackbar.make(view, holder.alreadyChecked, holder.snackBarTimeout)
                            .show();

                } else {
                    // The user has not checked yet
                    habit_at_position.addRecord();
                    holder.habitCount.setText(habit_at_position.getRecordnumber()+"/"+habit_at_position.getNeednumber());
                    // write the new change to database and refresh view
                    repo.update(habit_at_position);
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
            Log.w("HabitRecyclerAdapter", "remove: " + position + ", " + habitList.size());
        }
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, habitList.size());
    }

    // Add an entry
    public void add(Habit habit, int position) {
        try { habitList.add(position, habit); } catch (Exception e) {
            Log.w("HabitRecyclerAdapter", "add: " + position + ", " + habitList.size());
        }
        notifyItemInserted(position);
        notifyItemRangeChanged(position, habitList.size());
    }
}
