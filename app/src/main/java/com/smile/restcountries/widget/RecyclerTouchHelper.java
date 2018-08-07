package com.smile.restcountries.widget;

import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

import com.smile.restcountries.adapter.CountryAdapter;


public class RecyclerTouchHelper extends ItemTouchHelper.SimpleCallback implements RecyclerView.OnItemTouchListener {
    private TouchHelpListener touchHelpListener;
    private VelocityTracker velocityTracker;
    private Float velocityX;
    private boolean swipeBack;

    public RecyclerTouchHelper(int dragDirs, int swipeDirs, TouchHelpListener touchHelpListener) {
        super(dragDirs, swipeDirs);
        this.touchHelpListener = touchHelpListener;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
        return false;
    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        if (swipeBack) {
            swipeBack = false;
            return 0;
        }
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        //touchHelpListener.onSwiped(viewHolder, i);
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
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        final View foregroundView = ((CountryAdapter.ViewHolder) viewHolder).viewForeground;
        float fraction = Math.abs(dX / viewHolder.itemView.getWidth());
        Log.i("values", String.valueOf(-0.2f * viewHolder.itemView.getWidth()));
        Log.i("fraction", String.valueOf(fraction));
        switch (actionState) {
            case ItemTouchHelper.ACTION_STATE_SWIPE:
                Log.i("game", String.valueOf(fraction));
                getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY,
                      actionState, isCurrentlyActive);
                break;
            case ItemTouchHelper.ACTION_STATE_DRAG:
                if (fraction > 0.4f) {
                    Log.i("meg11", String.valueOf(fraction));
                    getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, -0.2f * viewHolder.itemView.getWidth(), dY,
                          actionState, isCurrentlyActive);
                } else {
                    getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY,
                          actionState, isCurrentlyActive);
                }
                break;
            default:
                getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY,
                      actionState, isCurrentlyActive);
        }
        /*if (fraction > 0.4f) {
            Log.i("meg11", String.valueOf(fraction));
            getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, -200, dY,
                  actionState, isCurrentlyActive);
        } else if (fraction > 0.2f) {
            Log.i("here1122", "here22");
            getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY,
                  actionState, isCurrentlyActive);
        } else {
            getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY,
                  actionState, isCurrentlyActive);
        }*/
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
        return true;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
        swipeBack = motionEvent.getAction() == MotionEvent.ACTION_CANCEL || motionEvent.getAction() == MotionEvent.ACTION_UP;
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean b) {

    }

    public interface TouchHelpListener {
        void onSwiped(RecyclerView.ViewHolder viewHolder, int direction);
    }
}
