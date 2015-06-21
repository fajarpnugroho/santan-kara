package euphoriadigital.karaoke.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.commonsware.cwac.camera.CameraHost;
import com.commonsware.cwac.camera.CameraHostProvider;
import com.commonsware.cwac.camera.SimpleCameraHost;

import euphoriadigital.karaoke.R;
import euphoriadigital.karaoke.util.CameraUtil;

import static com.commonsware.cwac.camera.CameraHost.RecordingHint;
import static euphoriadigital.karaoke.ui.MyCameraFragment.Controller;

public class CameraActivity extends AppCompatActivity implements CameraHostProvider, Controller {

    private CameraActionTaker actionTaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_camera);
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
    }

    @Override
    public void stop() {
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
