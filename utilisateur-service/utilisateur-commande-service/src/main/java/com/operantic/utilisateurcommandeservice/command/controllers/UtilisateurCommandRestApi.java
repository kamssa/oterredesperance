package com.operantic.utilisateurcommandeservice.command.controllers;


import com.amazonaws.services.cognitoidp.model.*;
import com.operantic.coreapi.UserDTO;
import com.operantic.coreapi.UserFindByEmailDTO;
import com.operantic.coreapi.UserFindByIdDTO;
import com.operantic.coreapi.coreapi.s3.ImageUplaodService;
import com.operantic.coreapi.coreapi.utilisateur.UtilisateurDTO;
import com.operantic.utilisateurcommandeservice.command.commands.*;
import com.operantic.utilisateurcommandeservice.command.dto.CreateUtilisateurDTO;
import com.operantic.utilisateurcommandeservice.command.dto.UpdateUtilisateurDTO;
import com.operantic.utilisateurcommandeservice.command.exception.CustomException;
import com.operantic.utilisateurcommandeservice.command.payload.UserSignInRequest;
import com.operantic.utilisateurcommandeservice.command.payload.UserSignInResponse;
import com.operantic.utilisateurcommandeservice.command.service.CodeGenerationService;
import com.operantic.utilisateurcommandeservice.command.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.GenericEventMessage;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import org.springframework.beans.factory.annotation.Value;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import com.amazonaws.services.cognitoidp.model.AdminDeleteUserRequest;
import com.amazonaws.services.cognitoidp.model.AdminDeleteUserResult;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(path = "/api/commands/")
@Slf4j
@CrossOrigin(origins = "*")
//@RequiredArgsConstructor
//@AllArgsConstructor
public class UtilisateurCommandRestApi {

	@Autowired
	private EventBus eventBus;
	private UtilisateurDTO utilisateur;

	@Value(value = "${aws.cognito.userPoolId}")
	private String userPoolId;
	@Value(value = "${aws.cognito.clientId}")
	private String clientId;
	@Value(value = "${aws.cognito.clientSecret}")
	private String clientSecret;
	@Value("${aws.cognito.logoutUrl}")
	private String logoutUrl;
	@Value("${aws.cognito.logout.success.redirectUrl}")
	private String logoutRedirectUrl;

	@Autowired
	private AWSCognitoIdentityProvider cognitoClient;

	@Autowired
	private CommandGateway commandGateway;
	@Autowired
	private EventStore eventStore;
	@Autowired
	private ImageUplaodService imageUplaodService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private CodeGenerationService codeGenerationService;

	@Operation(summary = "operation permettant denregistrer un utilisateur dans la base", description = "Create")
	@PostMapping("create")
	public CompletableFuture<String> newContact(@RequestBody CreateUtilisateurDTO request) {
		log.info("CreateUtilisateurDTO => " + request.getEmail().toString());
		CompletableFuture<String> future = new CompletableFuture<>();
		try {

			AttributeType emailAttr = new AttributeType().withName("email").withValue(request.getEmail());
			AttributeType emailVerifiedAttr = new AttributeType().withName("email_verified").withValue("true");

			AdminCreateUserRequest userRequest =
					new AdminCreateUserRequest().withUserPoolId(userPoolId).withUsername(request.getUsername()).withTemporaryPassword(request.getPassword()).withUserAttributes(emailAttr, emailVerifiedAttr).withMessageAction(MessageActionType.SUPPRESS).withDesiredDeliveryMediums(DeliveryMediumType.EMAIL);

			AdminCreateUserResult createUserResult = cognitoClient.adminCreateUser(userRequest);

			System.out.println("User " + createUserResult.getUser().getUsername() + " is created. Status: " + createUserResult.getUser().getUserStatus());

			// Disable force change password during first login
			AdminSetUserPasswordRequest adminSetUserPasswordRequest =
					new AdminSetUserPasswordRequest().withUsername(request.getUsername()).withUserPoolId(userPoolId).withPassword(request.getPassword()).withPermanent(true);

			cognitoClient.adminSetUserPassword(adminSetUserPasswordRequest);

			try {
				// add user to group
				request.getRoles().stream().forEach(role -> {
					AdminAddUserToGroupRequest addUserToGroupRequest = new AdminAddUserToGroupRequest()
							.withGroupName(role)
							.withUserPoolId(userPoolId)
							.withUsername(request.getUsername());

					cognitoClient.adminAddUserToGroup(addUserToGroupRequest);
				});

				return commandGateway.send(new CreateUtilisateurCommand(
						UUID.randomUUID().toString(),
						request.getNom(),
						request.getPrenom(),
						request.getEmail(),
						request.getAdresse(),
						request.getTelephone(),
						request.getPassword(),
						request.getRoles(),
						request.getUsername()
				));

			} catch (com.amazonaws.services.cognitoidp.model.InvalidPasswordException e) {
				//throw new FailedAuthenticationException(String.format("Invalid parameter: %s", e.getErrorMessage()), e);
					future.complete(String.format("Invalid parameter: %s", e.getErrorMessage()));
				return future;
			}

		} catch (AWSCognitoIdentityProviderException e) {
				future.complete(e.getErrorMessage());
			return future;
		} catch (Exception e) {
				future.complete("Setting user password");
			return future;
		}
	}

