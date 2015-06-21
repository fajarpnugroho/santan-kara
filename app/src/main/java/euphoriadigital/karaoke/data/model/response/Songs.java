package euphoriadigital.karaoke.data.model.response;

import java.util.List;

public final class Songs {
    public List<Data> data;

    public class Data {
        public final long id;
        public final Artist artist;
        public final Song song;

        public Data(long id, Artist artist, Song song) {
            this.id = id;
            this.artist = artist;
            this.song = song;
        }
    }

    public class Artist {
        public final String name;
        public final String album;
        public final String cover;

        public Artist(String name, String album, String cover) {
            this.name = name;
            this.album = album;
            this.cover = cover;
        }
    }

    public class Song {
        public final String title;
        public final String duration;
        public final String url;

        public Song(String title, String duration, String url) {
            this.title = title;
            this.duration = duration;
            this.url = url;
        }
    }
}
