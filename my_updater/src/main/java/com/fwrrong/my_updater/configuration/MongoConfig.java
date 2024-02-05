package com.fwrrong.my_updater.configuration;
import org.bson.BsonBinarySubType;
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
            System.out.println(("writer " +  source));
            ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
            bb.putLong(source.getMostSignificantBits());
            bb.putLong(source.getLeastSignificantBits());
            Binary binary = new Binary(BsonBinarySubType.UUID_STANDARD, bb.array());
            System.out.println(String.format("writer converted to binary %s", binary));
            return binary;
        }
    }

    @ReadingConverter
    public static class BinaryToUUIDConverter implements Converter<Binary, UUID> {
        @Override
        public UUID convert(Binary source) {
            System.out.println(source);
            ByteBuffer bb = ByteBuffer.wrap(source.getData());
            long firstLong = bb.getLong();
            long secondLong = bb.getLong();
            UUID uuid = new UUID(firstLong, secondLong);
            System.out.println(uuid);
            return uuid;
        }
    }
}
