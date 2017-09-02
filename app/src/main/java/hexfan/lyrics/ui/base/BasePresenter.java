package hexfan.lyrics.ui.base;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by Pawel on 20.06.2017.
 */

public abstract class BasePresenter<T> {

    protected abstract void attach(T view);

    protected abstract void detach();

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

//    protected void addSubscriber(Disposable disposable) {
//        compositeDisposable.add(disposable);
//    }

    protected void dispose() {
        compositeDisposable.clear();
    }

    protected <V> void  addSubscriber(Observable<V> observable, DisposableObserver<V> observer){
        compositeDisposable.add(observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(observer));
    }

    protected <V> void  addSubscriber(Flowable<V> observable, ResourceSubscriber<V> observer){
        compositeDisposable.add(observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer));
    }
//
//    protected <T> void  addSubscriber(Flowable<T> observable, ResourceSubscriber<T> observer){
//        addSubscriber(
//                observable.subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribeWith(observer));
//    }

//    protected <T> void create(Observable<T> observable, final BaseView view, final BasePresenterListener<T> listener){
//
//        addSubscriber(observable, new DisposableObserver<T>() {
//            @Override
//            public void onNext(T t) {
//                listener.onReturn(t);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                view.displayError(e.getMessage());
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });
//    }

//    public interface BasePresenterListener <T> {
//        void onReturn(T items);
//    }
//
//    public void onDestroy(){
//        compositeDisposable.clear();
//    }
}
