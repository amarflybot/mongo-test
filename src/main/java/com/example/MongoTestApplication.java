package com.example;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.ExportMetricWriter;
import org.springframework.boot.actuate.endpoint.MetricsEndpoint;
import org.springframework.boot.actuate.endpoint.MetricsEndpointMetricReader;
import org.springframework.boot.actuate.metrics.writer.GaugeWriter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@SpringBootApplication
public class MongoTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(MongoTestApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(PersonRepository personRepository){
		return strings -> {
            personRepository.deleteAll();
            Arrays.asList("Amar","Alka","Deepak","Nagendra","Amarendra"
                    ,"Shailendra","Olu","Vicky","Ashish","Nicky")
                    .forEach(name-> {
                        Address address = new Address(getRandomCity());
                        address.setId(UUID.randomUUID().toString());
                        address.addHouseNumber(getRandomNumber());
                        address.addHouseNumber(getRandomNumber());
                        personRepository.save(new Person(name, address));
                    });
            personRepository.findAll().forEach(System.out::println);
        };
	}

	static String getRandomCity(){
		List<String> strings = Arrays.asList("Nagpur", "Jamshedpur", "Surat", "Patna", "Bangalore", "Mumbai");
		Random random = new Random();
		return strings.get(random.nextInt(strings.size()));
	}

	static String getRandomNumber() {
		List<String> strings = Arrays.asList("H1", "H2", "H3", "H4", "H5", "H6");
		Random random = new Random();
		return strings.get(random.nextInt(strings.size()));
	}

	@Bean
	@ExportMetricWriter
	GaugeWriter influxMetricsWriter() {
		InfluxDB influxDB = InfluxDBFactory.connect( "http://192.168.2.7:8086",
				"root",
				"root");
		String dbName = "myMetricsDB";	// the name of the datastore you choose
		//influxDB.createDatabase( dbName);
		InfluxDBMetricWriter.Builder builder = new InfluxDBMetricWriter.Builder(influxDB);
		builder.databaseName( dbName);
		builder.batchActions( 50);	// number of points for batch before data is sent to Influx
		return builder.build();
	}

	@Bean
	public MetricsEndpointMetricReader metricsEndpointMetricReader(MetricsEndpoint metricsEndpoint) {
		return new MetricsEndpointMetricReader(metricsEndpoint);
	}
}
