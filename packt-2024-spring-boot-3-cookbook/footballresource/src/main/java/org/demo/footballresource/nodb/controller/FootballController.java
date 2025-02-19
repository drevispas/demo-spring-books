package org.demo.footballresource.nodb.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.demo.footballresource.nodb.service.AuctionService;
import org.demo.footballresource.nodb.service.DataService;
import org.demo.footballresource.nodb.service.FileLoader;
import org.demo.footballresource.nodb.service.TradingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/football")
@RequiredArgsConstructor
@Slf4j
public class FootballController {

    private final FileLoader fileLoader;
    private final TradingService tradingService;
    private final DataService dataService;
    private final AuctionService auctionService;

    @GetMapping("/teams")
    public List<String> listTeams() {
        return fileLoader.getTeams();
    }

    @PostMapping("/teams")
    public String addTeam(@RequestBody String teamName) {
        return teamName + " added";
    }

    @PostMapping
    public int tradeCards(@RequestBody int orders) {
        return tradingService.tradeCards(orders);
    }

    @GetMapping("/ranking/{player}")
    public int getRanking(@PathVariable String player) {
        log.info("Getting ranking for player: {}", player);
        Random random = new Random();
        if (random.nextInt(100) > 97) {
            throw new RuntimeException("Not possible to get the ranking for player " + player
                    + " at this moment. Please try again later.");
        }
        return random.nextInt(1000);
    }

    @GetMapping("/stats/{player}")
    public String getPlayerStats(@PathVariable String player) {
        return dataService.getPlayerStats(player);
    }

    @PostMapping("/bid/{player}")
    public void addBid(@PathVariable String player, @RequestBody String bid) {
        log.info("player: {}, bid: {}", player, bid);
        auctionService.addBid(player, bid);
    }

    @PostMapping("/auction/{player}")
    public void addAuction(@PathVariable String player, @RequestBody String bid) {
        log.info("player: {}, bid: {}", player, bid);
        auctionService.addBidAOP(player, bid);
    }
}
