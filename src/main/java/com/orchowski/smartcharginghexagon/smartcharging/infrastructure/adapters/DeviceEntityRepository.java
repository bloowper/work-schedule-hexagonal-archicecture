package com.orchowski.smartcharginghexagon.smartcharging.infrastructure.adapters;


import org.springframework.data.mongodb.repository.MongoRepository;

//[QUESTION] znowu to samo z publicznym interfacem :(, jest tylko dlatego że test behavioral znajduje sie w pakiecie domain
public interface DeviceEntityRepository extends MongoRepository<DeviceEntity,String> {
}
