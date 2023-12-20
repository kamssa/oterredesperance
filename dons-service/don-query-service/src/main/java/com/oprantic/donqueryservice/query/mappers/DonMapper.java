package com.oprantic.donqueryservice.query.mappers;

import com.oprantic.donqueryservice.query.document.Don;
import com.oprantic.donqueryservice.query.dto.DonDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DonMapper {

    DonDTO fromDon(Don don);

    Don fromDonDTO(DonDTO donDTO);

    List<DonDTO> fromListDon(List<Don> dons);

    List<Don> fromListDonDTO(List<DonDTO> donDTOs);
}
