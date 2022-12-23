package com.cheqplease.pax_terminal.flutter_pax_terminal

import android.content.Context
import com.cheqplease.pax_terminal.flutter_pax_terminal.data.PAXTerminalConfig
import com.google.gson.Gson
import com.pax.poslink.CommSetting
import com.pax.poslink.PaymentRequest
import com.pax.poslink.PosLink
import com.pax.poslink.ProcessTransResult
import java.lang.ref.WeakReference

object PAXTerminalManager {

    private lateinit var terminalConfig: PAXTerminalConfig
    private lateinit var context: WeakReference<Context>

    fun init(adyenTerminalConfig: PAXTerminalConfig, context: Context) {
        this.terminalConfig = adyenTerminalConfig
        this.context = WeakReference<Context>(context)
    }

    fun authorizeTransaction(
        transactionId: String, amountInMinorUnit: Double?, cardType: String, captureType: String,
        paymentSuccessHandler: PaymentSuccessHandler<String>, paymentFailureHandler: PaymentFailureHandler<String>){

        val posLink = PosLink(context.get())
        val commSetting = CommSetting()
        val paymentRequest = PaymentRequest()


        paymentRequest.TenderType = paymentRequest.ParseTenderType(cardType)
        paymentRequest.TransType = paymentRequest.ParseTransType("SALE")
        paymentRequest.ECRRefNum = terminalConfig.terminalID
        paymentRequest.ECRTransID = transactionId
        if (amountInMinorUnit != null) {
            paymentRequest.Amount = amountInMinorUnit.toInt().toString()
        }

        commSetting.type = terminalConfig.connectionType
        commSetting.timeOut = terminalConfig.timeout
        commSetting.destIP = terminalConfig.destinationIP
        commSetting.destPort = terminalConfig.destinationPort

        posLink.PaymentRequest = paymentRequest

        posLink.SetCommSetting(commSetting)

        val transResult: ProcessTransResult = posLink.ProcessTrans()

        return if(transResult.Code == ProcessTransResult.ProcessTransResultCode.OK) {
            val response = posLink.PaymentResponse
            val gson = Gson()
            val resultJSON = gson.toJson(response)

            paymentSuccessHandler.onSuccess(resultJSON)

        }else{
            paymentFailureHandler.onFailure("Txn Failed")
        }

    }

    fun cancelInProgressTransaction(terminalId: String, transactionId: String, txnIdToCancel: String) {



    }
}