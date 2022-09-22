package io.wiz.repositores;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import io.wiz.models.ServiceEntity;
import lombok.Cleanup;
import lombok.val;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public class ServiceCSVRepository implements ServiceRepository {

    private final Map<String, ServiceEntity> domainToService;
    private final CSVParser csvParser;

    public ServiceCSVRepository(Reader reader) throws IOException, CsvException {
        csvParser = new CSVParserBuilder().withSeparator(',').build();
        domainToService = new HashMap<>();
        loadToMap(reader);
    }

    private void loadToMap(Reader r) throws IOException, CsvException {
        @Cleanup CSVReader reader = new CSVReaderBuilder(r).withCSVParser(csvParser).withSkipLines(1).build();
        val data = reader.readAll();
        for (String[] line : data) {
            val serviceName = line[0];
            val domain = line[1];
            val risk = line[2];
            val country = line[3];
            val gdpr = line[4].equalsIgnoreCase("YES");
            val serviceEntity = new ServiceEntity(serviceName, domain, risk, country, gdpr);
            domainToService.put(domain, serviceEntity);
        }


    }

    @Override
    public ServiceEntity findServiceByDomain(String domain) {
        return domainToService.get(domain);
    }
}
