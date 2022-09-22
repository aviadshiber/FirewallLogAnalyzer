package io.wiz.models;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ServiceEntity {
    String serviceName;
    String domain;
    String Risk;
    String Country;
    boolean GDPR;
}
