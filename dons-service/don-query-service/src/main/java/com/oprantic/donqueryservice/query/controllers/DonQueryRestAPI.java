package com.oprantic.donqueryservice.query.controllers;

import com.operantic.coreapi.coreapi.shared.ObjectPerPage;
import com.operantic.coreapi.coreapi.utilisateur.AppConstants;
import com.operantic.coreapi.coreapi.utilisateur.PostResponse;
import com.oprantic.donqueryservice.query.dto.DonDTO;
import com.oprantic.donqueryservice.query.queries.GetDonAllQuery;
import com.oprantic.donqueryservice.query.queries.GetDonByIdQuery;
import lombok.AllArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "/api/query/")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class DonQueryRestAPI {

    private QueryGateway gateway;

    @GetMapping("don/{donId}")
    public CompletableFuture<DonDTO> getDon(@PathVariable String donId){
        CompletableFuture<DonDTO> query = gateway.query(new GetDonByIdQuery(donId),
                DonDTO.class);
        return query;
    }

    @GetMapping("dons")
    public CompletableFuture<List<DonDTO>> getDons(){
        CompletableFuture<List<DonDTO>> query = gateway.query(new GetDonAllQuery(), ResponseTypes.multipleInstancesOf(DonDTO.class));
        return query;
    }
    @GetMapping("pageDon")
    public CompletableFuture<PostResponse> getContacts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir

    ) {
        CompletableFuture<PostResponse> query = gateway.query(
                new ObjectPerPage(pageNo,pageSize,sortBy, sortDir),
                PostResponse.class);
        return query;
    }
}