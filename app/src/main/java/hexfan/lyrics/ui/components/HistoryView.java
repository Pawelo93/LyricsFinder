package hexfan.lyrics.ui.components;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.Pivot;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hexfan.lyrics.R;
import hexfan.lyrics.model.pojo.TrackInfo;
import hexfan.lyrics.ui.base.adapter.EaseAdapter;
import hexfan.lyrics.ui.base.adapter.EaseAdapterContract;
import hexfan.lyrics.ui.main.MainView;

/**
 * Created by Pawel on 29.07.2017.
 */

public class HistoryView extends LinearLayout implements EaseAdapterContract<TrackInfo, HistoryView.ViewHolder>{

    @BindView(R.id.tvTrackTitle)
    TextView tvSongName;
    @BindView(R.id.tvArtist)
    TextView tvArtistName;
    @BindView(R.id.picker)
    DiscreteScrollView picker;

    private EaseAdapter<TrackInfo, HistoryView.ViewHolder> adapter;
    private MainView mainView;

    Picasso picasso;

    public HistoryView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.history_view, this);
        ButterKnife.bind(this);
    }

    public void setup(MainView mainView, Picasso picasso){
        this.picasso = picasso;
        this.mainView = mainView;

        adapter = new EaseAdapter<>(this);

        picker.setAdapter(adapter);
        picker.setItemTransformer(new ScaleTransformer.Builder()
                .setMaxScale(1.05f)
                .setMinScale(0.8f)
                .setPivotX(Pivot.X.CENTER) // CENTER is a default one
                .setPivotY(Pivot.Y.CENTER) // CENTER is a default one
                .build());

        picker.addOnItemChangedListener(new DiscreteScrollView.OnItemChangedListener<RecyclerView.ViewHolder>() {
            @Override
            public void onCurrentItemChanged(@Nullable RecyclerView.ViewHolder viewHolder, int adapterPosition) {
                tvSongName.setText(adapter.getItem(adapterPosition).getName());
                tvArtistName.setText(adapter.getItem(adapterPosition).getArtist());
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ViewHolder.create(HistoryView.this.getContext(), parent);
    }

    @Override
    public void onBindViewHolder(TrackInfo element, ViewHolder holder) {
        holder.bind(picasso, element);
    }

    @Override
    public void onItemClicked(TrackInfo item) {
        mainView.showLyricsFragment(item);
    }

    public void setList(List<TrackInfo> trackInfos) {
        adapter.setList(trackInfos);

        if (trackInfos.size() == 0) {
            tvArtistName.setVisibility(GONE);
            picker.setVisibility(GONE);
            tvSongName.setText("Brak historii");
        }

    }

    public void setPositionLast() {
        picker.scrollToPosition(adapter.getItemCount());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView ivCover;

        public static ViewHolder create(Context context, ViewGroup parent){
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.picker_item, parent, false));
        }

        public ViewHolder(View itemView) {
            super(itemView);
            ivCover = itemView.findViewById(R.id.ivCover);
        }

        public void bind(Picasso picasso, final TrackInfo trackInfo){
            if(trackInfo.getAlbumCover() != null && !trackInfo.getAlbumCover().equals(""))
            picasso.load(trackInfo.getAlbumCover())
                    .placeholder(R.drawable.cover_placeholder)
                    .fit()
                    .centerCrop()
                    .into(ivCover);
        }
    }
}
