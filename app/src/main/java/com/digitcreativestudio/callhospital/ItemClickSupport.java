package com.digitcreativestudio.callhospital;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ItemClickSupport {
    private final RecyclerView mRecyclerView;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    private ItemClickSupport(RecyclerView recyclerView){
        mRecyclerView = recyclerView;
        mRecyclerView.setTag(R.id.item_click_support, this);
        // add onChild of recyclerView
        mRecyclerView.addOnChildAttachStateChangeListener(mAttachListener);
    }

    //interface (kerangka container untuk menyimpan recyclerView yang akan di set onClickListener
    public interface OnItemClickListener{
        void onItemClicked(RecyclerView recyclerView, int position, View v);
    }
    public interface OnItemLongClickListener{
        boolean onItemLongClicked(RecyclerView recyclerView, int position, View v);
    }

    //onClick
    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(mOnItemClickListener != null){
                RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(v);
                mOnItemClickListener.onItemClicked(mRecyclerView, holder.getAdapterPosition(), v);
            }
        }
    };

    private View.OnLongClickListener mOnLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            if(mOnItemLongClickListener != null){
                RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(v);
                return mOnItemLongClickListener.onItemLongClicked(mRecyclerView, holder.getAdapterPosition(), v);
            }
            return false;
        }
    };

    //set onChild of recyclerView
    private RecyclerView.OnChildAttachStateChangeListener mAttachListener = new RecyclerView.OnChildAttachStateChangeListener() {
        @Override
        public void onChildViewAttachedToWindow(View view) {
            if(mOnItemClickListener != null){
                view.setOnClickListener(mOnClickListener);
            }
            if(mOnItemLongClickListener != null){
                view.setOnLongClickListener(mOnLongClickListener);
            }
        }

        @Override
        public void onChildViewDetachedFromWindow(View view) {

        }
    };

    //detach
    private void detach(RecyclerView view){
        view.removeOnChildAttachStateChangeListener(mAttachListener);
        view.setTag(R.id.item_click_support, null);
    }

    //add & remove
    public static ItemClickSupport addTo(RecyclerView view){
        ItemClickSupport support = (ItemClickSupport)view.getTag(R.id.item_click_support);
        if (support == null){
            support = new ItemClickSupport(view);
        }
        return support;
    }

    public static ItemClickSupport removeFrom(RecyclerView view){
        ItemClickSupport support = (ItemClickSupport)view.getTag(R.id.item_click_support);
        if (support != null){
            support.detach(view);
        }
        return support;
    }

    //setOnItemClickListener/Long
    public ItemClickSupport setOnItemClickListener(OnItemClickListener listener){
        mOnItemClickListener = listener;
        return this;
    }
    public ItemClickSupport setOnItemLongClickListener(OnItemLongClickListener listener){
        mOnItemLongClickListener = listener;
        return this;
    }
}
