package com.qianying.bike.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qianying.bike.R;

/**
 */
public class CustomDialog extends Dialog {

    public CustomDialog(Context context) {
        super(context);
    }

    public CustomDialog(Context context, int theme) {
        super(context, theme);
    }

    public CustomDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public static class Builder {
        private Context context;
        private onDialogClickListener mOnDialogClickListener;

        public Builder(Context context) {
            this.context = context;
        }


        public Builder setOnCLickListener(
                onDialogClickListener listener) {
            this.mOnDialogClickListener = listener;
            return this;
        }

        public CustomDialog created() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final CustomDialog dialog = new CustomDialog(context, R.style.Dialog);
            View layout = inflater.inflate(R.layout.dialog_return_deposit, null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            // set the dialog title
            // set the confirm button
            if (mOnDialogClickListener != null) {
                (layout.findViewById(R.id.btn_confirm))
                        .setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                mOnDialogClickListener.onConfirm(dialog);
                            }
                        });
            }
            if (mOnDialogClickListener != null) {
                (layout.findViewById(R.id.btn_cancel))
                        .setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                mOnDialogClickListener.onCancel(dialog);
                            }
                        });
            }
//            (layout.findViewById(R.id.cancel))
//                    .setOnClickListener(new View.OnClickListener() {
//                        public void onClick(View v) {
//                            negativeButtonClickListener.onClick(dialog,
//                                    DialogInterface.BUTTON_NEGATIVE);
//                        }
//                    });
            dialog.setContentView(layout);
            return dialog;
        }

    }


    public interface onDialogClickListener {
        void onCancel(Dialog dialog);

        void onConfirm(Dialog dialog);
    }

}
