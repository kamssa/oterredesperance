package com.operantic.coreapi.coreapi.shared;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ObjectPerPage {
    int pageNo;
    int pageSize;
    String sortBy;
    String sortDir;
}
