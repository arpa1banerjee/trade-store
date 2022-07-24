package com.db.tradestore.controller;

import com.db.tradestore.dto.TradeDTO;
import com.db.tradestore.exception.InvalidTradeException;
import com.db.tradestore.service.TradeService;
import com.db.tradestore.util.TestDataUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class TradeControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static TestDataUtil testDataUtil;

    @MockBean
    private TradeService tradeService;

    @Autowired
    private MockMvc mockMvc;


    @BeforeAll
    static void setup() {
        testDataUtil = new TestDataUtil();
    }

    @Test
    void testTradeValidateAndStore_successful() throws Exception {
        mockMvc.perform(post("/trade-store/trade")
                .content(objectMapper.writeValueAsString(testDataUtil.createTradeDTO("T1","B1", 1, "CP-1", testDataUtil.currentDate())))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


    }

    @Test
    void testTradeValidateAndStore_exception_invalid_trade() throws Exception {

        TradeDTO tradeDTO = testDataUtil.createTradeDTO("T1", "B1", 1, "CP-1", testDataUtil.yesterdayDate());

        doThrow(new InvalidTradeException("T1 Trade is not valid.")).when(tradeService).trade(tradeDTO);

        mockMvc.perform(post("/trade-store/trade")
                        .content(objectMapper.writeValueAsString(tradeDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotAcceptable())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidTradeException))
                .andExpect(result -> assertEquals("T1 Trade is not valid.", Objects.requireNonNull(result.getResolvedException()).getMessage()));


    }

}
