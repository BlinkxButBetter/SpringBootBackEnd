package io.b3.Repositories;

import io.b3.Models.Bid;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidRepository extends MongoRepository<Bid, String> {
    List<Bid> findByProductId(String productId);
    List<Bid> findByBidderId(String bidderId);
}
