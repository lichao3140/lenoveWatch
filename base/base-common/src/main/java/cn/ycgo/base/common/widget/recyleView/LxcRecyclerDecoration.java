package cn.ycgo.base.common.widget.recyleView;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

/**
 * Created by brian
 * on 2018/5/17.
 */
public class LxcRecyclerDecoration extends RecyclerView.ItemDecoration {
    private int space;
    private IntervalType type;
    int left, top, right, bottom, firstItem, endItem;

    public LxcRecyclerDecoration setLayoutType(LxcRecyclerView.LayoutType layoutType) {
        this.layoutType = layoutType;
        return this;
    }

    LxcRecyclerView.LayoutType layoutType;

    public enum IntervalType {
        LEFT,
        RIGHT,
        TOP,
        BOTTOM,
        LEFT_RIGHT,
        TOP_BOTTOM,
        GRID
    }

    public LxcRecyclerDecoration(int space, IntervalType type) {
        this.space = space;
        this.type = type;

    }

    public LxcRecyclerDecoration(int left, int top, int right, int bottom) {
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
    }

    public LxcRecyclerDecoration(int left, int top, int right, int bottom, int firstItem, int endItem) {
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
        this.firstItem = firstItem;
        this.endItem = endItem;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        try {
            if (type != null) {
                switch (type) {
                    case LEFT:
                        outRect.left = space;
                        break;
                    case RIGHT:
                        outRect.right = space;
                        break;
                    case TOP:
                        outRect.top = space;
                        break;
                    case BOTTOM:
                        outRect.bottom = space;
                        break;
                    case LEFT_RIGHT:
                        outRect.left = space;
                        outRect.right = space;
                        break;
                    case TOP_BOTTOM:
                        outRect.top = space;
                        outRect.bottom = space;
                        break;
                    default:
                        if (layoutType != null) {
                            switch (layoutType) {
                                case VERTICAL:
                                    if (firstItem != 0 && parent.getChildLayoutPosition(view) == 0) {
                                        outRect.top = firstItem;
                                    } else {
                                        outRect.top = top;
                                    }
                                    if (endItem != 0 && parent.getChildLayoutPosition(view) == Objects.requireNonNull(parent.getAdapter()).getItemCount() - 1) {
                                        outRect.bottom = endItem;
                                    } else {
                                        outRect.bottom = bottom;
                                    }
                                    outRect.left = left;
                                    outRect.right = right;
                                    break;
                                case HORIZONTAL:
                                    if (firstItem != 0 && parent.getChildLayoutPosition(view) == 0) {
                                        outRect.left = firstItem;
                                    } else {
                                        outRect.left = left;
                                    }
                                    if (endItem != 0 && parent.getChildLayoutPosition(view) == Objects.requireNonNull(parent.getAdapter()).getItemCount() - 1) {
                                        outRect.right = endItem;
                                    } else {
                                        outRect.right = right;
                                    }
                                    outRect.bottom = bottom;
                                    outRect.top = top;
                                    break;
                                default:
                                    outRect.left = left;
                                    outRect.right = right;
                                    outRect.bottom = bottom;
                                    outRect.top = top;
                                    break;
                            }
                        } else {
                            outRect.left = left;
                            outRect.right = right;
                            outRect.bottom = bottom;
                            outRect.top = top;
                        }
                        break;
                }
            } else {
                if (layoutType != null) {
                    switch (layoutType) {
                        case VERTICAL:
                            if (firstItem != 0 && parent.getChildLayoutPosition(view) == 0) {
                                outRect.top = firstItem;
                            } else {
                                outRect.top = top;
                            }
                            if (endItem != 0 && parent.getChildLayoutPosition(view) == (Objects.requireNonNull(parent.getAdapter()).getItemCount() - 1)) {
                                outRect.bottom = endItem;
                            } else {
                                outRect.bottom = bottom;
                            }
                            outRect.left = left;
                            outRect.right = right;
                            break;
                        case HORIZONTAL:
                            if (firstItem != 0 && parent.getChildLayoutPosition(view) == 0) {
                                outRect.left = firstItem;
                            } else {
                                outRect.left = left;
                            }
                            if (endItem != 0 && parent.getChildLayoutPosition(view) == (Objects.requireNonNull(parent.getAdapter()).getItemCount() - 1)) {
                                outRect.right = endItem;
                            } else {
                                outRect.right = right;
                            }
                            outRect.bottom = bottom;
                            outRect.top = top;
                            break;
                        default:
                            outRect.left = left;
                            outRect.right = right;
                            outRect.bottom = bottom;
                            outRect.top = top;
                            break;
                    }
                } else {
                    outRect.left = left;
                    outRect.right = right;
                    outRect.bottom = bottom;
                    outRect.top = top;
                }
            }
        } catch (Exception e) {
            outRect.left = left;
            outRect.right = right;
            outRect.bottom = bottom;
            outRect.top = top;
        }
    }
}