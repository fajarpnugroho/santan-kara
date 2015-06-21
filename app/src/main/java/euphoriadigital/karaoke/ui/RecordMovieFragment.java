package euphoriadigital.karaoke.ui;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.VideoView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import euphoriadigital.karaoke.R;

public class RecordMovieFragment extends Fragment implements RecordMovieActionTaker {

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
    public void onDestroyView() {
        ButterKnife.reset(this);
        super.onDestroyView();
    }

    @OnClick(R.id.button_start)
    void onButtonStartClick() {
        controller.recordMovie();
    }

    @Override
    public void showMovie(Uri videoUri) {
        Toast.makeText(getActivity(), "Record success", Toast.LENGTH_SHORT).show();
        videoView.setVideoURI(videoUri);
    }

    public interface Controller {
        void recordMovie();

        void registerActionTaker(RecordMovieActionTaker actionTaker);

        void unregisterActionTaker();
    }
}
