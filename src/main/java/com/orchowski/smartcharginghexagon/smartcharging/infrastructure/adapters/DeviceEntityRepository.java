package com.orchowski.smartcharginghexagon.smartcharging.infrastructure.adapters;


import org.springframework.data.mongodb.repository.MongoRepository;

interface DeviceEntityRepository extends MongoRepository<DeviceEntity,String> {
}
