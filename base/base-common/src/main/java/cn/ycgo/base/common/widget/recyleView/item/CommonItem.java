package cn.ycgo.base.common.widget.recyleView.item;


import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ColorUtils;
import cn.ycgo.base.common.R;

public class CommonItem<T extends BaseItem> extends BaseItem<T> {

    private int backgroundColor = ColorUtils.getColor(R.color.lightGray);

    public CommonItem(int layoutId) {
        super(layoutId);
    }

    @CallSuper
    @Override
    public void bind(@NonNull final ItemViewHolder holder, int position) {
        holder.itemView.setBackgroundColor(backgroundColor);
    }

    public CommonItem<T> setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }
}
