package euphoriadigital.karaoke.ui;

import android.app.Activity;
import android.app.Fragment;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import euphoriadigital.karaoke.R;

import static android.media.MediaPlayer.OnCompletionListener;
import static android.media.MediaPlayer.OnPreparedListener;

public class RecordMovieFragment extends Fragment implements RecordMovieActionTaker,
        OnPreparedListener, OnCompletionListener {

    @InjectView(R.id.videoview) VideoView videoView;

    private Controller controller;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (!(activity instanceof Controller)) {
            throw new ClassCastException("Activity must implement "  + Controller.class);
        }

        controller = (Controller) activity;
        controller.registerActionTaker(this);
    }

    @Override
    public void onDetach() {
        controller.unregisterActionTaker();
        controller = null;
        super.onDetach();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record_movie, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller.recordMovie();
    }

    @Override
    public void onDestroyView() {
        ButterKnife.reset(this);
        super.onDestroyView();
    }

    @Override
    public void showMovie(Uri videoUri) {
        Toast.makeText(getActivity(), "Record success", Toast.LENGTH_SHORT).show();
        videoView.setVideoURI(videoUri);
        videoView.setOnPreparedListener(this);
        videoView.setMediaController(new MediaController(getActivity()));
        videoView.requestFocus();
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        this.videoView.seekTo(0);
        Toast.makeText(getActivity(), "Playback complete", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        this.videoView.setOnCompletionListener(this);
        this.videoView.start();
        Toast.makeText(getActivity(), "Video playback stard", Toast.LENGTH_SHORT).show();
    }

    public interface Controller {
        void recordMovie();

        void registerActionTaker(RecordMovieActionTaker actionTaker);

        void unregisterActionTaker();
    }
}
