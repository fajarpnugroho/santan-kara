package euphoriadigital.karaoke.ui;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Chronometer;
import android.widget.Toast;

import java.io.IOException;

import euphoriadigital.karaoke.R;
import euphoriadigital.karaoke.util.AssetsUtils;
import euphoriadigital.karaoke.util.CameraUtil;

import static euphoriadigital.karaoke.ui.MyCameraActivityFragment.Controller;

public class MyCameraActivity extends AppCompatActivity implements Controller {

    public static final String EXTRA_SONG = "extra_song";

    private Bundle bundle;
    private MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_camera);

        if (!CameraUtil.checkCameraHardware(this)) {
            Toast.makeText(this, "You dont have camera", Toast.LENGTH_SHORT).show();
            finish();
        }

        bundle = getIntent().getExtras();

        mediaPlayer = new MediaPlayer();
    }

    @Override
    public void startRecordVideo(MyMediaRecorder myMediaRecorder) {
        if (myMediaRecorder.prepareMediaRecorder()) {
            myMediaRecorder.start();
        } else {
            myMediaRecorder.releaseMediaRecorder();
            myMediaRecorder.releaseCamera();
        }
    }

    @Override
    public void stopRecordVideo(MyMediaRecorder myMediaRecorder) {
        if (myMediaRecorder != null) {
            myMediaRecorder.stop();
            myMediaRecorder.releaseMediaRecorder();
            myMediaRecorder.releaseCamera();
        }
    }

    @Override
    public void playAudio() {
        if (bundle == null) { return; }

        AssetFileDescriptor afd = AssetsUtils.openFileDescriptor(this, bundle.getString(EXTRA_SONG));

        try {
            mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(),
                    afd.getLength());
            afd.close();
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            Log.e("TAG", "Could not open file " + afd.toString() + " for playback.", e);
        }
    }

    @Override
    public void stopAudio() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    @Override
    public void startChromometer(Chronometer chronometer) {
        chronometer.start();
    }

    @Override
    public void stopChronometer(Chronometer chronometer) {
        chronometer.stop();
    }

    @Override
    public void navigateToReviewVideo() {
        Intent intent = new Intent(this, ViewVideoActivity.class);
        startActivity(intent);
        finish();
    }
}
