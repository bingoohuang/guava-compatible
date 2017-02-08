package com.google.common.util.concurrent;

public interface FutureFallback<V> {
    <V> ListenableFuture<V> create(Throwable input);
}
