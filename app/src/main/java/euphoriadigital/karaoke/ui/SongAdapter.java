package euphoriadigital.karaoke.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import euphoriadigital.karaoke.R;
import euphoriadigital.karaoke.data.model.response.Songs;

public class SongAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private List<Songs.Data> songs;
    private Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public SongAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    public void setSongs(List<Songs.Data> songs) {
        this.songs = songs;
    }

    @Override
    public int getCount() {
        return songs.size();
    }

    @Override
    public Songs.Data getItem(int position) {
        return songs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return songs.get(position).id;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.view_list_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Picasso.with(convertView.getContext())
                .load(getItem(position).artist.cover)
                .fit()
                .centerCrop()
                .into(viewHolder.coverImageView);

        viewHolder.artistTextView.setText(getItem(position).artist.name);
        viewHolder.titleTextView.setText(getItem(position).song.title);
        viewHolder.durationTextView.setText(getItem(position).song.duration);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClickListener(getItemId(position));
            }
        });

        return convertView;
    }

    public class ViewHolder {
        @InjectView(R.id.cover) ImageView coverImageView;
        @InjectView(R.id.artist) TextView artistTextView;
        @InjectView(R.id.title) TextView titleTextView;
        @InjectView(R.id.duration) TextView durationTextView;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    public interface Listener {
        void onItemClickListener(long id);
    }
}
