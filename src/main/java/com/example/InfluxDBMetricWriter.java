package com.example;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.springframework.boot.actuate.metrics.Metric;
import org.springframework.boot.actuate.metrics.writer.GaugeWriter;

import java.util.concurrent.TimeUnit;

/**
 * Created by amarendra on 26/01/17.
 */
public class InfluxDBMetricWriter implements GaugeWriter {

    private static final String DEFAULT_DATABASE_NAME = "metrics";
    private static final int DEFAULT_BATCH_ACTIONS = 500;
    private static final int DEFAULT_FLUSH_DURATION = 30;

    private final InfluxDB influxDB;
    private final String databaseName;

    private InfluxDBMetricWriter(Builder builder) {
        this.influxDB = builder.influxDB;
        this.databaseName = builder.databaseName;
        //this.influxDB.createDatabase( this.databaseName);
        this.influxDB.enableBatch( builder.batchActions, builder.flushDuration,
                builder.flushDurationTimeUnit);
        this.influxDB.setLogLevel( builder.logLevel);
    }

    @Override
    public void set(Metric<?> value) {
        Point point = Point.measurement( value.getName())
                .time( value.getTimestamp().getTime(), TimeUnit.MILLISECONDS)
                .addField( "value", value.getValue())
                .build();
        this.influxDB.write( this.databaseName, null, point);
    }

    @Accessors(fluent = true, chain = true)
    public static class Builder {
        @NonNull
        private final InfluxDB influxDB;
        private String databaseName = DEFAULT_DATABASE_NAME;
        private int batchActions = DEFAULT_BATCH_ACTIONS;
        private int flushDuration = DEFAULT_FLUSH_DURATION;
        private TimeUnit flushDurationTimeUnit = TimeUnit.SECONDS;
        private InfluxDB.LogLevel logLevel = InfluxDB.LogLevel.BASIC;

        public Builder(InfluxDB influxDB) {
            this.influxDB = influxDB;
        }

        public Builder flushDuration( int flushDuration, TimeUnit flushDurationTimeUnit) {
            this.flushDuration = flushDuration;
            this.flushDurationTimeUnit = flushDurationTimeUnit;
            return this;
        }

        public InfluxDBMetricWriter build() {
            return new InfluxDBMetricWriter(this);
        }

        public void databaseName(String dbName) {
            this.databaseName =dbName;
        }

        public void batchActions(int i) {
            this.batchActions = i;
        }
    }
}
