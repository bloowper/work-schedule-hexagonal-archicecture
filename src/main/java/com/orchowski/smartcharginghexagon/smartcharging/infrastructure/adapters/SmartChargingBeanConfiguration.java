package com.orchowski.smartcharginghexagon.smartcharging.infrastructure.adapters;

import com.orchowski.smartcharginghexagon.smartcharging.domain.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SmartChargingBeanConfiguration {
    private final DeviceEntityRepository deviceEntityRepository;
    private final DeviceMapper deviceMapper;


    @Bean
    public ScheduleService scheduleService() {
        DevicePersistenceOutputPortImpl devicePersistenceOutputPort = new DevicePersistenceOutputPortImpl(deviceEntityRepository, deviceMapper);
        return new ScheduleService(devicePersistenceOutputPort);
    }

}
