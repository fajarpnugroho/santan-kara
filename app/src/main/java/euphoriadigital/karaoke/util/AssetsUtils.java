package euphoriadigital.karaoke.util;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.util.Log;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public final class AssetsUtils {

    private AssetsUtils() {}

    public static Reader readFile(Context context, String fileName) throws IOException {
        InputStream inputStream = context.getAssets().open(fileName);
        Reader reader = new InputStreamReader(inputStream, "utf-8");
        return reader;
    }

    public static AssetFileDescriptor openFileDescriptor(Context context, String filename) {
        try {
            AssetFileDescriptor afd = context.getAssets().openFd("songs/" + filename);
            return  afd;
        } catch (IOException e) {
            Log.e("TAG", "Failed to open file " + filename, e);
        }
        return null;
    }
}
