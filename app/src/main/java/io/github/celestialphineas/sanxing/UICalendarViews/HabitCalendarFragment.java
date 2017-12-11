package io.github.celestialphineas.sanxing.UICalendarViews;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.transition.TransitionManager;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.AppCompatTextView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.celestialphineas.sanxing.R;

public class HabitCalendarFragment extends Fragment {
    @BindView(R.id.habit_calendar_view)
    CompactCalendarView habitCalendarView;
    @BindView(R.id.habit_calendar_year_month)
    AppCompatTextView habitCalendarYearMonth;
    @BindView(R.id.frag_habit_root_linear_layout)    ViewGroup fragHabitRootLinearLayout;
    @BindView(R.id.habit_calendar_content)           ViewGroup habitCalendarContent;
    @BindView(R.id.habit_calendar_card)              ViewGroup habitCalendarCard;
    @BindView(R.id.habit_detail_content)             ViewGroup habitDetailContent;
    @BindView(R.id.habit_detail_card)                ViewGroup habitDetailCard;
    @BindView(R.id.habit_detail_text)                AppCompatTextView habitDetailText;
    @BindString(R.string.checked)                   String checkedString;
    final Calendar selectedCalendar = Calendar.getInstance();
    final List<Event> events = new ArrayList<>();

    public HabitCalendarFragment() { }

    public static HabitCalendarFragment newInstance() {
        HabitCalendarFragment fragment = new HabitCalendarFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // An inner class for storing the habit event details
    class EventDetailObject {
        private String title, description; private int importance;
        EventDetailObject(String title, String description, int importance) {
            this.title = title;
            this.description = description;
            this.importance = importance;
        }
        String getHTML() {
            StringBuilder result = new StringBuilder();
            String colorString = "#" + Integer.toHexString(getColorByImportance(importance) & 0xFFFFFF);
            result.append("<p>");
            result.append("<big><big><b><font color=\"" + colorString + "\">");
            result.append(title + " ");
            result.append(checkedString);
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
        View view = inflater.inflate(R.layout.fragment_habit_calendar, container, false);
        ButterKnife.bind(this, view);
        habitCalendarView.setUseThreeLetterAbbreviation(true);
        updateHabitCalendarYearMonth();

        // TODO: Get a list of events (habits) from the model
        // The event list will be later drawn on the calendar
        // Below shows an example: 3 events are inserted
        // 1. The first argument of events should be a color, use the private method
        // getColorByImportance to get the color by importance of the habit.
        // 2. The second argument is the date of the event in milliseconds
        // 3. The third should be an object of the event
        //    Constructor: EventDetailObject(String title, String time, String description, int importance)
        events.add(new Event(getColorByImportance(0), new Date().getTime() + 10000000,
                new EventDetailObject("WTH!", "Brand new world!", 0)));
        events.add(new Event(getColorByImportance(3), new Date().getTime() + 120000000,
                new EventDetailObject("Hello!", "Lalala! Lalala~~~", 3)));
        events.add(new Event(getColorByImportance(2), new Date().getTime() + 120000200,
                new EventDetailObject("World!", "Hahahahahahahaha...", 2)));
        // End of TODO

        // This will add the habits in the "events" list to the calendar view
        habitCalendarView.addEvents(events);
        habitCalendarView.shouldDrawIndicatorsBelowSelectedDays(true);
        updateHabitDetails(selectedCalendar.getTime());

        habitCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                selectedCalendar.setTimeInMillis(dateClicked.getTime());
                updateHabitDetails(dateClicked);
                updateHabitCalendarYearMonth();
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                selectedCalendar.setTimeInMillis(firstDayOfNewMonth.getTime());
                updateHabitDetails(firstDayOfNewMonth);
                updateHabitCalendarYearMonth();
            }
        });
        return view;
    }

    // Give the color by the importance of the habit (0~4)
    final private int getColorByImportance(int importance) {
        switch (importance) {
            case 0: return ResourcesCompat.getColor(getResources(), R.color.colorHabit0, null);
            case 1: return ResourcesCompat.getColor(getResources(), R.color.colorHabit1, null);
            case 2: return ResourcesCompat.getColor(getResources(), R.color.colorHabit2, null);
            case 3: return ResourcesCompat.getColor(getResources(), R.color.colorHabit3, null);
            default: return ResourcesCompat.getColor(getResources(), R.color.colorHabit4, null);
        }
    }

    final void updateHabitCalendarYearMonth() {
        DateFormat sdf = new SimpleDateFormat("YYYY MMMM");
        habitCalendarYearMonth.setText(sdf.format(selectedCalendar.getTime()));
    }

    // Update the habit detail view
    final void updateHabitDetails(Date dateClicked) {
        List<Event> events = habitCalendarView.getEvents(dateClicked);
        StringBuilder habitsToPrint = new StringBuilder();
        for(Event event : events) {
            habitsToPrint.append(event.getData().toString());
        }
        // Here prints the habits in the text view.
        // I am just to bored to write another adapter.
        TransitionManager.beginDelayedTransition(habitDetailContent);
        TransitionManager.beginDelayedTransition(fragHabitRootLinearLayout);
        TransitionManager.beginDelayedTransition(habitDetailCard);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            habitDetailText.setText(Html.fromHtml(habitsToPrint.toString(), Html.FROM_HTML_MODE_LEGACY));
        } else {
            habitDetailText.setText(Html.fromHtml(habitsToPrint.toString()));
        }
    }

    @OnClick(R.id.habit_calendar_year_month)
    void habitCalendarYearMonthOnClickBehavior() {
        DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                selectedCalendar.set(Calendar.YEAR, year);
                selectedCalendar.set(Calendar.MONTH, monthOfYear);
                selectedCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                habitCalendarView.setCurrentDate(selectedCalendar.getTime());
                updateHabitCalendarYearMonth();
                updateHabitDetails(selectedCalendar.getTime());
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
        habitCalendarView.setCurrentDate(now);
        updateHabitCalendarYearMonth();
        updateHabitDetails(selectedCalendar.getTime());
    }

    // Click to expand/collapse the calendar view
    @OnClick(R.id.expand_habit_calendar_view)
    void expandHabitCalendarViewOnClick() {
        TransitionManager.beginDelayedTransition(habitCalendarContent);
        TransitionManager.beginDelayedTransition(fragHabitRootLinearLayout);
        TransitionManager.beginDelayedTransition(habitCalendarCard);
        if(habitCalendarContent.getVisibility() == View.VISIBLE) {
            habitCalendarContent.setVisibility(View.GONE);
        } else {
            habitCalendarContent.setVisibility(View.VISIBLE);
        }
    }

    //Click to expand/collapse the detail view
    @OnClick(R.id.expand_habit_detail_view)
    void expandHabitDetailViewOnClick() {
        TransitionManager.beginDelayedTransition(habitDetailContent);
        TransitionManager.beginDelayedTransition(fragHabitRootLinearLayout);
        TransitionManager.beginDelayedTransition(habitDetailCard);
        if(habitDetailContent.getVisibility() == View.VISIBLE) {
            habitDetailContent.setVisibility(View.GONE);
        } else {
            habitDetailContent.setVisibility(View.VISIBLE);
        }
    }
}
