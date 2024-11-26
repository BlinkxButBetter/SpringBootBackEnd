package io.b3.Controllers;

import io.b3.Models.Bid;
import io.b3.Services.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bids")
public class BidController {
    @Autowired
    private BidService bidService;

    @PostMapping("/place")
    public ResponseEntity<Bid> placeBid(@RequestBody Bid bid) {
        Bid savedBid = bidService.placeBid(bid);
        return ResponseEntity.ok(savedBid);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Bid>> getBidsByProduct(@PathVariable String productId) {
        List<Bid> bids = bidService.getBidsByProductId(productId);
        return ResponseEntity.ok(bids);
    }

    @GetMapping("/bidder/{bidderId}")
    public ResponseEntity<List<Bid>> getBidsByBidder(@PathVariable String bidderId) {
        List<Bid> bids = bidService.getBidsByBidderId(bidderId);
        return ResponseEntity.ok(bids);
    }
}
