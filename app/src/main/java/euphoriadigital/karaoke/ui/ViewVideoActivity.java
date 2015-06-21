package euphoriadigital.karaoke.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.io.File;

import euphoriadigital.karaoke.R;
import euphoriadigital.karaoke.util.CameraUtil;

import static euphoriadigital.karaoke.ui.ViewVideoFragment.Controller;

public class ViewVideoActivity extends AppCompatActivity implements Controller {

    private RecordMovieActionTaker actionTaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_movie);
    }

    @Override
    public void showVideo() {
        File file = CameraUtil.getLastFileModified();
        if (file.exists()) {
            Uri uri = Uri.fromFile(file);
            actionTaker.showMovie(uri);
        } else {
            throw new IllegalStateException("File not exist");
        }
    }

    @Override
    public void deleteVideo() {
        File file = CameraUtil.getLastFileModified();
        if (file.exists()) {
            file.delete();
            Toast.makeText(this, "Video deleted", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            throw new IllegalStateException("File not exist");
        }
    }

    @Override
    public void saveVideo() {
        Toast.makeText(this, "Video saved", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void registerActionTaker(RecordMovieActionTaker actionTaker) {
        this.actionTaker = actionTaker;
    }

    @Override
    public void unregisterActionTaker() {
        actionTaker = null;
    }
}
