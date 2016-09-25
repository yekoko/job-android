package com.mmitjobs.mmitjobs.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.mmitjobs.mmitjobs.event.InternetConnectionEvent;
import com.mmitjobs.mmitjobs.util.Helper;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by pyaehein on 24/9/16.
 */

public class InternetBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        boolean mConnectionExist = Helper.checkInterConnection(context);
        EventBus.getDefault().post(new InternetConnectionEvent(mConnectionExist));

    }

}
