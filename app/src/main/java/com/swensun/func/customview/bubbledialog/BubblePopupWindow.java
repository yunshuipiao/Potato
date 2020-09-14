//package com.swensun.func.customview.bubbledialog;
//
//import android.content.Context;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.PopupWindow;
//import android.widget.TextView;
//
//import com.swensun.potato.R;
//
//
///**
// * @author zp
// * 在键盘上显示引导的控件
// */
//public class BubblePopupWindow extends PopupWindow {
//
//    private final Context mContext;
//    private BubbleLayout mBubbleLayout;
//    private BubbleLayout.Look mGravity = BubbleLayout.Look.BOTTOM;
//    private View.OnClickListener onClickListener;
//    private TextView contentView;
//    private TextView closeView;
//    private int offsetY = 0;
//
//    public BubblePopupWindow(Context context) {
//        this.mContext = context;
//        initView();
//    }
//
//    private void initView() {
//        mBubbleLayout = new BubbleLayout(mContext);
//        View rootView = LayoutInflater.from(mContext).inflate(R.layout.dialog_guide_bubble, null);
//        contentView = rootView.findViewById(R.id.tv_content);
//        closeView = rootView.findViewById(R.id.tv_close);
//        closeView.setOnClickListener(v -> {
//            if (onClickListener != null) {
//                onClickListener.onClick(v);
//            }
//        });
//        mBubbleLayout.addView(rootView);
//        setContentView(mBubbleLayout);
//        setOutsideTouchable(false);
//        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
//        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
//
//        offsetY = Utils.dpToPx(mContext, 10);
//    }
//
//    /**
//     * 设置弹出的位置：支持 Bottom 和 TOP 两个属性
//     *
//     * @param gravity 位置设置
//     * @return this
//     */
//    public BubblePopupWindow withGravity(BubbleLayout.Look gravity) {
//        this.mGravity = gravity;
//        BubbleLayout.Look arrowPosition = BubbleLayout.Look.BOTTOM;
//        switch (gravity) {
//            case TOP: {
//                arrowPosition = BubbleLayout.Look.BOTTOM;
//                break;
//            }
//            case BOTTOM: {
//                arrowPosition = BubbleLayout.Look.TOP;
//                break;
//            }
//            default:
//                break;
//        }
//        mBubbleLayout.setLook(arrowPosition);
//        return this;
//    }
//
//    /**
//     * 计算弹窗中箭头的位置
//     */
//    private void setLookPosition(int viewOrKeyWidth, int x, int[] location) {
//        if (x < 0) {
//            mBubbleLayout.setLookPosition(location[0] + viewOrKeyWidth / 2 - mBubbleLayout.getLookWidth() / 2);
//        } else if (x + getMeasuredWidth() - Utils.getScreenWH(mContext)[0] > 0) {
//            mBubbleLayout.setLookPosition(getMeasuredWidth() - (Utils.getScreenWH(mContext)[0] - location[0] - viewOrKeyWidth / 2) - mBubbleLayout.getLookWidth() / 2);
//        } else {
//            mBubbleLayout.setLookPosition(getMeasuredWidth() / 2);
//        }
//    }
//
//    /**
//     * 设置 popupWindow 弹窗的位置
//     *
//     * @param parent：依据的view，比如 settingView
//     */
//    public void show(View parent) {
//        // 显示位置
//        int x, y;
//        int[] location = new int[2];
//        parent.getLocationOnScreen(location);
//        switch (mGravity) {
//            case BOTTOM: {
//                x = location[0] + parent.getWidth() / 2 - getMeasuredWidth() / 2;
//                y = location[1] + parent.getHeight() - Utils.getStatusHeight(mContext) - offsetY;
//                setLookPosition(parent.getWidth(), x, location);
//                showAtLocation(parent, Gravity.NO_GRAVITY, x, y);
//                break;
//            }
//            case TOP: {
//                x = location[0] + parent.getWidth() / 2 - getMeasuredWidth() / 2;
//                y = location[1] - getMeasureHeight() - Utils.getStatusHeight(mContext) + offsetY;
//                setLookPosition(parent.getWidth(), x, location);
//                showAtLocation(parent, Gravity.NO_GRAVITY, x, y);
//                break;
//            }
//            default:
//                break;
//        }
//    }
//
//    /**
//     * 弹窗的内容
//     *
//     * @param content 内容
//     * @return
//     */
//    public BubblePopupWindow withContent(String content) {
//        contentView.setText(content);
//        return this;
//    }
//
//    /**
//     * 测量高度
//     *
//     * @return
//     */
//    public int getMeasureHeight() {
//        getContentView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
//        return getContentView().getMeasuredHeight();
//    }
//
//    /**
//     * 测量宽度
//     *
//     * @return
//     */
//    public int getMeasuredWidth() {
//        getContentView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
//        return getContentView().getMeasuredWidth();
//    }
//
//    public BubblePopupWindow withOnCloseClickListener(View.OnClickListener listener) {
//        onClickListener = listener;
//        return this;
//    }
//}
//
