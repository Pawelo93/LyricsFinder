package hexfan.lyrics.ui.browse;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import hexfan.lyrics.R;
import hexfan.lyrics.model.DataModel;
import hexfan.lyrics.ui.base.BaseFragment;
import hexfan.lyrics.ui.components.HistoryView;
import hexfan.lyrics.ui.main.MainApplication;

/**
 * Created by Pawel on 30.07.2017.
 */

public class BrowseFragment extends BaseFragment implements BrowseView{

    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.container)
    FrameLayout container;

    @Inject
    DataModel dataModel;

    private HistoryView historyView;

    public static BrowseFragment newInstance() {
        
        Bundle args = new Bundle();

        BrowseFragment fragment = new BrowseFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        ButterKnife.bind(this, view);
        MainApplication.get(this).getMyComponents().inject(this);
        init();
        return view;
    }

    private void init(){

        historyView = new HistoryView(getContext());
        container.addView(historyView);

//        MainActivity.get(this).presenter.loadHistoryCache();

//        presenter.getTrackInfo("Scorpions", "Robot Man");



    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
