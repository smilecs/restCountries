package com.smile.restcountries.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MotionEvent;
import android.view.View;

import com.smile.restcountries.adapter.CountryAdapter;


public class RecyclerTouchHelper extends ItemTouchHelper.SimpleCallback {
    private TouchHelpListener touchHelpListener;
    private boolean swipeBack, scroll, swiped, isCurrentlyActive;
    private int actionState;
    private View foregroundView;
    private float fraction;
    private Canvas c;
    private float dY, dX;
    private RecyclerView recyclerView;
    private Context context;

    public RecyclerTouchHelper(int dragDirs, int swipeDirs, TouchHelpListener touchHelpListener, Context context) {
        super(dragDirs, swipeDirs);
        this.touchHelpListener = touchHelpListener;
        this.context = context;
    }

    public void setScroll(boolean scroll) {
        this.scroll = scroll;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (swiped) {
            swiped = false;
            touchHelpListener.onSwiped(viewHolder, i);
        } else if ((swipeBack && fraction >= 0.4f) && !swiped) {
            swiped = true;
            getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, -200, 0f,
                  actionState, false);
        }
    }

    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView,
                                RecyclerView.ViewHolder viewHolder, float dX, float dY,
                                int actionState, boolean isCurrentlyActive) {
        final View foregroundView = ((CountryAdapter.ViewHolder) viewHolder).viewForeground;
        getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY,
              actionState, isCurrentlyActive);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        final View foregroundView = ((CountryAdapter.ViewHolder) viewHolder).viewForeground;
        getDefaultUIUtil().clearView(foregroundView);
    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void onChildDraw(@NonNull final Canvas c, @NonNull final RecyclerView recyclerView,
                            @NonNull final RecyclerView.ViewHolder viewHolder,
                            final float dX, final float dY, final int actionState, final boolean isCurrentlyActive) {
        final View foregroundView = ((CountryAdapter.ViewHolder) viewHolder).viewForeground;
        this.recyclerView = recyclerView;
        this.dY = dY;
        this.dX = dX;
        this.foregroundView = foregroundView;
        this.c = c;
        fraction = Math.abs(dX / viewHolder.itemView.getWidth());
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getActionMasked()) {
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        swipeBack = true;
                        scroll = false;
                        break;
                    case MotionEvent.ACTION_DOWN:
                        swipeBack = false;
                        reset(null, (CountryAdapter.ViewHolder) viewHolder);
                        scroll = false;
                        break;
                }
                return false;
            }
        });
        if (!scroll && !swipeBack) {
            getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY,
                  actionState, isCurrentlyActive);
        }
    }

    private void reset(View currentView, CountryAdapter.ViewHolder viewHolder) {
        if (this.foregroundView != null && currentView != this.foregroundView) {
            getDefaultUIUtil().clearView(this.foregroundView);
            recyclerView.getAdapter().notifyItemChanged(viewHolder.getAdapterPosition());
        }
    }

    public interface TouchHelpListener {
        void onSwiped(RecyclerView.ViewHolder viewHolder, int direction);
    }
}
