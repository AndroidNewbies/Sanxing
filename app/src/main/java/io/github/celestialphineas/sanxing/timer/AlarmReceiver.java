package io.github.celestialphineas.sanxing.timer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

/**
 * Created by lin on 2017/11/4.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //设置通知内容并在onReceive()这个函数执行时开启
        //唤醒service
        Log.d("AlarmReceiver","I start service at " +intent.getStringExtra("date"));
        Intent i = new Intent(context, MyService.class);
        i.putExtra("source","receiver");
        context.startService(i);
    }
}
