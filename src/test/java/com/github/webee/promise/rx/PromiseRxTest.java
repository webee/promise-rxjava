package com.github.webee.promise.rx;

import com.github.webee.promise.Promise;
import com.github.webee.promise.functions.Action;
import org.junit.Test;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by webee on 16/11/22.
 */
public class PromiseRxTest {
    @Test
    public void observable() {
        Observable<String> o = Observable.just("v");
        o.subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.out.println("completed");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("error");
                e.printStackTrace();
            }

            @Override
            public void onNext(String s) {
                System.out.println("next: " + s);
            }
        });
    }

    @Test
    public void promise() throws Throwable {
        Promise<String> p = Promise.resolve("v");
        p.fulfilled(new Action<String>() {
            @Override
            public void run(String v) {
                System.out.println("next: " + v);
            }
        }).rejected(new Action<Throwable>() {
            @Override
            public void run(Throwable v) {
                System.out.println("error");
                v.printStackTrace();
            }
        }).settled(new Runnable() {
            @Override
            public void run() {
                System.out.println("completed");
            }
        }).await();
    }

    @Test
    public void promiseToObservable() {
        Promise<String> p = Promise.resolve("v");
        Observable<String> o = PromiseRx.observable(p);
        o.subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.out.println("completed");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("error");
                e.printStackTrace();
            }

            @Override
            public void onNext(String s) {
                System.out.println("next: " + s);
            }
        });
    }

    @Test
    public void observableToPromise() throws Throwable {
        Observable<String> o = Observable.just("v");
        Promise<String> p = PromiseRx.promise(o);
        p.fulfilled(new Action<String>() {
            @Override
            public void run(String v) {
                System.out.println("next: " + v);
            }
        }).rejected(new Action<Throwable>() {
            @Override
            public void run(Throwable v) {
                System.out.println("error");
                v.printStackTrace();
            }
        }).settled(new Runnable() {
            @Override
            public void run() {
                System.out.println("completed");
            }
        }).await();
    }
}