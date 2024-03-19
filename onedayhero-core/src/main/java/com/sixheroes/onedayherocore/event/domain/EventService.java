package com.sixheroes.onedayherocore.event.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sixheroes.onedayherocore.notification.application.dto.AlarmPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventService<T> {

    private final ObjectMapper objectMapper;

    public void convertObjectToString(
        Events events,
        T entityData
    ) {
        try {
            var string = objectMapper.writeValueAsString(entityData);
            events.updateEntityData(string);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public T convertStringToObject(
        Events events
    ) {
        var entityData = events.getEntityData();
        var entityType = events.getEntityType();
        try {
            return (T) objectMapper.readValue(entityData, entityType.getName());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
