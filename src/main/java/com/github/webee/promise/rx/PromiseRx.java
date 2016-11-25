package com.github.webee.promise.rx;


import com.github.webee.promise.Deferred;
import com.github.webee.promise.Promise;
import com.github.webee.promise.functions.Action;
import rx.Observable;
import rx.Subscriber;
import rx.subjects.AsyncSubject;

/**
 * Created by webee on 16/11/22.
 */
public class PromiseRx {
    public static <T> Observable<T> observable(Promise<T> promise) {
        final AsyncSubject asyncSubject = AsyncSubject.create();
        promise.fulfilled(new Action<T>() {
            @Override
            public void run(T v) {
                asyncSubject.onNext(v);
                asyncSubject.onCompleted();
            }
        }).rejected(new Action<Throwable>() {
            @Override
            public void run(Throwable v) {
                asyncSubject.onError(v);
            }
        });
        return asyncSubject.asObservable();
    }

    public static <T> Promise<T> promise(Observable<T> observable) {
        final Deferred<T> deferred = new Deferred<>();
        observable.last()
                .subscribe(new Subscriber<T>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        deferred.reject(e);
                    }

                    @Override
                    public void onNext(T t) {
                        deferred.fulfill(t);
                    }
                });
        return deferred.promise;
    }
}
