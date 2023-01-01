package com.orchowski.smartcharginghexagon.workschedule.adapters;

import com.orchowski.smartcharginghexagon.workschedule.domain.WorkScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SmartChargingBeanConfiguration {//TODO [question] this is also public for sake of tests :/ is it ok?
    private final DeviceEntityRepository deviceEntityRepository;
    private final EntityMapper entityMapper;


    @Bean
    public WorkScheduleService scheduleService() {
        DevicePersistenceOutputAdapter devicePersistenceOutputPort = new DevicePersistenceOutputAdapter(deviceEntityRepository, entityMapper);
        return new WorkScheduleService(devicePersistenceOutputPort);
    }

}
