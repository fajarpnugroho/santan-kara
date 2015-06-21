package euphoriadigital.karaoke.ui;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import euphoriadigital.karaoke.R;

public class ViewVideoFragment extends Fragment implements RecordMovieActionTaker {

    @InjectView(R.id.videoview) VideoView videoView;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_movie, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @OnClick(R.id.button_delete)
    void onButtonDeleteClick () {

    }

    @OnClick(R.id.button_save)
    void onButtonSaveClick() {

    }

    @Override
    public void showMovie(Uri videoUri) {
        videoView.setVideoURI(videoUri);
        videoView.setMediaController(new MediaController(getActivity()));
    }

    public interface Controller {
        void deleteVideo();

        void saveVideo();

        void registerActionTaker(RecordMovieActionTaker actionTaker);

        void unregisterActionTaker();
    }
}
