package hexfan.lyrics.ui.lyrics;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import hexfan.lyrics.R;
import hexfan.lyrics.di.Injector;
import hexfan.lyrics.model.pojo.TrackInfo;
import hexfan.lyrics.ui.base.BaseFragment;
import hexfan.lyrics.ui.main.MainActivity;

/**
 * Created by Pawel on 30.07.2017.
 */

public class LyricsFragment extends BaseFragment implements LyricsView {

    private static final String KEY_ARTIST = "artist";
    private static final String KEY_TRACK = "track";
    private static final String EXTRA_TRACK_INFO = "track_info";

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


    @Inject
    Picasso picasso;
//    @Inject
    LyricsViewModel viewModel;

    public static LyricsFragment newInstance(TrackInfo trackInfo) {

        Bundle args = new Bundle();
        args.putParcelable(EXTRA_TRACK_INFO, trackInfo);
        LyricsFragment fragment = new LyricsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Injector.inject(this);
        View view = inflater.inflate(R.layout.lyrics_fragment, container, false);
        ButterKnife.bind(this, view);
        init();
        showLyrics(getArguments().getParcelable(EXTRA_TRACK_INFO));
        return view;
    }

    @Override
    public void bind() {

    }


    private void init(){


    }


    public void showLyrics(TrackInfo trackInfo){

        if(trackInfo.getAlbum() != null)
            tvAlbum.setText(trackInfo.getAlbum());

        if(trackInfo.getLyrics() != null)
            tvLirycs.setText(trackInfo.getLyrics());

        if(trackInfo.getAlbumCover() != null) {
            picasso.load(trackInfo.getAlbumCover())
                    .fit()
                    .into(ivCover);
        }
    }



}
