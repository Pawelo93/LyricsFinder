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

/**
 * Created by Pawel on 29.07.2017.
 */

public class NowListenView extends LinearLayout {

//    public static final String HIDE = "hide";
//    public static final String SHOW = "show";

    @BindView(R.id.tvNowListenTitle)
    TextView tvTitle;
    @BindView(R.id.tvNowListenArtist)
    TextView tvArtist;
    @BindView(R.id.ivCover)
    ImageView ivCover;
    @BindView(R.id.ivShow)
    ImageView ivShow;

    public NowListenView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

//        MainApplication.getComponent(context).create(this);
//        ((ComponentInterface) ((Activity) context).getApplication()).getComponent().create(this);
        LayoutInflater.from(context).inflate(R.layout.now_listen_view, this);
        ButterKnife.bind(this);
    }

    public void setup(Picasso picasso, TrackInfo trackInfo){
        setVisibility(VISIBLE);
        tvTitle.setText(trackInfo.getName());
        tvArtist.setText(trackInfo.getArtist());

        picasso.load(trackInfo.getAlbumCover())
                .fit()
                .into(ivCover);
    }

    @OnClick(R.id.ivShow)
    public void showLyrics(){

    }
}
