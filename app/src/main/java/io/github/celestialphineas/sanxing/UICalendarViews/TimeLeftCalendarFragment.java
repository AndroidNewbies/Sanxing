package io.github.celestialphineas.sanxing.UICalendarViews;

import android.os.Build;
import android.os.Bundle;
import android.support.transition.TransitionManager;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.ListViewCompat;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import io.github.celestialphineas.sanxing.R;

public class TimeLeftCalendarFragment extends Fragment {
    @BindView(R.id.choose_a_time_left_button)           AppCompatButton chooseButton;
    @BindView(R.id.time_left_choice_card)               CardView choiceCard;
    @BindView(R.id.time_left_calendar_card)             CardView calendarCard;
    @BindView(R.id.time_left_detail_card)               CardView detailCard;
    @BindView(R.id.frag_time_left_root_linear_layout)   ViewGroup fragTimeLeftRootLinearLayout;
    @BindView(R.id.time_left_calendar_content)          ViewGroup timeLeftCalendarContent;
    @BindView(R.id.time_left_bar_content)               ViewGroup timeLeftBarContent;
    @BindView(R.id.time_left_grid_content)              ViewGroup timeLeftGridContent;
    @BindView(R.id.time_left_detail_content)            ViewGroup timeLeftDetailContent;
    @BindView(R.id.time_left_choice_list)               ListViewCompat timeLeftListView;
    @BindView(R.id.time_left_percentage_text)           AppCompatTextView percentageText;
    @BindView(R.id.time_left_title)                     AppCompatTextView timeLeftTitle;
    @BindView(R.id.time_left_subheading)                AppCompatTextView timeLeftSubheading;
    @BindView(R.id.time_left_description)               AppCompatTextView timeLeftDescription;
    @BindView(R.id.time_left_detail)                    AppCompatTextView timeLeftDetail;
    @BindView(R.id.time_left_bar_canvas)                BarCanvasView barCanvas;
    @BindView(R.id.time_left_grid_canvas)               GridCanvasView gridCanvas;
    @BindView(R.id.time_left_scale_seek_bar)            AppCompatSeekBar seekBar;
    @BindString(R.string.right_arrow)                   String rightArrow;
    @BindString(R.string.unit_year)                     String unitYearString;
    @BindString(R.string.unit_month)                    String unitMonthString;
    @BindString(R.string.unit_week)                     String unitWeekString;
    @BindString(R.string.unit_day)                      String unitDayString;
    @BindString(R.string.unit_hour)                     String unitHourString;
    int timeScale = 2;
    final List<TimeLeftEvent> timeLeftEventList = new ArrayList<>();
    final int[] currentIndex = new int[1];

    public TimeLeftCalendarFragment() { }

    public static TimeLeftCalendarFragment newInstance() {
        TimeLeftCalendarFragment fragment = new TimeLeftCalendarFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    class TimeLeftEvent {
        String title, description;
        Calendar startTime, endTime;
        int importance;
        TimeLeftEvent(String title, String description, Calendar startTime, Calendar endTime, int importance) {
            this.title = title; this.description = description; this.startTime = startTime;
            this.endTime = endTime; this.importance = importance;
        }
        public Map<String, Object> getHashSet() {
            Map<String, Object> map = new HashMap<>();
            map.put("title", title);            map.put("description", description);
            map.put("startTime", startTime);    map.put("endTime", endTime);
            return map;
        }
        // This public method returns the importance color value of the object
        public int getImportanceColor() {
            switch (importance) {
                case 0: return ResourcesCompat.getColor(getResources(), R.color.colorTimeLeft0, null);
                case 1: return ResourcesCompat.getColor(getResources(), R.color.colorTimeLeft1, null);
                case 2: return ResourcesCompat.getColor(getResources(), R.color.colorTimeLeft2, null);
                case 3: return ResourcesCompat.getColor(getResources(), R.color.colorTimeLeft3, null);
                default: return ResourcesCompat.getColor(getResources(), R.color.colorTimeLeft4, null);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_time_left_calendar, container, false);
        ButterKnife.bind(this, view);

        // TODO: Generate a list of timeLeftEvents
        // Constructor of the TimeLeftEvent:
        // TimeLeftEvent(String title, String description, Calendar startTime, Calendar endTime, int importance)
        Calendar past = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        Calendar then1 = Calendar.getInstance();
        Calendar then2 = Calendar.getInstance();
        Calendar then3 = Calendar.getInstance();
        past.setTimeInMillis(now.getTimeInMillis() - 1000000L);
        then1.setTimeInMillis(now.getTimeInMillis() + 10000000L);
        then2.setTimeInMillis(now.getTimeInMillis() + 5000000000L);
        then3.setTimeInMillis(now.getTimeInMillis() + 120000000L);
        timeLeftEventList.add(new TimeLeftEvent("Hello", "Hello world!!!", past, then1, 0));
        timeLeftEventList.add(new TimeLeftEvent("World", "Fucking Java. Fuck Android!", past, then2, 3));
        timeLeftEventList.add(new TimeLeftEvent("University time", "Lalala, hahaha", past, then3, 4));
        // End of TODO

        List<Map<String, Object>> adapterData = new ArrayList<>();
        for(TimeLeftEvent event : timeLeftEventList) {
            adapterData.add(event.getHashSet());
        }
        timeLeftListView.setAdapter(new SimpleAdapter(view.getContext(), adapterData,
                R.layout.list_view_template,
                new String[] {"title", "description"},
                new int[] {R.id.list_view_item_title, R.id.list_view_item_description}));
        // Bind SeekBar UI
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                timeScale = seekBar.getProgress();
                updateViews();
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override public void onStopTrackingTouch(SeekBar seekBar) { }
        });
        updateViews();
        return view;
    }

