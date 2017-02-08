package com.google.common.util.concurrent;

/**
 * @author bingoohuang [bingoohuang@gmail.com] Created on 2017/2/7.
 */
public interface FutureFallback<V> {
    <V> ListenableFuture<V> create(Throwable input);
}
