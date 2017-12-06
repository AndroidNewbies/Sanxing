package io.github.celestialphineas.sanxing.UIHomeTabFragments;

/**
 * Created by apple on 2017/11/3.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import io.github.celestialphineas.sanxing.R;
import io.github.celestialphineas.sanxing.sxObject.TimeLeft;


public class TimeLeftRecyclerAdapter
        extends RecyclerView.Adapter<TimeLeftRecyclerAdapter.TimeLeftViewHolder> {
    private String mArguments="l";
    private int count;
    private List<TimeLeft> mlist;
    TimeLeftRecyclerAdapter() {
        // Empty constructor
    }

    TimeLeftRecyclerAdapter(List<TimeLeft> TimeLeft_list) {
        mlist = TimeLeft_list;
        count = 2;
    }

    class TimeLeftViewHolder extends RecyclerView.ViewHolder {
        TextView cardTitle;
        TextView cardSubheading;
        TimeLeftViewHolder(View itemView) {
            super(itemView);
            cardTitle = itemView.findViewById(R.id.card_headline);
            cardSubheading = itemView.findViewById(R.id.card_subheading);
            cardTitle.setText(mArguments);
            cardSubheading.setText("Good night, my friend!");
        }
        TimeLeftViewHolder(View itemView,String title) {
            super(itemView);

            cardTitle = itemView.findViewById(R.id.card_headline);
            cardSubheading = itemView.findViewById(R.id.card_subheading);
            cardTitle.setText(title);
        }
        TimeLeftViewHolder(View itemView,List<TimeLeft> list) {
            super(itemView);

            mlist = list;
            cardTitle = itemView.findViewById(R.id.card_headline);
            cardSubheading = itemView.findViewById(R.id.card_subheading);
        }
    }

    @Override
    public TimeLeftViewHolder onCreateViewHolder(ViewGroup parent, int viewGroup) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_card, parent, false);

        if (!mArguments.equals("l")||mlist==null) return new TimeLeftViewHolder(itemView,mArguments);
        return new TimeLeftViewHolder(itemView,mlist);
    }

    @Override
    public void onBindViewHolder(TimeLeftViewHolder holder, int position) {
        // TODO: Bind the view with data
        holder.cardTitle.setText(mlist.get(position).getContent());
        Integer trans = mlist.get(position).getTime_left();
        holder.cardSubheading.setText("left:"+trans.toString());

    }

    @Override
    public int getItemCount() {
        // TODO: Implement this, and return how many cards concerned
        return mlist==null?0:mlist.size();

    }

}
