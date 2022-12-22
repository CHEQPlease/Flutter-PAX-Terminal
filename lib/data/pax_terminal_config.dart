class PaxTerminalConfig {
  PaxTerminalConfig({
      required this.destinationIP,
      required this.destinationPort,
      required this.timeout,
      required this.terminalId,});

  PaxTerminalConfig.fromJson(dynamic json) {
    destinationIP = json['destinationIP'];
    destinationPort = json['destinationPort'];
    timeout = json['timeout'];
    terminalId = json['terminalId'];
  }
  late String destinationIP;
  late String destinationPort;
  late String timeout;
  late String terminalId;

  Map<String, dynamic> toJson() {
    final map = <String, dynamic>{};
    map['destinationIP'] = destinationIP;
    map['destinationPort'] = destinationPort;
    map['timeout'] = timeout;
    map['terminalId'] = terminalId;
    return map;
  }

}