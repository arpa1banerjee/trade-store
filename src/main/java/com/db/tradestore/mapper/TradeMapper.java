package com.db.tradestore.mapper;

import com.db.tradestore.dto.TradeDTO;
import com.db.tradestore.entity.Trade;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TradeMapper {

    TradeMapper INSTANCE = Mappers.getMapper( TradeMapper.class );

    @Mappings({
            @Mapping(target = "tradeId", source = "id"),
            @Mapping(target = "maturityDate", source = "maturityDate", dateFormat = "dd/MM/yyyy")
    })
    Trade tradeDTOtoTrade(TradeDTO dto);
}
