package com.operantic.contactqueryservice.query.controllers;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.operantic.contactqueryservice.query.queries.GetContactAllQuery;
import com.operantic.coreapi.coreapi.shared.ObjectPerPage;
import com.operantic.coreapi.coreapi.utilisateur.AppConstants;
import com.operantic.coreapi.coreapi.utilisateur.PostResponse;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.*;

import com.operantic.contactqueryservice.query.dto.ContactDTO;
import com.operantic.contactqueryservice.query.queries.GetContactByIdQuery;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/api/query/")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class ContactQueryRestAPI {
	private QueryGateway gateway;

	@GetMapping("{contactId}")
	public CompletableFuture<ContactDTO> getContact(@PathVariable String contactId) {
		CompletableFuture<ContactDTO> query = gateway.query(new GetContactByIdQuery(contactId),
				ContactDTO.class);
		return query;
	}

	@GetMapping("contacts")
	public CompletableFuture<List<ContactDTO>> getContacts() {
		CompletableFuture<List<ContactDTO>> query = gateway.query(new GetContactAllQuery(), ResponseTypes.multipleInstancesOf(ContactDTO.class));
		return query;
	}
	@GetMapping("pageContacts")
	public CompletableFuture<PostResponse<ContactDTO>> getContacts(
			@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) Integer pageNo,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir

	) {

		int startIndex = (pageNo - 1) * pageSize;
		GetContactAllQuery query = new GetContactAllQuery(startIndex,pageSize,sortBy, sortDir);
		CompletableFuture<List<ContactDTO>> result = gateway.query(
				query,
				ResponseTypes.multipleInstancesOf(ContactDTO.class));
		return result.thenApply(contacts -> {
			// Construire l'objet PostResponse en utilisant les informations de pagination
			PostResponse<ContactDTO> postResponse = new PostResponse<>();
			postResponse.setContent(contacts);
			postResponse.setPageNo(pageNo);
			postResponse.setPageSize(pageSize);
			postResponse.setTotalElements((long) contacts.size()); // Vous devrez remplacer cela par le nombre total d'éléments dans votre source de données
			postResponse.setTotalPages((int) Math.ceil((double) postResponse.getTotalElements() / pageSize));
			postResponse.setLast(postResponse.getPageNo() == postResponse.getTotalPages());
			return postResponse;
		});
	}

}
