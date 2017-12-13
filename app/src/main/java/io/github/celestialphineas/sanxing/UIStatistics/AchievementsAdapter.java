package io.github.celestialphineas.sanxing.UIStatistics;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import java.util.List;

import io.github.celestialphineas.sanxing.R;

/**
 * Created by celestialphineas on 17-12-13.
 */

public class AchievementsAdapter extends ArrayAdapter<StatisticsActivity.Achievement> {

    public AchievementsAdapter(Context context, List<StatisticsActivity.Achievement> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        StatisticsActivity.Achievement achievement = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                            .inflate(R.layout.achievement_list_template, parent, false);
        }

        AppCompatTextView title = (AppCompatTextView)convertView.findViewById(R.id.achievement_list_item_title);
        AppCompatTextView description = (AppCompatTextView)convertView.findViewById(R.id.achievement_list_item_description);
        AppCompatImageView layer1 = (AppCompatImageView)convertView.findViewById(R.id.medal_layer1);
        AppCompatImageView layer2 = (AppCompatImageView)convertView.findViewById(R.id.medal_layer2);
        AppCompatImageView layer3 = (AppCompatImageView)convertView.findViewById(R.id.medal_layer3);

        if(achievement != null) {
            if(achievement.title != null) title.setText(achievement.title);
            if(achievement.description != null) description.setText(achievement.description);
            layer1.setColorFilter(achievement.getBandColor(), PorterDuff.Mode.SRC_IN);
            layer2.setColorFilter(achievement.getMedalBrightColor(), PorterDuff.Mode.SRC_IN);
            layer3.setColorFilter(achievement.getMedalDarkColor(), PorterDuff.Mode.SRC_IN);
        }

        return convertView;
    }
}
