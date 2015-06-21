package euphoriadigital.karaoke.util;

import android.content.Context;

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
}
