package com.swensun.swutils.util;

import android.view.Window;
import android.view.WindowManager;
import com.swensun.swutils.R;

/**
 * Created  on 2019-07-16
 *
 * @author sunwen
 *
 * 常用的工具类或者代码小技巧：java
 */
public class JavaUtils {

    /**
     * 因为 Alertdialog 显示需要 Activity 的 context，
     * 此方法用于在无此类 Context 的情况下， 比如 Service，ime 中弹出 Dialog，
     * 通过增加 Theme，和 window token 。
     * 同时使用 {@link android.app.AlertDialog#setCancelable(boolean)} 和
     * {@link android.app.AlertDialog#setCanceledOnTouchOutside(boolean)} ，才能是点击外部区域取消对话框。
     *
     */
    private void showScoreDialog() {
//        ScoreDialog dialog = new ScoreDialog(mContext, R.style.AppTheme).withType(1).withCancel(true);
//        Window window = dialog.getWindow();
//        if (window != null) {
//            WindowManager.LayoutParams lp = window.getAttributes();
//            lp.token = getWindowToken();
//            lp.type = WindowManager.LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG;
//            window.setAttributes(lp);
//            window.addFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
//            dialog.setCanceledOnTouchOutside(true);
//            dialog.show();
//        }
    }
}
