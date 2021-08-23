package cn.ycgo.base.common.widget.recyleView.item;

import android.util.SparseArray;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.recyclerview.widget.RecyclerView;

public class ItemViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> viewArray = new SparseArray<>();

    public ItemViewHolder(View itemView) {
        super(itemView);
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T findViewById(@IdRes final int viewId) {
        View view = viewArray.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            viewArray.put(viewId, view);
        }
        return (T) view;
    }

    public void setOnClickListener(@IdRes final int viewId, View.OnClickListener listener) {
        findViewById(viewId).setOnClickListener(listener);
    }

    public void setOnLongClickListener(@IdRes final int viewId, View.OnLongClickListener listener) {
        findViewById(viewId).setOnLongClickListener(listener);
    }
}
