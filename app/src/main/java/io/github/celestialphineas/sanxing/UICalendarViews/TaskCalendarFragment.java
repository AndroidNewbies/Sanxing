package io.github.celestialphineas.sanxing.UICalendarViews;

import android.app.DatePickerDialog;
import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.transition.TransitionManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.celestialphineas.sanxing.R;
import io.github.celestialphineas.sanxing.sxObject.Task;

public class TaskCalendarFragment extends Fragment {
    @BindView(R.id.task_calendar_view)          CompactCalendarView taskCalendarView;
    @BindView(R.id.task_calendar_year_month)    AppCompatTextView taskCalendarYearMonth;
    @BindView(R.id.task_calendar_content)       ViewGroup taskCalendarContent;
    @BindView(R.id.task_calendar_card)          ViewGroup taskCalendarCard;
    @BindView(R.id.task_detail_content)         ViewGroup taskDetailContent;
    @BindView(R.id.task_detail_card)            ViewGroup taskDetailCard;
    final Calendar selectedCalendar = Calendar.getInstance();

    public TaskCalendarFragment() { }

    public static TaskCalendarFragment newInstance() {
        TaskCalendarFragment fragment = new TaskCalendarFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_calendar, container, false);
        ButterKnife.bind(this, view);
        taskCalendarView.setUseThreeLetterAbbreviation(true);
        updateTaskCalendarYearMonth();

        taskCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                selectedCalendar.setTimeInMillis(dateClicked.getTime());
                List<Event> events = taskCalendarView.getEvents(dateClicked);
                updateTaskCalendarYearMonth();
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                selectedCalendar.setTimeInMillis(firstDayOfNewMonth.getTime());
                updateTaskCalendarYearMonth();
            }
        });

        return view;
    }

    final void updateTaskCalendarYearMonth() {
        DateFormat sdf = new SimpleDateFormat("YYYY MMMM");
        taskCalendarYearMonth.setText(sdf.format(selectedCalendar.getTime()));
    }

    @OnClick(R.id.task_calendar_year_month)
    void taskCalendarYearMonthOnClickBehavior() {
        DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                selectedCalendar.set(Calendar.YEAR, year);
                selectedCalendar.set(Calendar.MONTH, monthOfYear);
                selectedCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                taskCalendarView.setCurrentDate(selectedCalendar.getTime());
                updateTaskCalendarYearMonth();
            }
        };
        new DatePickerDialog(this.getContext(), dateListener,
                selectedCalendar.get(Calendar.YEAR),
                selectedCalendar.get(Calendar.MONTH),
                selectedCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @OnClick(R.id.calendar_go_to_today)
    void gotoTodayOnclickBehavior() {
        Date now = new Date();
        selectedCalendar.setTimeInMillis(now.getTime());
        taskCalendarView.setCurrentDate(now);
        updateTaskCalendarYearMonth();
    }

    // Click to expand/collapse the calendar view
    @OnClick(R.id.expand_task_calendar_view)
    void expandTaskCalendarViewOnClick() {
        TransitionManager.beginDelayedTransition(taskCalendarCard);
        TransitionManager.beginDelayedTransition(taskCalendarContent);
        if(taskCalendarContent.getVisibility() == View.VISIBLE) {
            taskCalendarContent.setVisibility(View.GONE);
        } else {
            taskCalendarContent.setVisibility(View.VISIBLE);
        }
    }

    // Click to expand/collapse the detail view
    @OnClick(R.id.expand_task_detail_view)
    void expandTaskDetailViewOnClick() {
        TransitionManager.beginDelayedTransition(taskDetailCard);
        TransitionManager.beginDelayedTransition(taskDetailContent);
        if(taskDetailContent.getVisibility() == View.VISIBLE) {
            taskDetailContent.setVisibility(View.GONE);
        } else {
            taskDetailContent.setVisibility(View.VISIBLE);
        }
    }
}