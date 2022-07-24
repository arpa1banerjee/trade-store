package com.db.tradestore.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "TradeStore")
@Getter
@Setter
public class Trade {

    @Id
    private String tradeId;

    private int version;

    private String counterPartyId;

    private String bookId;

    private LocalDate maturityDate;

    private LocalDate createdDate;

    private String expiredFlag;
}
