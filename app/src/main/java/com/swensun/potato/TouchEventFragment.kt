package com.swensun.potato


import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import kotlinx.android.synthetic.main.fragment_touch_event.*
import org.jetbrains.anko.support.v4.toast

class TouchEventFragment : Fragment(), View.OnClickListener {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_touch_event, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    private fun initView() {
        button1.setOnClickListener(this)
        button2.setOnClickListener(this)
        button3.setOnClickListener(this)
        show.setOnClickListener {
            //            if (frame_layout.visibility == View.GONE) {
//                frame_layout.visibility = View.VISIBLE
//                show.text = "hide"
//            } else {
//                frame_layout.visibility = View.GONE
//                show.text = "show"
//            }
            val act = requireActivity()
            val dialog = TouchDialog(act, act)
            dialog.setMessage("touch content")
            dialog.setTitle("Touch")
            dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "cancel") { _, _ -> dialog.dismiss() }
            dialog.setCanceledOnTouchOutside(false)
            dialog.setCancelable(false)
            dialog.show()
        }
        frame_layout.setOnTouchListener { v, event ->
            false
        }
        center_tv.setOnClickListener {
            toast("center_tv")
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            TouchEventFragment()
    }

    override fun onClick(v: View?) {
        toast((v as Button).text)
    }

    class TouchDialog(context: Context, val act: Activity) : androidx.appcompat.app.AlertDialog(context) {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        }
        override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
            val result = super.dispatchTouchEvent(ev)
            if (!result) {
                act.dispatchTouchEvent(ev)
            }
            return result
        }
    }
}
