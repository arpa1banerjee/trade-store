package com.db.tradestore.controller;

import com.db.tradestore.dto.TradeDTO;
import com.db.tradestore.entity.Trade;
import com.db.tradestore.exception.InvalidTradeException;
import com.db.tradestore.service.TradeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/trade-store")
public class TradeController {

    private final TradeService tradeStoreService;

    public TradeController(TradeService tradeStoreService) {
        this.tradeStoreService = tradeStoreService;
    }

    @PostMapping("/trade")
    public ResponseEntity<String> trade(@Valid @RequestBody TradeDTO trade) throws InvalidTradeException {
        tradeStoreService.trade(trade);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
