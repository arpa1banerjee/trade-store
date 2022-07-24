package com.db.tradestore.scheduler;

import com.db.tradestore.service.TradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TradeExpiryScheduler {

    private final TradeService tradeService;

    public TradeExpiryScheduler(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @Scheduled(cron = "0 0 0 * * *") // every day at midnight
    public void expireTrade() {
        log.info("checking for trades to expire");
        tradeService.expire();
    }
}
