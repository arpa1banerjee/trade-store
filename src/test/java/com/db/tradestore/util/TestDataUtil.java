package com.db.tradestore.util;

import com.db.tradestore.dto.TradeDTO;
import com.db.tradestore.entity.Trade;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TestDataUtil {

    public TradeDTO createTradeDTO(String tradeId, String bookId, int version, String cptId, String maturityDate){
        TradeDTO trade = new TradeDTO();
        trade.setId(tradeId);
        trade.setBookId(bookId);
        trade.setVersion(version);
        trade.setCounterPartyId(cptId);
        trade.setMaturityDate(maturityDate);
        trade.setExpiredFlag("Y");
        return trade;
    }

    public Trade createTrade(String tradeId, String bookId, int version, String cptId, String maturityDate){
        Trade trade = new Trade();
        trade.setTradeId(tradeId);
        trade.setBookId(bookId);
        trade.setVersion(version);
        trade.setCounterPartyId(cptId);
        trade.setMaturityDate(LocalDate.parse(maturityDate, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        trade.setCreatedDate(LocalDate.now());
        trade.setExpiredFlag("Y");
        return trade;
    }

    public String currentDate() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public String yesterdayDate() {
        return LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}
