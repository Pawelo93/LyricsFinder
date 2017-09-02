package hexfan.lyrics.ui.components;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.Pivot;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hexfan.lyrics.R;
import hexfan.lyrics.model.pojo.TrackInfo;
import hexfan.lyrics.ui.base.BaseActivity;

/**
 * Created by Pawel on 29.07.2017.
 */

public class MainView extends LinearLayout {

    @BindView(R.id.tvTrackTitle)
    TextView tvSongName;
    @BindView(R.id.tvArtist)
    TextView tvArtistName;
    @BindView(R.id.picker)
    DiscreteScrollView picker;

    private Adapter adapter;

    public MainView(Context context) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.main_view, this);
        ButterKnife.bind(this);

    }

    public void setup(final List<TrackInfo> list){

        if (list.size() == 0) {
            tvArtistName.setVisibility(GONE);
            picker.setVisibility(GONE);
            tvSongName.setText("Brak historii");
            return;
        }

        adapter = new Adapter(list);

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
                tvSongName.setText(list.get(adapterPosition).getName());
                tvArtistName.setText(list.get(adapterPosition).getArtist());
            }
        });
    }

    private class Adapter extends RecyclerView.Adapter<ViewHolder>{

        private List<TrackInfo> list;

        public Adapter(List<TrackInfo> list){
            this.list = list;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return ViewHolder.create(MainView.this.getContext(), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.bind(list.get(position));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    private static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView ivCover;

        public static ViewHolder create(Context context, ViewGroup parent){
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.picker_item, parent, false));
        }

        public ViewHolder(View itemView) {
            super(itemView);

            ivCover = itemView.findViewById(R.id.ivCover);
        }

        public void bind(TrackInfo trackInfo){

            ((BaseActivity) itemView.getContext()).getPicasso()
                    .load(trackInfo.getAlbumCover())
                    .placeholder(R.mipmap.ic_launcher)
                    .fit()
                    .centerCrop()
                    .into(ivCover);
        }
    }
}
