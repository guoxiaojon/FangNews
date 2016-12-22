package com.example.jon.fangnews.widget;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by jon on 2016/12/17.
 */

public class DefaultItemTouchHelperCalllback extends ItemTouchHelper.Callback {

    private OnItemTouchListener mOnItemTouchListener;

    public DefaultItemTouchHelperCalllback(OnItemTouchListener onItemTouchListener) {
        this.mOnItemTouchListener = onItemTouchListener;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);

    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return mOnItemTouchListener.onMove(recyclerView,viewHolder, target);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        mOnItemTouchListener.onSwiped(viewHolder, direction);
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        if(actionState !=ItemTouchHelper.ACTION_STATE_IDLE){
            if(viewHolder instanceof ItemTouchViewHolder){
                ((ItemTouchViewHolder)viewHolder).onItemSelected();
            }
        }
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        if(viewHolder instanceof ItemTouchViewHolder){
            ((ItemTouchViewHolder)viewHolder).onItemClear();
        }
    }

    public interface OnItemTouchListener{
        boolean onMove(RecyclerView recyclerView,RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target);
        void onSwiped(RecyclerView.ViewHolder viewHolder, int direction);
    }
    public interface ItemTouchViewHolder{
        void onItemSelected();
        void onItemClear();
    }


}
