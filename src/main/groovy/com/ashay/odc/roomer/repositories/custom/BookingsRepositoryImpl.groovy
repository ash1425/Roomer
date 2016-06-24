package com.ashay.odc.roomer.repositories.custom

import com.ashay.odc.roomer.domain.Booking
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate

import static org.springframework.data.mongodb.core.query.Criteria.where
import static org.springframework.data.mongodb.core.query.Query.query

class BookingsRepositoryImpl implements BookingsRepositoryCustom{

    private final MongoTemplate mongoTemplate

    @Autowired
    BookingsRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate
    }

    @Override
    public List<Booking> findExistingBookings(String room, Date startTime, Date endTime) {
        return mongoTemplate.find(query(where("room").is(room)
                .orOperator(
                where("startTime").lte(startTime).and("endTime").gt(startTime),
                where("startTime").lt(endTime).and("endTime").gte(endTime))), Booking)
    }
}
