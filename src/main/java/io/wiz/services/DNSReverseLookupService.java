package io.wiz.services;

/**
 * Given an ip address will return the domain
 */
public interface DNSReverseLookupService {
    String getDomain(String ipaddr);
}
