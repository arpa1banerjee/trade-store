package com.db.tradestore.service;

import com.db.tradestore.dto.TradeDTO;
import com.db.tradestore.entity.Trade;
import com.db.tradestore.exception.InvalidTradeException;
import com.db.tradestore.repository.TradeRepository;
import com.db.tradestore.util.TestDataUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class TradeServiceTest {

    private static TestDataUtil testDataUtil;

    @Mock
    TradeRepository repository;

    @InjectMocks
    TradeService tradeService;

    @BeforeAll
    static void setup() {
        testDataUtil = new TestDataUtil();
    }

    @Test
    public void testTradeValidateAndStore_successful() throws InvalidTradeException {
        TradeDTO tradeDTO = testDataUtil.createTradeDTO("T1", "B1", 1, "CP-1", testDataUtil.currentDate());
        Trade trade = testDataUtil.createTrade("T1", "B1", 1, "CP-1", testDataUtil.currentDate());

        when(repository.findById(tradeDTO.getId())).thenReturn(Optional.of(trade));

        tradeService.trade(tradeDTO);

        assertTrue(repository.findById(tradeDTO.getId()).isPresent());

        assertEquals("T1", repository.findById(tradeDTO.getId()).get().getTradeId());
    }

    @Test
    public void testTradeValidateAndStore_before_maturity() {
        TradeDTO tradeDTO = testDataUtil.createTradeDTO("T1", "B1", 1, "CP-1", testDataUtil.yesterdayDate());
        Trade trade = testDataUtil.createTrade("T1", "B1", 1, "CP-1", testDataUtil.yesterdayDate());

        when(repository.findById(tradeDTO.getId())).thenReturn(Optional.of(trade));

        assertThrows(InvalidTradeException.class, () -> tradeService.trade(tradeDTO));

    }

    @Test
    public void testTradeValidateAndStore_lower_version() {
        TradeDTO tradeDTO = testDataUtil.createTradeDTO("T1", "B1", 1, "CP-1", testDataUtil.currentDate());
        Trade trade = testDataUtil.createTrade("T1", "B1", 2, "CP-1", testDataUtil.currentDate());

        when(repository.findById(tradeDTO.getId())).thenReturn(Optional.of(trade));

        assertThrows(InvalidTradeException.class, () -> tradeService.trade(tradeDTO));

    }
}

