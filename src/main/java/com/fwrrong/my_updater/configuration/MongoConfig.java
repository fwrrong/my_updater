package com.fwrrong.my_updater.configuration;
import org.bson.types.Binary;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.UUID;

@Configuration
public class MongoConfig {

    @Bean
    public MongoCustomConversions customConversions() {
        return new MongoCustomConversions(Arrays.asList(new UUIDToBinaryConverter(), new BinaryToUUIDConverter()));
    }

    @WritingConverter
    public static class UUIDToBinaryConverter implements Converter<UUID, Binary> {
        @Override
        public Binary convert(UUID source) {
            ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
            bb.putLong(source.getMostSignificantBits());
            bb.putLong(source.getLeastSignificantBits());
            return new Binary(bb.array());
        }
    }

    @ReadingConverter
    public static class BinaryToUUIDConverter implements Converter<Binary, UUID> {
        @Override
        public UUID convert(Binary source) {
            ByteBuffer bb = ByteBuffer.wrap(source.getData());
            long firstLong = bb.getLong();
            long secondLong = bb.getLong();
            return new UUID(firstLong, secondLong);
        }
    }
}
