package com.indmind.moviecataloguetwo.utils;

import androidx.annotation.Nullable;
import androidx.test.espresso.IdlingResource;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class SimpleIdlingResource implements IdlingResource {
    // Idleness is controlled with this boolean.
    private final AtomicBoolean mIsIdleNow = new AtomicBoolean(true);
    @Nullable
    private volatile ResourceCallback mCallback;

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        return mIsIdleNow.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        mCallback = callback;
    }

    /**
     * Sets the new idle state, if isIdleNow is true, it pings the {@link ResourceCallback}.
     *
     * @param isIdleNow false if there are pending operations, true if idle.
     */
    public void setIdleState(boolean isIdleNow) {
        mIsIdleNow.set(isIdleNow);
        if (isIdleNow && mCallback != null) {
            Objects.requireNonNull(mCallback).onTransitionToIdle();
        }
    }
}
