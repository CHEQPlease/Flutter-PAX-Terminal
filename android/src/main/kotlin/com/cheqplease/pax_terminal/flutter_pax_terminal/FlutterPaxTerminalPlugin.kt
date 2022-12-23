package com.cheqplease.pax_terminal.flutter_pax_terminal

import android.content.Context
import androidx.annotation.NonNull
import com.cheqplease.pax_terminal.flutter_pax_terminal.data.PAXTerminalConfig

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FlutterPaxTerminalPlugin : FlutterPlugin, MethodCallHandler {

  private lateinit var applicationContext: Context
  private lateinit var flutterAssets: FlutterPlugin.FlutterAssets
  private lateinit var channel: MethodChannel
  private lateinit var flutterPluginBinding : FlutterPlugin.FlutterPluginBinding

  companion object {
    lateinit var terminalConfig: PAXTerminalConfig
  }

  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    onAttachedToEngine(flutterPluginBinding.applicationContext, flutterPluginBinding.binaryMessenger,flutterPluginBinding.flutterAssets)
  }

  private fun onAttachedToEngine(applicationContext: Context, messenger: BinaryMessenger, flutterAssets: FlutterPlugin.FlutterAssets) {
    this.applicationContext = applicationContext
    this.flutterAssets = flutterAssets
    channel = MethodChannel(messenger, "com.itsniaz.adyenterminal/channel")
    channel.setMethodCallHandler(this)
  }

  @OptIn(DelicateCoroutinesApi::class)
  override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {

    if (call.method == "init") {

      val destinationIP = call.argument<String>("destinationIP")!!
      val destinationPort = call.argument<String>("destinationPort")!!
      val timeout = call.argument<String>("timeout")!!
      val terminalID = call.argument<String>("terminalID")!!
      val connectionType = call.argument<String>("connectionType")!!

      terminalConfig = PAXTerminalConfig(
        destinationIP = destinationIP,
        destinationPort = destinationPort,
        timeout = timeout,
        terminalID = terminalID,
        connectionType = connectionType)

      PAXTerminalManager.init(terminalConfig,applicationContext)

    } else if (call.method == "authorize_transaction") {

      val amount = call.argument<Double>("amount")
      val captureType = call.argument<String>("captureType")
      val transactionId = call.argument<String>("transactionId")
      val currency = call.argument<String>("currency")
      val reqAmount = call.argument<Double>("amount")

      if (amount != null && captureType!=null && transactionId!=null && currency!=null && reqAmount!=null) {
        GlobalScope.launch(Dispatchers.IO) {
          try {
            PAXTerminalManager.authorizeTransaction(
              terminalId = terminalConfig.terminalID,
              transactionId = transactionId,
              amountInMinorUnit = currency,
              )
          } catch (e: Exception) {
            result.error("FAILED","TXN FAILED",e.message)
            println(e.stackTraceToString())
          }
        }
      }
    }

    else if(call.method == "cancel_transaction"){

      val txnId = call.argument<String>("transactionId")
      val cancelTxnId = call.argument<String>("cancelTxnId")
      if (txnId!=null && cancelTxnId!=null) {
        GlobalScope.launch(Dispatchers.IO) {
          try {
            result.success(true)
          } catch (e: Exception) {
            println(e.stackTraceToString())
          }
        }
      }
    }

    else {
      result.notImplemented()
    }
  }

  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }
}
