package hexfan.lyrics.ui.lyrics;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import hexfan.lyrics.R;
import hexfan.lyrics.model.pojo.TrackInfo;
import hexfan.lyrics.ui.base.BaseActivity;
import hexfan.lyrics.ui.base.BaseFragment;
import hexfan.lyrics.ui.main.MainActivity;

/**
 * Created by Pawel on 30.07.2017.
 */

public class LyricsFragment extends BaseFragment {

    private static final String KEY_TRACK_INFO = "track_info";

    @BindView(R.id.ivCover)
    ImageView ivCover;
    @BindView(R.id.tvTrackTitle)
    TextView tvTrackTitle;
    @BindView(R.id.tvArtist)
    TextView tvArtist;
    @BindView(R.id.tvAlbum)
    TextView tvAlbum;
    @BindView(R.id.tvLyrics)
    TextView tvLirycs;

    public static LyricsFragment newInstance(String trackInfo) {

        Bundle args = new Bundle();
        args.putString(KEY_TRACK_INFO, trackInfo);
        LyricsFragment fragment = new LyricsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lyrics_fragment, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init(){
        TrackInfo trackInfo = BaseActivity.get(this).getGson().fromJson(getArguments().getString(KEY_TRACK_INFO), TrackInfo.class);
        changeTrack(trackInfo);

    }

    public void changeTrack(TrackInfo trackInfo){
        Log.e("LyricsFragment", "changeTrack: changed");
        tvTrackTitle.setText(trackInfo.getName());
        tvArtist.setText(trackInfo.getArtist());

        MainActivity.get(this).presenter.getTrackInfo(trackInfo.getArtist(), trackInfo.getName());
    }

    public void showFull(TrackInfo trackInfo){
        if(trackInfo.getAlbum() != null)
            tvAlbum.setText(trackInfo.getAlbum());

        if(trackInfo.getLyrics() != null)
            tvLirycs.setText(trackInfo.getLyrics());

        if(trackInfo.getAlbumCover() != null) {
            MainActivity.get(this)
                    .getPicasso()
                    .load(trackInfo.getAlbumCover())
                    .fit()
                    .into(ivCover);
        }
    }
}
