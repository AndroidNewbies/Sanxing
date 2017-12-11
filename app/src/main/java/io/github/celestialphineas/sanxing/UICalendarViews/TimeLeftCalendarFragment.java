package io.github.celestialphineas.sanxing.UICalendarViews;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.transition.AutoTransition;
import android.support.transition.Fade;
import android.support.transition.TransitionManager;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.CardView;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnItemSelected;
import io.github.celestialphineas.sanxing.R;

public class TimeLeftCalendarFragment extends Fragment {

    @BindView(R.id.choose_a_time_left_button)           AppCompatButton chooseButton;
    @BindView(R.id.time_left_choice_card)               CardView choiceCard;
    @BindView(R.id.frag_time_left_root_linear_layout)   ViewGroup fragTimeLeftRootLinearLayout;
    @BindView(R.id.time_left_choice_list)               ListViewCompat timeLeftListView;
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
        Calendar then = Calendar.getInstance();
        past.setTimeInMillis(now.getTimeInMillis() - 1000000);
        then.setTimeInMillis(now.getTimeInMillis() + 10000000);
        timeLeftEventList.add(new TimeLeftEvent("Hello", "Hello world!!!", past, then, 0));
        timeLeftEventList.add(new TimeLeftEvent("World", "Fucking Java. Fuck Android!", past, then, 3));
        timeLeftEventList.add(new TimeLeftEvent("Gosh", "Lalala, hahaha", past, then, 4));
        // End of TODO

        List<Map<String, Object>> adapterData = new ArrayList<>();
        for(TimeLeftEvent event : timeLeftEventList) {
            adapterData.add(event.getHashSet());
        }
        timeLeftListView.setAdapter(new SimpleAdapter(view.getContext(), adapterData,
                R.layout.list_view_template,
                new String[] {"title", "description"},
                new int[] {R.id.list_view_item_title, R.id.list_view_item_description}));

        return view;
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
    }
}
