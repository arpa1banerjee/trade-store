package com.db.tradestore.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TradeDTO {

    @NotBlank(message = "Trade Id can not be empty")
    private String id;

    private int version;

    private String counterPartyId;

    private String bookId;

    private String maturityDate;

    private String expiredFlag;

}
