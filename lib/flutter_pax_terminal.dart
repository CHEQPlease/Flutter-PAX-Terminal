///*****************************
///Created by Niaz on 22 December 2022
///******************************


///********************************************************
/// Created by Niaz Ahmed on 22,December 2022 at 7:23 PM.
/// Email : n.ahmed@cheq.io
/// Copyright ©  CHEQ Lifestyle Technology Inc.
///*********************************************************


import 'dart:async';

import 'package:flutter/services.dart';
import 'package:flutter_pax_terminal/data/pax_terminal_config.dart';

import 'package:flutter/services.dart';

import 'data/enums.dart';

typedef OnSuccess<T> = Function(T response);
typedef OnFailure<T> = Function(T response);


class FlutterPAX {
  static const MethodChannel _channel = MethodChannel('com.cheqplease.pax_terminal/channel');
  static const String _methodInit = "init";
  static const String _methodAuthorizeTransaction = "authorize_transaction";
  static const String _methodCancelTransaction = "cancel_transaction";

  static late PaxTerminalConfig _terminalConfig;

  static void init(PaxTerminalConfig terminalConfig){
    _terminalConfig = terminalConfig;
    _channel.invokeMethod(_methodInit,_terminalConfig.toJson());
  }

  static Future<void> authorizeTransaction(
      {
        required double amount,
        required String transactionId,
        required String currency,
        required String cardType,
        CaptureType captureType =  CaptureType.delayed,
        OnSuccess<String>? onSuccess,
        OnFailure<String>? onFailure,
      }) async {

    _channel.invokeMethod(_methodAuthorizeTransaction,{
      "amount" : amount,
      "transactionId" : transactionId,
      "currency" : currency,
      "captureType" : captureType.name,
      "cardType" : cardType
    }).then((value){
      onSuccess!(value);
    }).catchError((value){
      print(value);
      onFailure!(value.details);
    });

  }

  static Future<dynamic> cancelTransaction({required txnId,required cancelTxnId, required terminalId}) async {

    var result = await _channel.invokeMethod(_methodCancelTransaction,{
      "transactionId" : txnId,
      "cancelTxnId" : cancelTxnId
    });

    return result;
  }

}
