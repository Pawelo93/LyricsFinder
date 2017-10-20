package hexfan.lyrics.ui.base.adapter;

import android.support.v7.widget.RecyclerView;

/**
 * Created by Pawel on 25.08.2017.
 */

public interface PaginationAdapterContract<V, T extends RecyclerView.ViewHolder>  extends EaseAdapterContract<V, T>{

    void loadMore();
}
