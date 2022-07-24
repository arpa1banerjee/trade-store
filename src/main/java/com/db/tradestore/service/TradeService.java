package com.db.tradestore.service;

import com.db.tradestore.entity.Trade;
import com.db.tradestore.exception.InvalidTradeException;
import com.db.tradestore.repository.TradeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

@Service
@Slf4j
public class TradeService {

    private final TradeRepository tradeStoreRepository;

    private final Predicate<Trade> maturityPredicate = trade -> !trade.getMaturityDate().isBefore(LocalDate.now());

    private final BiPredicate<Trade, Trade> versionPredicate = (current, existing) -> current.getVersion() >= existing.getVersion();

    public TradeService(TradeRepository tradeStoreRepository) {
        this.tradeStoreRepository = tradeStoreRepository;
    }

    public void trade(Trade trade) throws InvalidTradeException {
        if(isValid(trade)) {

            log.info("{} trade is valid and persisting it in store.", trade.getTradeId());

            trade.setCreatedDate(LocalDate.now());
            tradeStoreRepository.save(trade);

            log.info("successfully persisted trade in store.");

        } else {
            log.warn("{} trade is invalid.", trade.getTradeId());
            throw new InvalidTradeException(String.format("%s Trade is not valid.",trade.getTradeId()));
        }
    }

    private boolean isValid(Trade trade) {
        log.info("validating trade {}", trade.getTradeId());
        if(maturityPredicate.test(trade)) {
            Optional<Trade> optionalExistingTrade = tradeStoreRepository.findById(trade.getTradeId());
            return optionalExistingTrade.map(existingTrade -> versionPredicate.test(trade, existingTrade)).orElse(true);
        }
        return false;
    }

}
