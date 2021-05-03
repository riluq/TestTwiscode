package com.riluq.testtwiscode.utils

import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.riluq.testtwiscode.R
import java.text.DecimalFormat

class CommonUtils {
    companion object {
        fun showLoadingDialog(context: Context?): ProgressDialog? {
            val progressDialog = ProgressDialog(context)
            progressDialog.show()
            if (progressDialog.window != null) {
                progressDialog.window!!
                    .setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
            progressDialog.setContentView(R.layout.progress_dialog)
            progressDialog.isIndeterminate = true
            progressDialog.setCancelable(false)
            progressDialog.setCanceledOnTouchOutside(false)
            return progressDialog
        }

        fun currencyFormat(amount: String): String? {
            val formatter = DecimalFormat("###,###,##0")
            return formatter.format(amount.toDouble())
        }
    }
}