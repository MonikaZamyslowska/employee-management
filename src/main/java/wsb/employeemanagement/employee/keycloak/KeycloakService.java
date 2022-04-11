package wsb.employeemanagement.employee.keycloak;

import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import wsb.employeemanagement.employee.domain.Employee;
import wsb.employeemanagement.employee.domain.Role;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class KeycloakService {
    private static String FIRST_PASSWORD = "Password123!";

    @Value("${keycloak.credentials.secret}")
    private String clientSecret;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.resource}")
    private String clientId;

    @Value("${keycloak.auth-server-url}")
    private String authUrl;

    @Value("${custom.keycloak.admin.name}")
    private String adminName;

    @Value("${custom.keycloak.admin.password}")
    private String adminPassword;


    public boolean createUser(Employee employee) throws KeycloakException {
        boolean result;

        try {
            Keycloak keycloak = KeycloakBuilder.builder()
                    .serverUrl(authUrl)
                    .realm(realm)
                    .username(adminName)
                    .password(adminPassword)
                    .clientId(clientId)
                    .clientSecret(clientSecret)
                    .build();

            CredentialRepresentation credential = new CredentialRepresentation();
            credential.setType(CredentialRepresentation.PASSWORD);
            credential.setValue(FIRST_PASSWORD);
            credential.setTemporary(true);

            UserRepresentation user = new UserRepresentation();
            user.setEnabled(true);
            user.setUsername(employee.getUsername());
            user.setFirstName(employee.getFirstName());
            user.setLastName(employee.getLastName());
            user.setCredentials(Collections.singletonList(credential));

            RealmResource realmResource = keycloak.realm(realm);
            UsersResource usersResource = realmResource.users();

            Response response = usersResource.create(user);
            String userId = CreatedResponseUtil.getCreatedId(response);
            UserResource userResource = usersResource.get(userId);

            List<RoleRepresentation> realmRoles = realmResource.roles().list();
            List<RoleRepresentation> userRoles = getKeycloakRoles(employee.getRoles(), realmRoles);
            userResource.roles().realmLevel().add(userRoles);

            result = true;
        } catch (ClientErrorException e) {
            throw new KeycloakClientException("User cannot be created. Internal keycloak client fault.");
        } catch (Exception e) {
            throw new KeycloakException("User cannot be updated. Internal keycloak fault.");
        }

        return result;
    }


    private UserRepresentation findUserRepresentation(UsersResource usersResource, String username) {
        return usersResource.search(username)
                .stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username))
                .findFirst()
                .orElseThrow(() -> new KeycloakClientException("Updated user doesn't exist"));
    }

    protected List<RoleRepresentation> getKeycloakRoles(List<Role> roles, List<RoleRepresentation> roleRepresentations) {
        List<String> rolesAsString = roles.stream()
                .map(Role::getKeycloakRole)
                .collect(Collectors.toList());
        return roleRepresentations.stream()
                .filter(x -> rolesAsString.contains(x.getName()))
                .collect(Collectors.toList());
    }
}
