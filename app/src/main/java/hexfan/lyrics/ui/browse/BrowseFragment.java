package hexfan.lyrics.ui.browse;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import hexfan.lyrics.R;
import hexfan.lyrics.di.Injector;
import hexfan.lyrics.model.pojo.TrackInfo;
import hexfan.lyrics.ui.base.BaseFragment;
import hexfan.lyrics.ui.components.HistoryView;
import hexfan.lyrics.ui.main.MainActivity;
import hexfan.lyrics.ui.main.MainView;

/**
 * Created by Pawel on 30.07.2017.
 */

public class BrowseFragment extends BaseFragment implements BrowseView {

    @BindView(R.id.etSearch)
    EditText etSearch;
//    @BindView(R.id.container)
//    FrameLayout container;
    @BindView(R.id.historyView)
    HistoryView historyView;

    @Inject
    BrowseViewModel viewModel;
    @Inject
    Picasso picasso;

    public static BrowseFragment newInstance() {
        
        Bundle args = new Bundle();

        BrowseFragment fragment = new BrowseFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        View view = inflater.inflate(R.layout.browse_fragment, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }



    private void init(){

        historyView.setup((MainView) getActivity(), picasso);


    }

    @Override
    public void bind() {
        addSubscribe(viewModel.observeLastTrackInfos().subscribe(trackInfos -> {
            historyView.setList(trackInfos);
            historyView.setPositionLast();
        }));
    }
}
