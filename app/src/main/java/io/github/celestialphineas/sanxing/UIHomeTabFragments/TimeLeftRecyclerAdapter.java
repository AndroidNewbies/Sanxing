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

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.celestialphineas.sanxing.R;
import io.github.celestialphineas.sanxing.SanxingBackend.TimeLeftRepo;
import io.github.celestialphineas.sanxing.UIOperateItemActivities.EditItem.EditTimeLeftActivity;
import io.github.celestialphineas.sanxing.sxObject.TimeLeft;
import io.github.celestialphineas.sanxing.timer.MyDuration;


public class TimeLeftRecyclerAdapter
        extends RecyclerView.Adapter<TimeLeftRecyclerAdapter.TimeLeftViewHolder> {

    private List<TimeLeft> timeLeftList;
    private Context context;
    private Calendar timeLeftStartCalendar = Calendar.getInstance();
    private Calendar timeLeftDueCalendar = Calendar.getInstance();

    TimeLeftRecyclerAdapter() { }

    TimeLeftRecyclerAdapter(List<TimeLeft> time_left_list) {
        final TimeLeftRepo repo = new TimeLeftRepo(context);

        timeLeftList = time_left_list;
        //renew state of each item
        for (TimeLeft timeLeft:timeLeftList){
            long diff= MyDuration.durationFromNowtoB(timeLeft.getEndLocalDate());
            if (diff<0) {
                timeLeft.setState(2);
                repo.update(timeLeft);
            }
        }
    }

    public List<TimeLeft> getTimeLeftList() { return timeLeftList; }
    public void setTimeLeftList(List<TimeLeft> time_left_list) {
        timeLeftList = time_left_list;
        notifyDataSetChanged();
    }

    class TimeLeftViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.card_root_view)      CardView cardView;
        @BindView(R.id.card_headline)       TextView timeLeftTitle;
        @BindView(R.id.card_subheading)     TextView timeLeftDueTime;
        @BindView(R.id.card_countdown)      TextView timeLeftCountDown;
        @BindView(R.id.card_description)    TextView timeLeftDescription;
        @BindView(R.id.card_button_edit)    AppCompatButton buttonEdit;
        @BindView(R.id.card_button_delete)  AppCompatButton buttonDelete;
        @BindString(R.string.right_arrow)   String rightArrow;
        @BindString(R.string.unit_year)     String unitYearString;
        @BindString(R.string.unit_month)    String unitMonthString;
        @BindString(R.string.unit_week)     String unitWeekString;
        @BindString(R.string.unit_day)      String unitDayString;
        @BindString(R.string.unit_hour)     String unitHourString;
        @BindString(R.string.unit_minute)   String unitMinuteString;
        @BindString(R.string.end)   String end;
        TimeLeftViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public TimeLeftViewHolder onCreateViewHolder(ViewGroup parent, int viewGroup) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.home_card, parent, false);
        context = parent.getContext();
        return new TimeLeftViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final TimeLeftViewHolder holder, final int position) {
        final TimeLeft timeLeft_at_position=timeLeftList.get(position);
        holder.timeLeftTitle.setText(timeLeft_at_position.getTitle());
        String beginDateString = timeLeft_at_position.getBeginDate().substring(0,11);
        String endDateString = timeLeft_at_position.getEndDate().substring(0,11);
        /*try {
            DateFormat sdf = android.text.format.DateFormat.getDateFormat(context);
            Date date = new SimpleDateFormat("yyyy-M-d", Locale.ENGLISH).parse(beginDateString);
            beginDateString = sdf.format(date);
            date = new SimpleDateFormat("yyyy-M-d", Locale.ENGLISH).parse(endDateString);
            endDateString = sdf.format(date);
        } catch (Exception e) {}*/
        // Bind the view with data
        // Please make changes to the timeLeftStartCalendar and timeLeftDueCalendar
        // to match the date with the model
        holder.timeLeftDueTime.setText(beginDateString + holder.rightArrow + endDateString);
        // Calculate the time difference and print it here
        //done by Lin
        String endtime=timeLeft_at_position.getEndDate();
        long diff= MyDuration.durationFromNowtoB(endtime);
        diff /=1000;
        long years= diff/60/60/24/365;
        diff %=31536000;
        long days= diff/60/60/24;
        diff %=86400;
        long hours=diff/60/60;
        diff %=3600;
        long minutes=diff/60;
        if (diff>0){
            if (years>0) holder.timeLeftCountDown.setText(years
                    + holder.unitYearString + days + holder.unitDayString + hours + ":" + minutes);
            else holder.timeLeftCountDown.setText(days+ holder.unitDayString + hours + ":" + minutes);
        }
        else {

            holder.timeLeftCountDown.setText(holder.end);// this code will never be executed
        }
        // Get the description and print it out here
        holder.timeLeftDescription.setText(timeLeft_at_position.getContent());//content is the description

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TransitionManager.beginDelayedTransition(holder.cardView);
                if(holder.timeLeftDescription.getVisibility() == View.VISIBLE) {
                    holder.timeLeftDescription.setVisibility(View.GONE);
                } else {
                    holder.timeLeftDescription.setVisibility(View.VISIBLE);
                }
            }
        });

        // Button edit behavior
        holder.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditTimeLeftActivity.class);
                intent.putExtra("position",position);

                ((Activity)context).startActivityForResult(intent, 5);
            }
        });
        // Button delete behavior
        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final TimeLeftRepo repo = new TimeLeftRepo(context);
                remove(position);
                View.OnClickListener redo = new View.OnClickListener() {
                    @Override public void onClick(View view) {
                        add(timeLeft_at_position, position);
                        // Restore the lazily deleted database entry
                        timeLeft_at_position.setState(1);
                        repo.update(timeLeft_at_position);

                    }
                };
                Snackbar.make(view, R.string.snack_one_item_deleted, R.integer.undo_timeout)
                        .setAction(R.string.undo, redo)
                        .show();
                // Lazy delete a database entry
                timeLeft_at_position.setState(0);
                repo.update(timeLeft_at_position);
            }
        });
        //将position保存在itemView的Tag中，以便点击时进行获取
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return timeLeftList == null ? 0 : timeLeftList.size();
    }

    // Remove an entry
    public void remove(int position) {
        try { timeLeftList.remove(position); } catch (Exception e) {
            Log.w("TimeLeftRecyclerAdapter", "remove: " + position + ", " + timeLeftList.size());
        }
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, timeLeftList.size());
    }

    // Add an entry
    public void add(TimeLeft timeLeft, int position) {
        try { timeLeftList.add(position, timeLeft); } catch (Exception e) {
            Log.w("TimeLeftRecyclerAdapter", "add: " + position + ", " + timeLeftList.size());
        }
        notifyItemInserted(position);
        notifyItemRangeChanged(position, timeLeftList.size());
    }
}
