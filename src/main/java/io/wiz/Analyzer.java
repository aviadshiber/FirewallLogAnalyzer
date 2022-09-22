package io.wiz;

import io.wiz.filters.LogFilter;
import io.wiz.models.Direction;
import io.wiz.models.FirewallLog;
import io.wiz.repositores.ServiceRepository;
import io.wiz.services.DNSReverseLookupService;
import lombok.val;

import java.io.IOException;
import java.io.Writer;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class Analyzer implements Runnable {

    private final Map<String, Set<String>> serviceName2Ips;
    private final Stream<String> logReader;
    private final ServiceRepository serviceRepository;
    private final LogFilter filters;
    private final DNSReverseLookupService dnsLookup;
    private final Writer output;

    public Analyzer(Stream<String> logReader, ServiceRepository serviceRepository, LogFilter filters, DNSReverseLookupService dnsLookup, Writer output) {
        this.serviceName2Ips = new ConcurrentHashMap<>();
        this.logReader = logReader;
        this.serviceRepository = serviceRepository;
        this.filters = filters;
        this.dnsLookup = dnsLookup;
        this.output = output;
    }

    private static FirewallLog parseLog(String log) {
        //TODO: parse log to firewall log
        return new FirewallLog("domain", "srcIp", "dstIp", Direction.IN);
    }

    @Override
    public void run() {
        Stream<FirewallLog> filteredLogs = logReader.map(Analyzer::parseLog).filter(filters::filter);
        filteredLogs.forEach(this::updateLog);
        writeResults();
    }

    private void updateLog(FirewallLog log) {
        val ip = log.getDirection() == Direction.OUT ? log.getDstIp() : log.getSrcIp();
        val domain = log.getDomain().isEmpty() ? dnsLookup.getDomain(ip) : log.getDomain();
        val service = serviceRepository.findServiceByDomain(domain);
        val serviceName = service.getServiceName();
        if (serviceName != null) {
            Set<String> ips = serviceName2Ips.getOrDefault(serviceName, new HashSet<>());
            ips.add(ip);
            serviceName2Ips.put(serviceName, ips);
        }
    }

    private void writeResults() {
        for (Map.Entry<String, Set<String>> entry : serviceName2Ips.entrySet()) {
            val serviceName = entry.getKey();
            val ips = entry.getValue();
            try {
                val out = String.format("%s %s", serviceName, ips.toString());
                output.write(out);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                //todo log exception
            }
        }
    }
}
