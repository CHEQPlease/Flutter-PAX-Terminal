package com.cheqplease.pax_terminal.flutter_pax_terminal.data

data class PAXTerminalConfig(
    val destinationIP: String,
    val destinationPort : String,
    val terminalID : String,
    val timeout : String,
    val connectionType : String
)