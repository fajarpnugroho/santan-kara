package euphoriadigital.karaoke.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;

import euphoriadigital.karaoke.R;
import euphoriadigital.karaoke.data.model.response.Songs;
import euphoriadigital.karaoke.util.AssetsUtils;

public class MainActivity extends AppCompatActivity implements MainActivityFragment.Controller {

    private MainActionTaker mainActionTaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void navigateToRecordMovie(long id, String song) {
        Intent intent = new Intent(this, MyCameraActivity.class);
        intent.putExtra(MyCameraActivity.EXTRA_SONG, song);
        startActivity(intent);
    }

    @Override
    public void loadJSONAssets() {
        Reader reader = null;
        try {
            reader = AssetsUtils.readFile(this, "songs.json");
            Songs songs = new Gson().fromJson(reader, Songs.class);
            if (songs != null) {
                mainActionTaker.successGetJson(songs);
            } else {
                mainActionTaker.failedGetJson();
            }
        } catch (IOException e) {
            throw new RuntimeException("Unexpected error");
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                Log.e("MainActivity", "Reader already been closed");
            }
        }
    }

    @Override
    public void registerActionTaker(MainActionTaker mainActionTaker) {
        this.mainActionTaker = mainActionTaker;
    }

    @Override
    public void unregisterActionTaker() {
        this.mainActionTaker = null;
    }
}
