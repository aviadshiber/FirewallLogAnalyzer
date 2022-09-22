package io.wiz.services;

import lombok.var;

import java.util.HashMap;
import java.util.Map;

/**
 * Cached DNSReverseLookupService implementation,
 * using the proxy pattern
 */
public class DNSReverseLookupServiceCache implements DNSReverseLookupService {
    private final DNSReverseLookupService delegated;
    Map<String,String> ip2domain;
    public DNSReverseLookupServiceCache(DNSReverseLookupService dnsLookupService) {
        this.delegated = dnsLookupService;
        ip2domain = new HashMap<>();
    }

    @Override
    public String getDomain(String ipaddr) {
        var domain = ip2domain.get(ipaddr);
        if(domain == null){
          domain = delegated.getDomain(ipaddr);
          ip2domain.put(ipaddr, domain);
        }
        return domain;
    }
}
