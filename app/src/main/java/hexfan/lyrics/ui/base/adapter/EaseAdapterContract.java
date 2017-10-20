package hexfan.lyrics.ui.base.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by Paweł on 05.04.2017.
 */

public interface EaseAdapterContract<V, T extends RecyclerView.ViewHolder> {

    T onCreateViewHolder(ViewGroup parent, int viewType);

    void onBindViewHolder(V element, T holder);

    void onItemClicked(V item);
}
