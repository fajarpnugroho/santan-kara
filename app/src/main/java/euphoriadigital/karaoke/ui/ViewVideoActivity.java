package euphoriadigital.karaoke.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import euphoriadigital.karaoke.R;

import static euphoriadigital.karaoke.ui.ViewVideoFragment.*;

public class ViewVideoActivity extends AppCompatActivity implements Controller {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_movie);
    }

    @Override
    public void deleteVideo() {

    }

    @Override
    public void saveVideo() {

    }
}