	@PutMapping("update/{userId}")
	public CompletableFuture<String> updateUtilisateur(@PathVariable String userId, @RequestBody UpdateUtilisateurDTO request){
		return commandGateway.send(new UpdateUtilisateurCommand(
				userId,
				request.getNom(),
				request.getPrenom(),
				request.getEmail(),
				request.getAdresse(),
				request.getTelephone(),
				request.getProfilImage(),
				request.getPassword(),
				request.getRoles(),
				request.getUsername()
		));
	}

	@DeleteMapping("{userId}")
	public CompletableFuture<String> removeUtilisateur(@PathVariable String userId){
		eventBus.publish(GenericEventMessage.asEventMessage(new UserFindByIdDTO(userId)));
		// Effectuer la suppression de l'utilisateur
		AdminDeleteUserRequest deleteUserRequest = new AdminDeleteUserRequest()
				.withUsername(this.utilisateur.getUsername())
				.withUserPoolId(userPoolId);
		AdminDeleteUserResult deleteUserResponse = cognitoClient.adminDeleteUser(deleteUserRequest);
		//*/
		// Vérifier la réponse
		if (deleteUserResponse.getSdkHttpMetadata().getHttpStatusCode() == 200) {
			System.out.println("Utilisateur supprimé avec succès.");
			return commandGateway.send(new RemoveUtilisateurCommand(
					userId
			));
		} else {
			System.err.println("Erreur lors de la suppression de l'utilisateur: "
					+ deleteUserResponse.getSdkHttpMetadata().getHttpStatusCode());
			return CompletableFuture.completedFuture("Erreur lors de la suppression de l'utilisateur avec Cognito");
		}
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> exceptionHandler(Exception ex) {
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@GetMapping(path = "events/{utilisateurId}")
	public Stream utilisateurEvent(@PathVariable String utilisateurId) {
		return eventStore.readEvents(utilisateurId).asStream();
	}

	/*@PostMapping( value = "{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public CompletableFuture<String> uploadUserProfilImageImage(
			@PathVariable  String userId,
			@RequestParam("file") MultipartFile file)
	{
		String folder = "projet-images/%s/%s";
		String profileImageId = UUID.randomUUID().toString();
		imageUplaodService.uploadImage(userId, file, profileImageId,folder);
		log.info("update pour la modification  de image projet => " );
		return commandGateway.send(new UpdateProfileImageCommnad(
				userId,
				profileImageId));
	}*/

	@PostMapping(path = "sign-in")
	public @ResponseBody UserSignInResponse signIn(@RequestBody UserSignInRequest userSignInRequest) {

		String SECRET_HASH = "";
		UserSignInResponse userSignInResponse = new UserSignInResponse();
		try {
			String data = userSignInRequest.getUsername() + clientId;
			SecretKeySpec signingKey = new SecretKeySpec(clientSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
			Mac mac = Mac.getInstance("HmacSHA256");
			mac.init(signingKey);
			byte[] rawHmac = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
			SECRET_HASH = Base64.getEncoder().encodeToString(rawHmac);
		} catch (Exception e) {
			throw new RuntimeException("Failed to calculate SECRET_HASH: " + e.getMessage());
		}

		final Map<String, String> authParams = new HashMap<>();
		authParams.put("USERNAME", userSignInRequest.getUsername());
		authParams.put("PASSWORD", userSignInRequest.getPassword());
		authParams.put("SECRET_HASH", SECRET_HASH);

		final AdminInitiateAuthRequest authRequest = new AdminInitiateAuthRequest();
		authRequest.withAuthFlow(AuthFlowType.ADMIN_NO_SRP_AUTH).withClientId(clientId).withUserPoolId(userPoolId).withAuthParameters(authParams);

		try {
			AdminInitiateAuthResult result = cognitoClient.adminInitiateAuth(authRequest);

			AuthenticationResultType authenticationResult = null;

			if (result.getChallengeName() != null && !result.getChallengeName().isEmpty()) {

				System.out.println("Challenge Name is " + result.getChallengeName());

				if (result.getChallengeName().contentEquals("NEW_PASSWORD_REQUIRED")) {
					if (userSignInRequest.getPassword() == null) {
						throw new CustomException("User must change password " + result.getChallengeName());

					} else {

						final Map<String, String> challengeResponses = new HashMap<>();
						challengeResponses.put("USERNAME", userSignInRequest.getUsername());
						challengeResponses.put("PASSWORD", userSignInRequest.getPassword());
						// add new password
						challengeResponses.put("NEW_PASSWORD", userSignInRequest.getNewPassword());

						final AdminRespondToAuthChallengeRequest request =
								new AdminRespondToAuthChallengeRequest().withChallengeName(ChallengeNameType.NEW_PASSWORD_REQUIRED).withChallengeResponses(challengeResponses).withClientId(clientId).withUserPoolId(userPoolId).withSession(result.getSession());

						AdminRespondToAuthChallengeResult resultChallenge =
								cognitoClient.adminRespondToAuthChallenge(request);
						authenticationResult = resultChallenge.getAuthenticationResult();

						userSignInResponse.setAccessToken(authenticationResult.getAccessToken());
						userSignInResponse.setIdToken(authenticationResult.getIdToken());
						userSignInResponse.setRefreshToken(authenticationResult.getRefreshToken());
						userSignInResponse.setExpiresIn(authenticationResult.getExpiresIn());
						userSignInResponse.setTokenType(authenticationResult.getTokenType());
						userSignInResponse.setUsername(userSignInRequest.getUsername());

						eventBus.publish(GenericEventMessage.asEventMessage(new UtilisateurDTO(userSignInRequest.getUsername())));

					}

				} else {
					throw new CustomException("User has other challenge " + result.getChallengeName());
				}
			} else {

				System.out.println("User has no challenge");
				authenticationResult = result.getAuthenticationResult();

				userSignInResponse.setAccessToken(authenticationResult.getAccessToken());
				userSignInResponse.setIdToken(authenticationResult.getIdToken());
				userSignInResponse.setRefreshToken(authenticationResult.getRefreshToken());
				userSignInResponse.setExpiresIn(authenticationResult.getExpiresIn());
				userSignInResponse.setTokenType(authenticationResult.getTokenType());

				userSignInResponse.setUsername(userSignInRequest.getUsername());
				eventBus.publish(GenericEventMessage.asEventMessage(new UserDTO(userSignInRequest.getUsername())));
				userSignInResponse.setPrenom(this.utilisateur.getPrenom());
				userSignInResponse.setNom(this.utilisateur.getNom());
				userSignInResponse.setRoles(this.utilisateur.getRoles());
			}

		} catch (InvalidParameterException e) {
			//throw new CustomException(e.getErrorMessage());
				// Throw an exception if the utilisateur is null
				throw new UserNotFoundException("User with username or password" +  userSignInRequest.getUsername() + " not found");

		} catch (Exception e) {
			//throw new CustomException(e.getMessage());
			throw new UserNotFoundException("User with username or password" +  userSignInRequest.getUsername() + " not found");
		}
		//cognitoClient.shutdown();
		return userSignInResponse;

	}
	@GetMapping("logout")
	public String  logoutUser(){
		return UriComponentsBuilder
				.fromUri(URI.create(logoutUrl))
				.queryParam("client_id", clientId)
				.queryParam("logout_uri", logoutRedirectUrl)
				.encode(StandardCharsets.UTF_8)
				.build()
				.toUriString();
	}

	@PostMapping("update-password")
	public ResponseEntity<String> updateUserPassword(@RequestBody UserSignInRequest userSignInRequest) {
		eventBus.publish(GenericEventMessage.asEventMessage(new UserFindByEmailDTO(userSignInRequest.getEmail())));
		if(userSignInRequest.getEmail() ==null && userSignInRequest.getPassword()==null)
			throw new UserNotFoundException("username or newPassword not found");
		if(!doesUsernameExist(userPoolId,this.utilisateur.getUsername()))
			throw new UserNotFoundException("username  not found");
		AdminSetUserPasswordRequest request = new AdminSetUserPasswordRequest()
				.withUserPoolId(userPoolId)
				.withUsername(this.utilisateur.getUsername())
				.withPassword(userSignInRequest.getPassword())
				.withPermanent(true);

		try {
			AdminSetUserPasswordResult response = cognitoClient.adminSetUserPassword(request);
			// Gérez la réponse selon vos besoins
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			// Gérez les erreurs ici
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Erreur lors de la mise à jour du mot de passe de l'utilisateur : " + e.getMessage());
		}
	}

	@PostMapping("/doesUsernameexist")
	public boolean doesUsernameExit(@RequestParam String username){
		return doesUsernameExist(userPoolId,username);
	}

	public boolean doesUsernameExist(String userPoolId, String username) {
		AdminGetUserRequest request = new AdminGetUserRequest()
				.withUserPoolId(userPoolId)
				.withUsername(username);

		try {
			AdminGetUserResult result = cognitoClient.adminGetUser(request);
			// Aucune exception n'a été levée, ce qui signifie que l'utilisateur existe
			return true;
		} catch (UserNotFoundException e) {
			// L'exception UserNotFoundException est levée si l'utilisateur n'est pas trouvé
			return false;
		} catch (Exception e) {
			// Gérez d'autres exceptions si nécessaire
			throw new RuntimeException("Erreur lors de la vérification de l'existence de l'utilisateur dans Cognito", e);
		}
	}

	@PostMapping("send-email")
	public CompletableFuture<String> requestPasswordReset(@RequestBody UserSignInRequest user) {
		String resetCode = codeGenerationService.generateCode();

		// Enregistrez le code avec l'utilisateur (vous devrez probablement stocker le code en base de données)
		eventBus.publish(GenericEventMessage.asEventMessage(new UserFindByEmailDTO(user.getEmail())));
		String emailBody = "Votre code de réinitialisation de mot de passe est : " + resetCode;
		emailService.sendEmail(user.getEmail(), "Réinitialisation de mot de passe", emailBody);
		log.info("user ===> " + this.utilisateur.getId());
		return commandGateway.send(new ResetPasswordCommand(
				this.utilisateur.getId(),
				this.utilisateur.getEmail(),
				resetCode
		));
	}

	@EventHandler
	public void getUserByUsername(UtilisateurDTO utilisateur){
		this.utilisateur=utilisateur;
	}

}
