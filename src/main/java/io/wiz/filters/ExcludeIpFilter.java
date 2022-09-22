package io.wiz.filters;

import io.wiz.models.FirewallLog;

public class ExcludeIpFilter extends IncludeIPFilter {

    public ExcludeIpFilter(String networkIp) {
        super(networkIp);
    }
    @Override
    public boolean filter(FirewallLog log) {
        return !super.filter(log);
    }

}
