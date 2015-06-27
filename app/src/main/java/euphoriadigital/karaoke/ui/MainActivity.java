package euphoriadigital.karaoke.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void navigateToRecordMovie() {
        Intent intent = new Intent(this, CameraActivity.class);
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
