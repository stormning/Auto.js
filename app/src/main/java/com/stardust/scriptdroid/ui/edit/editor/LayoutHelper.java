package com.stardust.scriptdroid.ui.edit.editor;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.text.Layout;
import android.text.TextUtils;

/**
 * Created by Stardust on 2018/2/13.
 */

public class LayoutHelper {


    private static final Rect sTempRect = new Rect();

    public static long getLineRangeForDraw(Layout layout, Canvas canvas) {
        int dtop, dbottom;
        synchronized (sTempRect) {
            if (!canvas.getClipBounds(sTempRect)) {
                // Negative range end used as a special flag
                return packRangeInLong(0, -1);
            }

            dtop = sTempRect.top;
            dbottom = sTempRect.bottom;
        }

        final int top = Math.max(dtop, 0);
        final int bottom = Math.min(layout.getLineTop(layout.getLineCount()), dbottom);

        if (top >= bottom) return packRangeInLong(0, -1);
        return packRangeInLong(layout.getLineForVertical(top), layout.getLineForVertical(bottom));
    }

    /**
     * Pack 2 int values into a long, useful as a return value for a range
     */
    public static long packRangeInLong(int start, int end) {
        return (((long) start) << 32) | end;
    }

    /**
     * Get the start value from a range packed in a long by {@link #packRangeInLong(int, int)}
     */
    public static int unpackRangeStartFromLong(long range) {
        return (int) (range >>> 32);
    }

    public static int unpackRangeEndFromLong(long range) {
        return (int) (range & 0x00000000FFFFFFFFL);
    }

}
