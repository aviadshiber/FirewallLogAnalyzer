package io.wiz;

import com.opencsv.exceptions.CsvException;
import io.wiz.filters.*;
import io.wiz.repositores.ServiceCSVRepository;
import io.wiz.repositores.ServiceRepository;
import io.wiz.services.DNSReverseLookupService;
import io.wiz.services.DNSReverseLookupServiceCache;
import io.wiz.services.DNSReverseLookupServiceJava;
import lombok.Cleanup;
import lombok.val;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws IOException, CsvException, URISyntaxException {
        URL firewallLogUrl = Main.class.getClassLoader().getResource("firewall.log");
        URL serviceDbUrl = Main.class.getClassLoader().getResource("ServiceDBv1.csv");
        assert firewallLogUrl != null;
        assert serviceDbUrl != null;

        val logPath = Paths.get(firewallLogUrl.toURI());
        val serviceDBFile = Paths.get(serviceDbUrl.toURI()).toFile();
        @Cleanup Stream<String> lines = Files.lines(logPath);
        ServiceRepository serviceRepository = new ServiceCSVRepository(new BufferedReader(new FileReader(serviceDBFile)));
        LogFilter filters = new CompositeLogFilter(new IncludeIPFilter("1.1.1.1/24"), new UserIncludeFilter("a?"));
        DNSReverseLookupService dnsLookup = new DNSReverseLookupServiceCache(new DNSReverseLookupServiceJava());
        Writer writer = new PrintWriter(System.out);
        Analyzer analyzer = new Analyzer(lines, serviceRepository, filters, dnsLookup, writer);
        analyzer.run();
    }


}