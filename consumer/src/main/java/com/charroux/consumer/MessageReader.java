package com.charroux.consumer;

import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serde;

import java.util.Arrays;

@Component
public class MessageReader {

    private static final Serde<String> STRING_SERDE = Serdes.String();
    Logger logger = LoggerFactory.getLogger(MessageReader.class);

    @Autowired
    void buildPipeline(StreamsBuilder streamsBuilder) {
        KStream<String, String> messageStream = streamsBuilder
                .stream("my-topic", Consumed.with(STRING_SERDE, STRING_SERDE));

        logger.info("-------------------------------\n\nStream\n\n------------------");

        KTable<String, Long> wordCounts = messageStream
                .mapValues((ValueMapper<String, String>) String::toLowerCase)
                .flatMapValues(value -> Arrays.asList(value.split("\\W+")))
                .groupBy((key, word) -> word, Grouped.with(STRING_SERDE, STRING_SERDE))
                .count();

        String n = wordCounts.toStream().toString();
        logger.info("-------------------------------\n\nWorld count\n\n------------------" + n);

    }

}
