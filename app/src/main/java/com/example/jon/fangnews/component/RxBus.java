package com.example.jon.fangnews.component;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by jon on 2016/12/6.
 */

public class RxBus {
    private static RxBus sInstance;

    private final Subject<Object,Object> bus;
    private RxBus(){
         bus = new SerializedSubject<>(PublishSubject.create());
    }
    public static RxBus getDefault(){
        if(sInstance == null){
            synchronized (RxBus.class){
                if(sInstance == null){
                    sInstance = new RxBus();
                }
            }
        }
        return sInstance;
    }

    public void post(Object o){
        bus.onNext(o);
    }

    public <T> Observable<T> toObservable(Class<T> eventType){
        return bus.ofType(eventType);
    }




}
