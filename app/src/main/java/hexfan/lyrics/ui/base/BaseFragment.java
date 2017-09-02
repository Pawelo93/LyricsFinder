package hexfan.lyrics.ui.base;

import android.support.v4.app.Fragment;

import butterknife.Unbinder;

/**
 * Created by Pawel on 20.06.2017.
 */

public class BaseFragment extends Fragment {

    private Unbinder unbinder;

    protected void setUnbinder(Unbinder unbinder){
        this.unbinder = unbinder;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(unbinder != null) {
            unbinder.unbind();
        }
    }
}
