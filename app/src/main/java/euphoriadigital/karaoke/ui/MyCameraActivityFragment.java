package euphoriadigital.karaoke.ui;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import euphoriadigital.karaoke.R;
import euphoriadigital.karaoke.service.MusicIntentReceiver;
import euphoriadigital.karaoke.service.MusicIntentReceiver.Listener;
import euphoriadigital.karaoke.ui.widget.CameraPreview;
import euphoriadigital.karaoke.util.CameraUtil;

public class MyCameraActivityFragment extends Fragment implements Listener {

    @InjectView(R.id.camera_preview) FrameLayout preview;
    @InjectView(R.id.button_action) CompoundButton buttonAction;
    @InjectView(R.id.chronometer) Chronometer chronometer;

    private Camera mCamera;
    private CameraPreview mPreview;
    private MyMediaRecorder myMediaRecorder;

    private Controller controller;

    private MusicIntentReceiver musicIntentReceiver;
    private boolean headsetPlugged = false;
    private boolean recording = false;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (!( activity instanceof Controller)) {
            throw new ClassCastException("Activity must implement " + Controller.class);
        }

        controller = (Controller) activity;
        musicIntentReceiver = new MusicIntentReceiver(this);
    }

    @Override
    public void onDetach() {
        controller = null;
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        getActivity().registerReceiver(musicIntentReceiver, filter);
    }

    @Override
    public void onPause() {
        getActivity().unregisterReceiver(musicIntentReceiver);
        super.onPause();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_camera, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Create an instance of Camera
        mCamera = CameraUtil.getCameraInstance();

        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(view.getContext(), mCamera, getActivity().getWindowManager());

        myMediaRecorder = new MyMediaRecorder(mPreview);

        preview.addView(mPreview);

    }

    @OnClick(R.id.button_action)
    void onButtonActionClick(CompoundButton view) {
        if (headsetPlugged) {
            if (view.isChecked()) {
                recording = true;
                controller.startRecordVideo(myMediaRecorder);
                controller.startChromometer(chronometer);
                controller.playAudio();
            } else {
                recording = false;
                controller.stopRecordVideo(myMediaRecorder);
                controller.stopChronometer(chronometer);
                controller.stopAudio();
                controller.navigateToReviewVideo();
            }
        } else {
            view.setChecked(false);
            Toast.makeText(view.getContext(), "Please plug in your earphone or headset",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        ButterKnife.reset(this);
        super.onDestroyView();
    }

    @Override
    public void onHeadSetPlugged() {
        headsetPlugged = true;
        Toast.makeText(getActivity(), "Ready to rock on", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onHeadSetUnPlugged() {
        headsetPlugged = false;
        buttonAction.setChecked(false);
        Toast.makeText(getActivity(), "Ups, your headset is unplugged", Toast.LENGTH_SHORT).show();
    }

    public interface Controller {
        void startRecordVideo(MyMediaRecorder mPreview);

        void stopRecordVideo(MyMediaRecorder mPreview);

        void playAudio();

        void stopAudio();

        void startChromometer(Chronometer chronometer);

        void stopChronometer(Chronometer chronometer);

        void navigateToReviewVideo();
    }
}
