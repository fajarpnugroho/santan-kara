package euphoriadigital.karaoke.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.commonsware.cwac.camera.CameraHost;
import com.commonsware.cwac.camera.CameraHostProvider;
import com.commonsware.cwac.camera.SimpleCameraHost;

import euphoriadigital.karaoke.util.CameraUtil;

import static com.commonsware.cwac.camera.CameraHost.*;
import static euphoriadigital.karaoke.ui.MyCameraFragment.Controller;

public class CameraActivity extends AppCompatActivity implements CameraHostProvider, Controller {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(android.R.id.content, new MyCameraFragment()).commit();
        }
    }

    @Override
    public CameraHost getCameraHost() {
        SimpleCameraHost.Builder simpleCameraHost =
                new SimpleCameraHost.Builder(this)
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
}
