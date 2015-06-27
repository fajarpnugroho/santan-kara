package euphoriadigital.karaoke.ui;

import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.view.SurfaceHolder;

import java.io.IOException;

import euphoriadigital.karaoke.ui.widget.CameraPreview;
import euphoriadigital.karaoke.util.CameraUtil;

public class MyMediaRecorder extends MediaRecorder {
    private CameraPreview mPreview;
    private Camera camera;
    private SurfaceHolder holder;

    public MyMediaRecorder(CameraPreview mPreview) {
        this.mPreview = mPreview;
        this.camera = mPreview.getmCamera();
        this.holder = mPreview.getmHolder();
    }

    public boolean prepareMediaRecorder() {
        camera.unlock();
        setCamera(camera);
        setAudioSource(AudioSource.CAMCORDER);
        setVideoSource(VideoSource.CAMERA);

        setProfile(CamcorderProfile.get(Camera.CameraInfo.CAMERA_FACING_FRONT,
                CamcorderProfile.QUALITY_HIGH));

        int rotation = (mPreview.camera_orientation - 180 + 360) % 360;
        setOrientationHint(rotation);

        setPreviewDisplay(holder.getSurface());
        setOutputFile(CameraUtil.getOutputMediaFile(CameraUtil.MEDIA_TYPE_VIDEO, true).toString());

        try {
            prepare();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void releaseMediaRecorder (){
        reset();
        release();
    }

    public void releaseCamera() {
        camera.lock();
        camera.stopPreview();
        camera.release();
        camera = null;
    }
}
