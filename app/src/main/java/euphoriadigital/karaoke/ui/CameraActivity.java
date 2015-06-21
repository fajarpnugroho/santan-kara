package euphoriadigital.karaoke.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.commonsware.cwac.camera.CameraHost;
import com.commonsware.cwac.camera.CameraHostProvider;
import com.commonsware.cwac.camera.SimpleCameraHost;

import euphoriadigital.karaoke.util.CameraUtil;

public class CameraActivity extends AppCompatActivity implements CameraHostProvider {

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
                        .useFullBleedPreview(true)
                        .useFrontFacingCamera(true);
        return simpleCameraHost.build();
    }

}
