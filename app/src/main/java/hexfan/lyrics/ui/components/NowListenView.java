package hexfan.lyrics.ui.components;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hexfan.lyrics.R;
import hexfan.lyrics.model.pojo.TrackInfo;
import hexfan.lyrics.ui.main.MainView;

/**
 * Created by Pawel on 29.07.2017.
 */

public class NowListenView extends LinearLayout {

    @BindView(R.id.tvNowListenTitle)
    TextView tvTitle;
    @BindView(R.id.tvNowListenArtist)
    TextView tvArtist;
    @BindView(R.id.ivCover)
    ImageView ivCover;
    @BindView(R.id.ivShow)
    ImageView ivShow;
    @BindView(R.id.tvLyricsReady)
    TextView tvLyricsReady;

    private MainView contract;
    private Picasso picasso;
    private TrackInfo trackInfo;

    public NowListenView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.now_listen_view, this);
        ButterKnife.bind(this);
    }

    public void setup(MainView contract, Picasso picasso) {
        this.picasso = picasso;
        this.contract = contract;
    }

    public void update(TrackInfo trackInfo){
        this.trackInfo = trackInfo;
        setVisibility(VISIBLE);
        tvTitle.setText(trackInfo.getName());
        tvArtist.setText(trackInfo.getArtist());

        if (trackInfo.getLyrics() != null) {
            tvLyricsReady.setText(R.string.show_lyrics);
            tvLyricsReady.setTextColor(getResources().getColor(R.color.colorAccent));
        }else{
            tvLyricsReady.setText(R.string.no_lyrics);
            tvLyricsReady.setTextColor(getResources().getColor(R.color.normalText));
        }

        picasso.load(trackInfo.getAlbumCover())
                .placeholder(R.drawable.cover_placeholder)
                .fit()
                .into(ivCover);
    }

    @OnClick(R.id.tvLyricsReady)
    public void showLyrics(){
        if(trackInfo.getLyrics() == null)
            return;

        if(contract != null)
            contract.showLyricsFragment(trackInfo);
    }

    public TrackInfo getTrackInfo(){
        return trackInfo;
    }
//    public interface NowListenViewContract{
//        void showLyricsFragment(TrackInfo trackInfo);
//    }
}
