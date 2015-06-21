package euphoriadigital.karaoke.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;

import euphoriadigital.karaoke.R;
import euphoriadigital.karaoke.util.CameraUtil;

public class RecordMovieActivity extends AppCompatActivity implements RecordMovieFragment.Controller {

    private RecordMovieActionTaker actionTaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_movie);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CameraUtil.CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE
                && resultCode == RESULT_OK) {
            Uri videoUri = data.getData();
            actionTaker.showMovie(videoUri);
        }
    }

    @Override
    public void recordMovie() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            CameraUtil.takeVideo(this);
        }
    }

    @Override
    public void registerActionTaker(RecordMovieActionTaker actionTaker) {
        this.actionTaker = actionTaker;
    }

    @Override
    public void unregisterActionTaker() { this.actionTaker = null; }
}
