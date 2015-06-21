package euphoriadigital.karaoke.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import euphoriadigital.karaoke.R;
import euphoriadigital.karaoke.data.model.response.Songs;
import euphoriadigital.karaoke.ui.SongAdapter.Listener;

public class MainActivityFragment extends Fragment implements MainActionTaker, Listener {

    @InjectView(R.id.listview) ListView songListView;

    private Controller controller;
    private SongAdapter songAdapter;

    public MainActivityFragment() {}

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof Controller)) {
            throw new ClassCastException("Acitivty must implement " + Controller.class);
        }
        controller = (Controller) activity;
        controller.registerActionTaker(this);
        songAdapter = new SongAdapter(getActivity());
        songAdapter.setListener(this);
    }

    @Override
    public void onDetach() {
        controller.unregisterActionTaker();
        controller = null;
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        ButterKnife.reset(this);
        super.onDestroyView();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller.loadJSONAssets();
    }

    @Override
    public void successGetJson(Songs songs) {
        songAdapter.setSongs(songs.data);
        songListView.setAdapter(songAdapter);
    }

    @Override
    public void failedGetJson() {
        Toast.makeText(getActivity(), "Failed get data", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemClickListener(long id) {
        controller.navigateToRecordMovie();
    }

    public interface Controller {
        void navigateToRecordMovie();

        void loadJSONAssets();

        void registerActionTaker(MainActionTaker mainActionTaker);

        void unregisterActionTaker();
    }
}
