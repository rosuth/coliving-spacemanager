package com.colivingspacemanager.app.repository;

import com.colivingspacemanager.app.entity.ColivingSpace;
import com.colivingspacemanager.app.entity.SpaceStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ColivingSpaceRepository extends MongoRepository<ColivingSpace, String> {

    List<ColivingSpace> findBySpaceStatus(SpaceStatus spaceStatus);

    long countBySpaceStatusAndSpaceManagerCode(SpaceStatus spaceStatus, String spaceManagerCode);

    ColivingSpace findBySpaceCode(String spaceCode);

    long countBySpaceStatus(SpaceStatus spaceStatus);

    long countBySpaceManagerCode(String spaceManagerCode);

    long count();

}
