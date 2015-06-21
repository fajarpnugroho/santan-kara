package euphoriadigital.karaoke.ui;

import euphoriadigital.karaoke.data.model.response.Songs;

public interface MainActionTaker {
    void successGetJson(Songs songs);

    void failedGetJson();
}
