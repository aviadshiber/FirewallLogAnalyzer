package io.wiz.filters;


import inet.ipaddr.IPAddressString;
import io.wiz.models.Direction;
import io.wiz.models.FirewallLog;

public class IncludeIPFilter implements LogFilter{

    private final String networkIp;
    public IncludeIPFilter(String networkIp) {
        this.networkIp = networkIp;
    }

    static boolean contains(String network, String address) {
        IPAddressString one = new IPAddressString(network);
        IPAddressString two = new IPAddressString(address);
        return one.contains(two);
    }

    @Override
    public boolean filter(FirewallLog log) {
        //example of how to write a filter
        return contains(networkIp,log.getDirection().equals(Direction.IN) ? log.getDstIp() : log.getSrcIp());
    }
}
