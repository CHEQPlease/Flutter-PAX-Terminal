import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:flutter_pax_terminal/data/pax_terminal_config.dart';
import 'package:flutter_pax_terminal/flutter_pax_terminal.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {


  @override
  void initState() {
    super.initState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body:  Center(
          child: ElevatedButton(onPressed: () {

            FlutterPAX.init(PaxTerminalConfig(destinationIP: "192.168.31.7", destinationPort: "10009", timeout: "5000", connectionType: "TCP", terminalId: "testterminal"));
            FlutterPAX.authorizeTransaction(amount: 5000, transactionId: "sdfgsgf435", currency: "USD",cardType : "CREDIT");

          },
          child: Text("Send Terminal Request"),),
        )
      ),
    );
  }
}
