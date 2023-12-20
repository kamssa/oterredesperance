package com.oprantic.donqueryservice.query.document;

import com.operantic.coreapi.ProjetData;
import com.operantic.coreapi.UserData;
import com.operantic.coreapi.coreapi.don.enumeted.DonStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Don {

    @MongoId
    private String id;
    private UserData userData;
    private ProjetData projetData;
    private LocalDate localDate;
    @Enumerated(EnumType.STRING)
    private DonStatus status;
}
