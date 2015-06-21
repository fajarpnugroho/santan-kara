package euphoriadigital.karaoke.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.CompoundButton;

import com.commonsware.cwac.camera.CameraFragment;
import com.commonsware.cwac.camera.CameraView;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import euphoriadigital.karaoke.R;

public class MyCameraFragment extends CameraFragment {
    @InjectView(R.id.camera) CameraView cameraView;
    @InjectView(R.id.action) CompoundButton action;
    @InjectView(R.id.chronometer) Chronometer chronometer;

    private Controller controller;

    public MyCameraFragment() {}

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof Controller)) {
            throw new ClassCastException("Activity must implement " + Controller.class);
        }
        controller = (Controller) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View content=inflater.inflate(R.layout.fragment_camera, container, false);
        ButterKnife.inject(this, content);
        setCameraView(cameraView);
        return(content);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        action.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked) {
                    try {
                        record();
                        chronometer.start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        stopRecording();
                        chronometer.stop();
                        controller.navigateToViewVideoAcitivity();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public interface Controller {
        void navigateToViewVideoAcitivity();
    }
}
