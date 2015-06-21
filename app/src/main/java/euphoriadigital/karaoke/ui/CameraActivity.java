package euphoriadigital.karaoke.ui;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.commonsware.cwac.camera.CameraHost;
import com.commonsware.cwac.camera.CameraHostProvider;
import com.commonsware.cwac.camera.SimpleCameraHost;

import java.io.IOException;

import euphoriadigital.karaoke.R;
import euphoriadigital.karaoke.util.AssetsUtils;
import euphoriadigital.karaoke.util.CameraUtil;

import static com.commonsware.cwac.camera.CameraHost.RecordingHint;
import static euphoriadigital.karaoke.ui.MyCameraFragment.Controller;

public class CameraActivity extends AppCompatActivity implements CameraHostProvider, Controller {

    public static final String EXTRA_SONG = "extra_song";
    public static final String EXTRA_ID = "extra_id";
    private CameraActionTaker actionTaker;
    private Bundle bundle;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_camera);

        bundle = getIntent().getExtras();

        mediaPlayer = new MediaPlayer();
    }

    @Override
    public CameraHost getCameraHost() {
        SimpleCameraHost.Builder simpleCameraHost = new SimpleCameraHost.Builder(this)
                        .videoDirectory(CameraUtil
                                .getOutputMediaFile(CameraUtil.MEDIA_TYPE_VIDEO, false))
                        .recordingHint(RecordingHint.VIDEO_ONLY)
                        .mirrorFFC(false)
                        .useFrontFacingCamera(true);
        return simpleCameraHost.build();
    }

    @Override
    public void navigateToViewVideoAcitivity() {
        Intent intent = new Intent(this, ViewVideoActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void record() {
        actionTaker.startRecordVideo();

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
    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        actionTaker.stopRecordVideo();
    }

    @Override
    public void registerActionTaker(CameraActionTaker actionTaker) {
        this.actionTaker = actionTaker;
    }

    @Override
    public void unregisterActionTaker() {
        this.actionTaker = null;
    }
}
