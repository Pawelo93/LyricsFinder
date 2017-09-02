package hexfan.lyrics.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hexfan.lyrics.R;
import hexfan.lyrics.model.pojo.TrackInfo;
import hexfan.lyrics.ui.base.BaseActivity;
import hexfan.lyrics.ui.base.BaseFragment;
import hexfan.lyrics.ui.components.MainView;

/**
 * Created by Pawel on 30.07.2017.
 */

public class MainFragment extends BaseFragment {

    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.container)
    FrameLayout container;

    private MainView mainView;

    public static MainFragment newInstance() {
        
        Bundle args = new Bundle();

        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init(){

        mainView = new MainView(getContext());
        container.addView(mainView);

        MainActivity.get(this).presenter.loadHistoryCache();

//        presenter.getTrackInfo("Scorpions", "Robot Man");



    }

    public void showHistory(List<TrackInfo> cacheList) {
        mainView.setup(cacheList);

    }
}
