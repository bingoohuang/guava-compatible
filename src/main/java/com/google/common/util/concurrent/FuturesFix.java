package com.google.common.util.concurrent;

import java.util.concurrent.Executor;

/**
 * @author bingoohuang [bingoohuang@gmail.com] Created on 2017/2/7.
 */
public class FuturesFix {
    public static <V> ListenableFuture<V> withFallback(
            ListenableFuture<V> input,
            final FutureFallback<V> fallback,
            Executor executor) {
        return Futures.catchingAsync(input, Throwable.class,
                new AsyncFunction<Throwable, V>() {
                    @Override
                    public ListenableFuture<V> apply(Throwable input) throws Exception {
                        return fallback.create(input);
                    }
                }, executor);
    }

    public static <V> ListenableFuture<V> withFallback(
            ListenableFuture<V> input, final FutureFallback<V> fallback) {
        return Futures.catchingAsync(input, Throwable.class, new AsyncFunction<Throwable, V>() {
            @Override
            public ListenableFuture<V> apply(Throwable input) throws Exception {
                return fallback.create(input);
            }
        });
    }

    public static <I, O> ListenableFuture<O> transform(
            ListenableFuture<I> input, AsyncFunction<? super I, ? extends O> function) {
        return Futures.transformAsync(input, function);
    }

    public static <I, O> ListenableFuture<O> transform(
            ListenableFuture<I> input, AsyncFunction<? super I, ? extends O> function,
            Executor executor) {
        return Futures.transformAsync(input, function, executor);
    }
}
