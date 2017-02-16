package com.dtunctuncer.booster.utils.widgets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

public class AdvancedRecyclerView extends RecyclerView {
    private View emptyView;
    private View loadingView;
    private TextView countTextView;
    private boolean isCountTextAdd;
    private AdapterDataObserver emptyObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            checkIfEmpty();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            checkIfEmpty();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            checkIfEmpty();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            checkIfEmpty();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            checkIfEmpty();
        }
    };

    public AdvancedRecyclerView(Context context) {
        super(context);
    }

    public AdvancedRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AdvancedRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void checkIfEmpty() {
        int count;
        if (getAdapter() != null) {
            if (loadingView != null)
                loadingView.setVisibility(GONE);
            count = getAdapter().getItemCount();
            if (this.countTextView != null) {
                if (isCountTextAdd) {
                    countTextView.setText(String.format("%s Adet", count));
                } else {
                    countTextView.setText(String.valueOf(count));
                }
            }
            if (emptyView != null) {
                final boolean emptyViewVisible = count == 0;
                emptyView.setVisibility(emptyViewVisible ? VISIBLE : GONE);
                if (!emptyViewVisible) {
                    setVisibility(VISIBLE);
                } else
                    setVisibility(GONE);
            }
        } else {
            if (loadingView != null) {
                loadingView.setVisibility(VISIBLE);
                setVisibility(GONE);
            }
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
        final Adapter oldAdapter = getAdapter();
        if (oldAdapter != null) {
            oldAdapter.unregisterAdapterDataObserver(emptyObserver);
        }
        super.setAdapter(adapter);
        if (adapter != null) {
            adapter.registerAdapterDataObserver(emptyObserver);
        }
        checkIfEmpty();
        emptyObserver.onChanged();
    }

    public void setEmptyView(View emptyView) {
        if (emptyView == null)
            return;
        this.emptyView = emptyView;
        checkIfEmpty();
    }

    public void setLoadingView(View loadingView) {
        if (loadingView == null)
            return;
        this.loadingView = loadingView;
        checkIfEmpty();
    }

    public void setCountTextView(TextView countTextView, boolean isCountTextAdd) {
        this.isCountTextAdd = isCountTextAdd;
        this.countTextView = countTextView;
        checkIfEmpty();
    }
}