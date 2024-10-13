package io.b3.Services;

import io.b3.Models.Bid;
import io.b3.Repositories.BidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BidService {
    @Autowired
    private BidRepository bidRepository;

    public Bid placeBid(Bid bid) {
        return bidRepository.save(bid);
    }

    public List<Bid> getBidsByProductId(String productId) {
        return bidRepository.findByProductId(productId);
    }

    public List<Bid> getBidsByBidderId(String bidderId) {
        return bidRepository.findByBidderId(bidderId);
    }
}
