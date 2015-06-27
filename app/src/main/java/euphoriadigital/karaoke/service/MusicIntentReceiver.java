package euphoriadigital.karaoke.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MusicIntentReceiver extends BroadcastReceiver {

    private static final String TAG = "MusicIntentReceiver";
    private Listener listener;

    public MusicIntentReceiver(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
            int state = intent.getIntExtra("state", -1);
            switch (state) {
                case 0:
                    listener.onHeadSetUnPlugged();
                    break;
                case 1:
                    listener.onHeadSetPlugged();
                    break;
                default:
                    Log.d(TAG, "I have no idea what the headset state is");
            }

        }
    }

    public interface Listener {
        void onHeadSetPlugged();

        void onHeadSetUnPlugged();
    }
}
