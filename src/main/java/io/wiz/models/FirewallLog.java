package io.wiz.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class FirewallLog {
    String domain;
    String srcIp;
    String dstIp;
    Direction direction;
}
