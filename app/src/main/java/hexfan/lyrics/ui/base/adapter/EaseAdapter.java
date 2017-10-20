package hexfan.lyrics.ui.base.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paweł on 05.04.2017.
 */

public class EaseAdapter<V, T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {

    protected List<V> list;
    private EaseAdapterContract<V, T> easeAdapterContract;
    private boolean noMore;
    private boolean paginationOn;
    private int loadBeforeEnd = 2;

    public EaseAdapter(@NonNull EaseAdapterContract<V, T> easeAdapterContract){
        this.easeAdapterContract = easeAdapterContract;
        list = new ArrayList<>();
    }

    @Override
    public T onCreateViewHolder(ViewGroup parent, int viewType) {
        final T viewHolder = easeAdapterContract.onCreateViewHolder(parent, viewType);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                easeAdapterContract.onItemClicked(list.get(viewHolder.getAdapterPosition()));
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(T holder, int position) {
        easeAdapterContract.onBindViewHolder(list.get(position), holder);

        if(easeAdapterContract instanceof PaginationAdapterContract
                && !noMore && paginationOn && position == list.size() - loadBeforeEnd) {
            ((PaginationAdapterContract) easeAdapterContract).loadMore();
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<V> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setList(List<V> list, boolean cached) {
        this.list = list;
        paginationOn = !cached;
        notifyDataSetChanged();
    }

    public void setList(boolean newList, List<V> list) {
        if(newList)
            this.list = list;
        else
            addToList(list);
        notifyDataSetChanged();
    }

    public void addToList(List<V> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public V getItem(int position) {
        return list.get(position);
    }

    public void setNoMoreArticles(){
        this.noMore = true;
    }

    public void add(V element) {
        list.add(element);
        notifyItemInserted(list.size()-1);
    }

    public void addNewOnTop(V item) {
        list.add(0, item);
        notifyItemInserted(0);
    }

    public void addNewOnBottom(V item) {
        list.add(item);
        notifyItemInserted(list.size()-1);
    }

    public void remove(V item) {
        int pos = 0;
        for (V oldItem : list) {
            if(oldItem == item) {
                list.remove(oldItem);
                notifyItemRemoved(pos);
                break;
            }

            pos++;
        }
    }

    public void clear() {
        list.clear();
    }

    public boolean isEmpty() {
        return list.size() == 0;
    }

    public void setLoadBeforeEnd(int loadBeforeEnd) {
        this.loadBeforeEnd = loadBeforeEnd;
    }

    //    public List<V> getList(){
//        return list;
//    }
}
