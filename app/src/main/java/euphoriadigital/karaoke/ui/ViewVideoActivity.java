package euphoriadigital.karaoke.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.File;

import euphoriadigital.karaoke.R;
import euphoriadigital.karaoke.util.CameraUtil;

import static euphoriadigital.karaoke.ui.ViewVideoFragment.Controller;

public class ViewVideoActivity extends AppCompatActivity implements Controller {

    private RecordMovieActionTaker actionTaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_view_movie);
    }

    @Override
    public void getLastModifiedVideo() {
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
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("Do you want to delete this video?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                File file = CameraUtil.getLastFileModified();
                if (file.exists()) {
                    file.delete();
                    Toast.makeText(dialog.getContext(), "Video deleted", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    throw new IllegalStateException("File not exist");
                }
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.show();
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
    public void unregisterActionTaker() { actionTaker = null; }
}
