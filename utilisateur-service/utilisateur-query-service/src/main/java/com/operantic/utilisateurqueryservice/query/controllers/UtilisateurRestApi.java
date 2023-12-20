package com.operantic.utilisateurqueryservice.query.controllers;

import com.operantic.coreapi.UserData;
import com.operantic.coreapi.coreapi.s3.ImageUplaodService;
import com.operantic.coreapi.coreapi.shared.ObjectPerPage;
import com.operantic.coreapi.coreapi.utilisateur.AppConstants;
import com.operantic.coreapi.coreapi.utilisateur.PostResponse;
import com.operantic.coreapi.coreapi.utilisateur.UtilisateurDTO;
import com.operantic.utilisateurqueryservice.query.document.Utilisateur;
import com.operantic.utilisateurqueryservice.query.dto.UtilisateurDto;
import com.operantic.utilisateurqueryservice.query.queries.GetUtilisateurAllQuery;
import com.operantic.utilisateurqueryservice.query.queries.GetUtilisateurByIdQuery;
import com.operantic.utilisateurqueryservice.query.repository.UtilisateurRepository;
import lombok.AllArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "/api/query/")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class UtilisateurRestApi {
	private QueryGateway gateway;
	private UtilisateurRepository utilisateurRepository;
	private ImageUplaodService imageUplaodService;

	@GetMapping("{userId}")
	public CompletableFuture<UtilisateurDto> getUserbyId(@PathVariable String userId) {
		CompletableFuture<UtilisateurDto> query = gateway.query(new GetUtilisateurByIdQuery(userId),
				UtilisateurDto.class);
		return query;
	}

	@GetMapping("users")
	public CompletableFuture<List<UtilisateurDto>> getUsers() {
		return gateway.query(new GetUtilisateurAllQuery(), ResponseTypes.multipleInstancesOf(UtilisateurDto.class));
	}

	@GetMapping("pageUtilisateur")
	public CompletableFuture<PostResponse<UtilisateurDto>> getContacts(
			@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) Integer pageNo,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir

	) {

		int startIndex = (pageNo - 1) * pageSize;
		GetUtilisateurAllQuery query = new GetUtilisateurAllQuery(startIndex, pageSize, sortBy, sortDir);
		CompletableFuture<List<UtilisateurDto>> result = gateway.query(
				query,
				ResponseTypes.multipleInstancesOf(UtilisateurDto.class));
		return result.thenApply(utilisateurs -> {
			// Construire l'objet PostResponse en utilisant les informations de pagination
			PostResponse<UtilisateurDto> postResponse = new PostResponse<>();
			postResponse.setContent(utilisateurs);
			postResponse.setPageNo(pageNo);
			postResponse.setPageSize(pageSize);
			postResponse.setTotalElements((long) utilisateurs.size()); // Vous devrez remplacer cela par le nombre total d'éléments dans votre source de données
			postResponse.setTotalPages((int) Math.ceil((double) postResponse.getTotalElements() / pageSize));
			postResponse.setLast(postResponse.getPageNo() == postResponse.getTotalPages());
			return postResponse;
		});
	}

	@GetMapping(value = "image/{userId}", produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getProjetImage(@PathVariable("projetId") String userId) {
		Utilisateur utilisateur = utilisateurRepository.findById(userId).get();
		String folder ="user-images/%s/%s";
		return imageUplaodService.getImage(userId, utilisateur.getProfilImage(), folder);
	}

	@GetMapping("/get-user-by-username")
	public ResponseEntity<UserData> getUserByUsername(@RequestParam(value = "username") String username) {
		Utilisateur utilisateurDto = utilisateurRepository.findUtilisateurByUsername(username);

		// Vérifiez si l'utilisateur a été trouvé
		if (utilisateurDto != null) {
			// Convertissez l'utilisateur en UserData et renvoyez la réponse
			UserData userData = convertUserInUserDTO(utilisateurDto);
			return new ResponseEntity<>(userData, HttpStatus.OK);
		} else {
			// Si l'utilisateur n'existe pas, renvoyez une réponse 404 (Not Found)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("verify-password")
	public ResponseEntity<UserData> verifyResetPassword(
			@RequestParam(value ="email") String email,
			@RequestParam(value = "code") String code) {
		Utilisateur utilisateurDto = utilisateurRepository.findUtilisateurByEmail(email);
		System.out.println("utilisateurDto ==> " + utilisateurDto.getEmail());
		// Vérifiez si l'utilisateur a été trouvé
		if (utilisateurDto != null) {
			if (utilisateurDto.getResetPassword().equals(code)) {
				// Convertissez l'utilisateur en UserData et renvoyez la réponse
				UserData userData = convertUserInUserDTO(utilisateurDto);
				return new ResponseEntity<>(userData, HttpStatus.OK);
			}else{
				return new ResponseEntity<>(HttpStatus.OK);
			}
		} else {
			// Si l'utilisateur n'existe pas, renvoyez une réponse 404 (Not Found)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	private UserData convertUserInUserDTO(Utilisateur utilisateur){
        return UserData.builder()
				.id(utilisateur.getId())
				.nom(utilisateur.getNom())
				.prenom(utilisateur.getPrenom())
				.email(utilisateur.getEmail())
				.telephone(utilisateur.getTelephone())
				.username(utilisateur.getUsername())
				.resetPassword(utilisateur.getResetPassword())
				.build();
	}
}
