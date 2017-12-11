package io.github.celestialphineas.sanxing.UICalendarViews;

import android.app.DatePickerDialog;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.os.Bundle;
import android.support.transition.TransitionManager;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.AppCompatTextView;
import android.text.Html;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.celestialphineas.sanxing.R;
import io.github.celestialphineas.sanxing.sxObject.Task;

public class TaskCalendarFragment extends Fragment {
    @BindView(R.id.task_calendar_view)              CompactCalendarView taskCalendarView;
    @BindView(R.id.task_calendar_year_month)        AppCompatTextView taskCalendarYearMonth;
    @BindView(R.id.frag_task_root_linear_layout)    ViewGroup fragTaskRootLinearLayout;
    @BindView(R.id.task_calendar_content)           ViewGroup taskCalendarContent;
    @BindView(R.id.task_calendar_card)              ViewGroup taskCalendarCard;
    @BindView(R.id.task_detail_content)             ViewGroup taskDetailContent;
    @BindView(R.id.task_detail_card)                ViewGroup taskDetailCard;
    @BindView(R.id.task_detail_text)                AppCompatTextView taskDetailText;
    final Calendar selectedCalendar = Calendar.getInstance();
    final List<Event> events = new ArrayList<>();

    public TaskCalendarFragment() { }

    public static TaskCalendarFragment newInstance() {
        TaskCalendarFragment fragment = new TaskCalendarFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // An inner class for storing the task event details
    class EventDetailObject {
        private String title, timeString, description; private int importance;
        EventDetailObject(String title, String timeString, String description, int importance) {
            this.title = title; this.timeString = timeString;
            this.description = description; this.importance = importance;
        }
        String getHTML() {
            StringBuilder result = new StringBuilder();
            String colorString = "#" + Integer.toHexString(getColorByImportance(importance) & 0xFFFFFF);
            result.append("<p>");
                result.append("<big><big><b><font color=\"" + colorString + "\">");
                    result.append(timeString + " ");
                    result.append(title);
                result.append("</font></b></big></big><br/>");
                result.append(description);
            result.append("</p>");
            return result.toString();
        }
        @Override
        public String toString() { return getHTML(); }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_calendar, container, false);
        ButterKnife.bind(this, view);
        taskCalendarView.setUseThreeLetterAbbreviation(true);
        updateTaskCalendarYearMonth();

        // TODO: Get a list of events (tasks) from the model
        // The event list will be later drawn on the calendar
        // Below shows an example: 3 events are inserted
        // 1. The first argument of events should be a color, use the private method
        // getColorByImportance to get the color by importance of the task.
        // 2. The second argument is the date of the event in milliseconds
        // 3. The third should be an object of the event
        //    Constructor: EventDetailObject(String title, String time, String description, int importance)
        events.add(new Event(getColorByImportance(0), new Date().getTime() + 10000000,
                new EventDetailObject("WTH!", "14:00", "Brand new world!", 0)));
        events.add(new Event(getColorByImportance(3), new Date().getTime() + 120000000,
                new EventDetailObject("Hello!", "12:00", "Lalala! Lalala~~~", 3)));
        events.add(new Event(getColorByImportance(2), new Date().getTime() + 120000200,
                new EventDetailObject("World!", "13:00", "Hahahahahahahaha...", 2)));
        // End of TODO

        // This will add the tasks in the "events" list to the calendar view
        taskCalendarView.addEvents(events);
        taskCalendarView.shouldDrawIndicatorsBelowSelectedDays(true);
        updateTaskDetails(selectedCalendar.getTime());

        taskCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                selectedCalendar.setTimeInMillis(dateClicked.getTime());
                updateTaskDetails(dateClicked);
                updateTaskCalendarYearMonth();
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                selectedCalendar.setTimeInMillis(firstDayOfNewMonth.getTime());
                updateTaskDetails(firstDayOfNewMonth);
                updateTaskCalendarYearMonth();
            }
        });
        return view;
    }

    // Give the color by the importance of the task (0~4)
    final private int getColorByImportance(int importance) {
        switch (importance) {
            case 0: return ResourcesCompat.getColor(getResources(), R.color.colorTask0, null);
            case 1: return ResourcesCompat.getColor(getResources(), R.color.colorTask1, null);
            case 2: return ResourcesCompat.getColor(getResources(), R.color.colorTask2, null);
            case 3: return ResourcesCompat.getColor(getResources(), R.color.colorTask3, null);
            default: return ResourcesCompat.getColor(getResources(), R.color.colorTask4, null);
        }
    }

    final void updateTaskCalendarYearMonth() {
        DateFormat sdf = new SimpleDateFormat("YYYY MMMM");
        taskCalendarYearMonth.setText(sdf.format(selectedCalendar.getTime()));
    }

    // Update the task detail view
    final void updateTaskDetails(Date dateClicked) {
        List<Event> events = taskCalendarView.getEvents(dateClicked);
        StringBuilder tasksToPrint = new StringBuilder();
        for(Event event : events) {
            tasksToPrint.append(event.getData().toString());
        }
        // Here prints the tasks in the text view.
        // I am just to bored to write another adapter.
        TransitionManager.beginDelayedTransition(taskDetailContent);
        TransitionManager.beginDelayedTransition(fragTaskRootLinearLayout);
        TransitionManager.beginDelayedTransition(taskDetailCard);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            taskDetailText.setText(Html.fromHtml(tasksToPrint.toString(), Html.FROM_HTML_MODE_LEGACY));
        } else {
            taskDetailText.setText(Html.fromHtml(tasksToPrint.toString()));
        }
//        taskDetailText.setText(tasksToPrint);
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
                updateTaskDetails(selectedCalendar.getTime());
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
        updateTaskDetails(selectedCalendar.getTime());
    }

    // Click to expand/collapse the calendar view
    @OnClick(R.id.expand_task_calendar_view)
    void expandTaskCalendarViewOnClick() {
        TransitionManager.beginDelayedTransition(taskCalendarContent);
        TransitionManager.beginDelayedTransition(fragTaskRootLinearLayout);
        TransitionManager.beginDelayedTransition(taskCalendarCard);
        if(taskCalendarContent.getVisibility() == View.VISIBLE) {
            taskCalendarContent.setVisibility(View.GONE);
        } else {
            taskCalendarContent.setVisibility(View.VISIBLE);
        }
    }

    //Click to expand/collapse the detail view
    @OnClick(R.id.expand_task_detail_view)
    void expandTaskDetailViewOnClick() {
        TransitionManager.beginDelayedTransition(taskDetailContent);
        TransitionManager.beginDelayedTransition(fragTaskRootLinearLayout);
        TransitionManager.beginDelayedTransition(taskDetailCard);
        if(taskDetailContent.getVisibility() == View.VISIBLE) {
            taskDetailContent.setVisibility(View.GONE);
        } else {
            taskDetailContent.setVisibility(View.VISIBLE);
        }
    }
}