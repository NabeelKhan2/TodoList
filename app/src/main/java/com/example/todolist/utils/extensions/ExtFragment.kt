package com.example.todolist.utils.extensions

import android.widget.Toast
import androidx.annotation.StyleRes
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun Fragment.toast(content: Any = "", length: Int = Toast.LENGTH_SHORT) =
    requireContext().toast(content, length)

fun Fragment.alert(
    @StyleRes style: Int = 0,
    dialogBuilder: MaterialAlertDialogBuilder.() -> Unit
) {
    MaterialAlertDialogBuilder(requireContext(), style)
        .apply {
            setCancelable(false)
            dialogBuilder()
            create()
            show()
        }
}