    // This method update the canvases
    private void updateViews() {
        if(currentIndex[0] < timeLeftEventList.size()) {
            TimeLeftEvent timeLeftEvent = timeLeftEventList.get(currentIndex[0]);
            // Title
            timeLeftTitle.setText(timeLeftEvent.title);
            // Calculate the percentage
            long startMillis = timeLeftEvent.startTime.getTimeInMillis();
            long endMillis = timeLeftEvent.endTime.getTimeInMillis();
            long nowMillis = Calendar.getInstance().getTimeInMillis();
            double percentage = ((double)(nowMillis - startMillis))/(endMillis - startMillis);
            // Calculate the number of squares
            long span = endMillis - startMillis;
            long hours = span/1000/60/60;
            long days = hours/24;
            long weeks = days/7;
            long months = days/30;
            long years = days/365;
            int squares = hours > 100 ? days > 100 ? weeks > 100 ? months > 100 ?
                    (int)years : (int)months : (int)weeks : (int)days : (int)hours;
            // Set text
            DateFormat sdf = android.text.format.DateFormat.getDateFormat(getContext());
            String timespanString = sdf.format(startMillis) + rightArrow + sdf.format(endMillis);
            timeLeftSubheading.setText(timespanString);
            timeLeftDescription.setText(timeLeftEvent.description);
            // Bar canvas
            percentageText.setText("" + (int)(percentage * 100 + 0.5) + "%");
            percentageText.setTextColor(timeLeftEvent.getImportanceColor());
            barCanvas.setPercentage(percentage);
            barCanvas.setFgColor(timeLeftEvent.getImportanceColor());
            // Grid canvas
            gridCanvas.setPercentage(percentage);
            gridCanvas.setFgColor(timeLeftEvent.getImportanceColor());
            gridCanvas.setnSquares(squares);
            // Write the details
            StringBuilder detailString = new StringBuilder();
            String colorString = "#" + Integer.toHexString(timeLeftEvent.getImportanceColor() & 0xFFFFFF);
            String numString, unitString;
            switch (timeScale) {
                case 0: numString = Long.toString(hours); unitString = unitHourString; break;
                case 1: numString = Long.toString(days); unitString = unitDayString; break;
                case 2: numString = Long.toString(weeks); unitString = unitWeekString; break;
                case 3: numString = Long.toString(months); unitString = unitMonthString; break;
                case 4: default: numString = Long.toString(years); unitString = unitYearString; break;
            }
            detailString.append(numString);
            detailString.append("<b><font color=\"" + colorString + "\">");
                detailString.append(unitString);
            detailString.append("</font></b>");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                timeLeftDetail.setText(Html.fromHtml(detailString.toString(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                timeLeftDetail.setText(Html.fromHtml(detailString.toString()));
            }
        }
    }

    @OnClick(R.id.choose_a_time_left_button)
    void chooseButtonOnClickBehavior() {
        TransitionManager.beginDelayedTransition(fragTimeLeftRootLinearLayout);
        choiceCard.setVisibility(View.VISIBLE);
        chooseButton.setVisibility(View.GONE);
    }

    @OnItemClick(R.id.time_left_choice_list)
    void listOnItemClickBehavior(AdapterView<?> adapter, View view, int pos, long id) {
        currentIndex[0] = pos;
        TransitionManager.beginDelayedTransition(fragTimeLeftRootLinearLayout);
        choiceCard.setVisibility(View.GONE);
        chooseButton.setVisibility(View.VISIBLE);
        updateViews();
    }

    // Click to expand/collapse the detail view
    @OnClick(R.id.view_bar_canvas_button)
    void showBarOnClick() {
        TransitionManager.beginDelayedTransition(timeLeftGridContent);
        TransitionManager.beginDelayedTransition(timeLeftBarContent);
        TransitionManager.beginDelayedTransition(fragTimeLeftRootLinearLayout);
        if(timeLeftBarContent.getVisibility() != View.VISIBLE) {
            timeLeftGridContent.setVisibility(View.GONE);
            timeLeftBarContent.setVisibility(View.VISIBLE);
        }
    }

    // Click to expand/collapse the detail view
    @OnClick(R.id.view_grid_canvas_button)
    void showGridOnClick() {
        TransitionManager.beginDelayedTransition(timeLeftBarContent);
        TransitionManager.beginDelayedTransition(timeLeftGridContent);
        TransitionManager.beginDelayedTransition(fragTimeLeftRootLinearLayout);
        if(timeLeftGridContent.getVisibility() != View.VISIBLE) {
            timeLeftBarContent.setVisibility(View.GONE);
            timeLeftGridContent.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.expand_time_left_calendar_view)
    void expandCardOnClick() {
        TransitionManager.beginDelayedTransition(timeLeftCalendarContent);
        TransitionManager.beginDelayedTransition(calendarCard);
        TransitionManager.beginDelayedTransition(fragTimeLeftRootLinearLayout);
        if(timeLeftCalendarContent.getVisibility() == View.VISIBLE) {
            timeLeftCalendarContent.setVisibility(View.GONE);
        } else timeLeftCalendarContent.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.expand_time_left_detail)
    void expandDetailOnClick() {
        TransitionManager.beginDelayedTransition(timeLeftDetailContent);
        TransitionManager.beginDelayedTransition(detailCard);
        TransitionManager.beginDelayedTransition(fragTimeLeftRootLinearLayout);
        if(timeLeftDetailContent.getVisibility() == View.VISIBLE) {
            timeLeftDetailContent.setVisibility(View.GONE);
        } else timeLeftDetailContent.setVisibility(View.VISIBLE);
    }
}
