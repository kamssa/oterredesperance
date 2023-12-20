package com.operantic.projetqueryservice.query.controllers;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.operantic.coreapi.coreapi.s3.ImageUplaodService;
import com.operantic.coreapi.coreapi.shared.ObjectPerPage;
import com.operantic.coreapi.coreapi.utilisateur.AppConstants;
import com.operantic.coreapi.coreapi.utilisateur.PostResponse;
import com.operantic.projetqueryservice.query.document.Projet;
import com.operantic.projetqueryservice.query.queries.GetProjectAllQuery;
import com.operantic.projetqueryservice.query.repository.ProjetRepository;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.operantic.projetqueryservice.query.dto.ProjetDTO;
import com.operantic.projetqueryservice.query.queries.GetProjetByIdQuery;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/api/query/")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class ProjetQueryRestAPI {
	private QueryGateway gateway;
	private ImageUplaodService imageUplaodService;
	private ProjetRepository projetRepository;

	@GetMapping("{projetId}")
	public CompletableFuture<ProjetDTO> getProjet(@PathVariable String projetId) {
		CompletableFuture<ProjetDTO> query = gateway.query(new GetProjetByIdQuery(projetId),
				ProjetDTO.class);
		return query;
	}

	@GetMapping("projets")
	public CompletableFuture<List<ProjetDTO>> getListProject() {
		CompletableFuture<List<ProjetDTO>> query = gateway.query(new GetProjectAllQuery(), ResponseTypes.multipleInstancesOf(ProjetDTO.class));
		return query;
	}
	// getAll avec pagination
	@GetMapping("pageProjets")
	public CompletableFuture<PostResponse<ProjetDTO>> getListProject(
			@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) Integer pageNo,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = "", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "", required = false) String sortDir
	) {

		// Calculer l'index de début pour la pagination
		int startIndex = (pageNo - 1) * pageSize;

		GetProjectAllQuery query = new GetProjectAllQuery(startIndex, pageSize, sortBy, sortDir);

		CompletableFuture<List<ProjetDTO>> result = gateway.query(
				query,
				ResponseTypes.multipleInstancesOf(ProjetDTO.class)
		);

		return result.thenApply(projets -> {
			// Construire l'objet PostResponse en utilisant les informations de pagination
			PostResponse<ProjetDTO> postResponse = new PostResponse<>();
			postResponse.setContent(projets);
			postResponse.setPageNo(pageNo);
			postResponse.setPageSize(pageSize);
			postResponse.setTotalElements((long) projets.size()); // Vous devrez remplacer cela par le nombre total d'éléments dans votre source de données
			postResponse.setTotalPages((int) Math.ceil((double) postResponse.getTotalElements() / pageSize));
			postResponse.setLast(postResponse.getPageNo() == postResponse.getTotalPages());
			return postResponse;
		});
	}

	// obtenir une image du projet
	@GetMapping(value = "image/{projetId}", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<byte[]> getProjetImage(@PathVariable("projetId") String projetId) {
		Projet projet = projetRepository.findById(projetId).orElse(null);

		if (projet == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		String folder = "projet-images/%s/%s";
		byte[] imageData = imageUplaodService.getImage(projetId, projet.getCover(), folder);

		if (imageData != null && imageData.length > 0) {
			return ResponseEntity.ok().body(imageData);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	//obtenir toutes les images d'un projet
	@GetMapping(value = "images/{projetId}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void getAllProjetImage(@PathVariable("projetId") String projetId) {

	 	// imageUplaodService.listBucketObjects("fs.oterredesperance-projet");
	}

}
