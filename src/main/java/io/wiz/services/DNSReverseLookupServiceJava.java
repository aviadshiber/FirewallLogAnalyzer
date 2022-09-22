package io.wiz.services;

import lombok.SneakyThrows;

import java.net.InetAddress;

public class DNSReverseLookupServiceJava implements DNSReverseLookupService
{

    @Override
    @SneakyThrows //TODO handle failures
    public String getDomain(String ipaddr) {

        return InetAddress.getByName(ipaddr).getCanonicalHostName();
    }
}
