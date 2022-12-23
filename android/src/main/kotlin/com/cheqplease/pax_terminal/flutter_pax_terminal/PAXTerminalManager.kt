package com.cheqplease.pax_terminal.flutter_pax_terminal

import android.content.Context
import com.cheqplease.pax_terminal.flutter_pax_terminal.data.PAXTerminalConfig
import java.lang.ref.WeakReference

object PAXTerminalManager {

    private lateinit var terminalConfig: PAXTerminalConfig
    private lateinit var context: WeakReference<Context>

    fun init(adyenTerminalConfig: PAXTerminalConfig, context: Context) {
        this.terminalConfig = adyenTerminalConfig
        this.context = WeakReference<Context>(context)
    }

    fun authorizeTransaction(terminalId: String, transactionId: String, amountInMinorUnit : String){



    }

    fun cancelInProgressTransaction(terminalId: String, transactionId: String, txnIdToCancel: String) {



    }
}