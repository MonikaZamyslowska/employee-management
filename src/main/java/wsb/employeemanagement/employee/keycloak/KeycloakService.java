package wsb.employeemanagement.employee.keycloak;

import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.ClientRepresentation;
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
    @Value("${keycloak.credentials.secret}")
    private String clientSecret;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${custom.keycloak.realmMaster}")
    private String realmMaster;

    @Value("${custom.keycloak.clientId}")
    private String clientId;

    @Value("${keycloak.auth-server-url}")
    private String authUrl;

    @Value("${custom.keycloak.admin.name}")
    private String adminName;

    @Value("${custom.keycloak.admin.password}")
    private String adminPassword;

    @Value("${keycloak.resource}")
    private String resource;


    public boolean createUser(Employee employee) throws KeycloakException {
        boolean result;

        try {
            Keycloak keycloak = getKeycloakInstance();

            CredentialRepresentation credential = new CredentialRepresentation();
            credential.setType(CredentialRepresentation.PASSWORD);
            credential.setValue(employee.getPassword());
            credential.setTemporary(true);

            UserRepresentation user = new UserRepresentation();
            user.setEnabled(true);
            user.setUsername(employee.getUsername());
            user.setFirstName(employee.getFirstName());
            user.setLastName(employee.getLastName());
            user.setCredentials(Collections.singletonList(credential));

            RealmResource realmResource = keycloak.realm(realm);
            UsersResource usersResource = realmResource.users();
            ClientRepresentation clientRepresentation = realmResource.clients().findByClientId(resource).get(0);

            Response response = usersResource.create(user);
            String userId = CreatedResponseUtil.getCreatedId(response);
            UserResource userResource = usersResource.get(userId);

            List<RoleRepresentation> clientRoles = realmResource.clients().get(clientRepresentation.getId())
                    .roles().list();
            List<RoleRepresentation> newRoles = getKeycloakRoles(employee.getRoles(), clientRoles);
            userResource.roles().clientLevel(clientRepresentation.getId()).add(newRoles);

            result = true;
        } catch (ClientErrorException e) {
            throw new KeycloakClientException("User cannot be created. Internal keycloak client fault.");
        } catch (Exception e) {
            throw new KeycloakException("User cannot be created. Internal keycloak fault.");
        }

        return result;
    }

    public boolean updateUser(Employee employee) throws KeycloakException {
        boolean result = false;
        try {
            //get keycloak instance
            Keycloak keycloak = getKeycloakInstance();

            //get resources
            RealmResource realmResource = keycloak.realm(realm);
            UsersResource usersResource = realmResource.users();
            UserRepresentation user = findUserRepresentation(usersResource, employee.getUsername());
            UserResource userResource = usersResource.get(user.getId());
            ClientRepresentation clientRepresentation = realmResource.clients().findByClientId(resource).get(0);

            //update password
            if (employee.getPassword() != null && !employee.getPassword().isEmpty()) {
                CredentialRepresentation passwordCred = new CredentialRepresentation();
                passwordCred.setTemporary(false);
                passwordCred.setType(CredentialRepresentation.PASSWORD);
                passwordCred.setValue(employee.getPassword());
                userResource.resetPassword(passwordCred);
            }

            //update roles
            List<RoleRepresentation> clientRoles = realmResource.clients().get(clientRepresentation.getId())
                    .roles().list();
            List<RoleRepresentation> newRoles = getKeycloakRoles(employee.getRoles(), clientRoles);
            userResource.roles().clientLevel(clientRepresentation.getId())
                    .remove(userResource.roles().clientLevel(clientRepresentation.getId()).listAll());
            userResource.roles().clientLevel(clientRepresentation.getId()).add(newRoles);

            //update personal info
            UserRepresentation userRepresentation = userResource.toRepresentation();
            userRepresentation.setFirstName(employee.getFirstName());
            userRepresentation.setLastName(employee.getLastName());
            userResource.update(userRepresentation);

            result = true;
        } catch (ClientErrorException e) {
            throw new KeycloakClientException("User cannot be updated. Internal keycloak client fault.");
        } catch (Exception e) {
            throw new KeycloakException("User cannot be updated. Internal keycloak fault.");
        }
        return result;
    }

    public boolean deleteUser(Employee employee) throws KeycloakException {
        boolean result = false;
        try {
            //get keycloak instance
            Keycloak keycloak = getKeycloakInstance();

            //get resources
            RealmResource realmResource = keycloak.realm(realm);
            UsersResource usersResource = realmResource.users();

            UserRepresentation user = findUserRepresentation(usersResource, employee.getUsername());
            UserResource userResource = usersResource.get(user.getId());

            userResource.remove();

            result = true;
        } catch (ClientErrorException e) {
            throw new KeycloakClientException("User cannot be updated. Internal keycloak client fault.");
        } catch (Exception e) {
            throw new KeycloakException("User cannot be updated. Internal keycloak fault.");
        }
        return result;
    }

    private UserRepresentation findUserRepresentation(UsersResource usersResource, String username) {
        return usersResource.search(username, null, null)
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

    private Keycloak getKeycloakInstance() {
        return Keycloak.getInstance(
                authUrl,
                realmMaster,
                adminName,
                adminPassword,
                clientId);
    }
}
