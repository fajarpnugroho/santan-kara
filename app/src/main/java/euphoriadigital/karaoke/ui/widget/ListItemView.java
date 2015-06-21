package euphoriadigital.karaoke.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import euphoriadigital.karaoke.R;

public class ListItemView extends FrameLayout {

    @InjectView(R.id.cover) ImageView coverImageView;
    @InjectView(R.id.artist) TextView artistTextView;
    @InjectView(R.id.title) TextView titleTextView;
    @InjectView(R.id.duration) TextView durationTextView;

    public ListItemView(Context context) {
        this(context, null);
    }

    public ListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_list_item, this);
        ButterKnife.inject(this);
    }

    public void setCover(String url) {
        Picasso.with(coverImageView.getContext()).load(url).fit().centerCrop().into(coverImageView);
    }

    public void setArtist(String name) {
        artistTextView.setText(name);
    }

    public void setTitle(String title) {
        titleTextView.setText(title);
    }

    public void setDuration(String duration) {
        durationTextView.setText(duration);
    }

}
