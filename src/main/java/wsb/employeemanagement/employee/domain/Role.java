package wsb.employeemanagement.employee.domain;

public enum Role {
    EMPLOYEE("ROLE_EMPLOYEE", "ROLE_EMPLOYEE"),
    PM("ROLE_PM","ROLE_PM"),
    ADMIN("ROLE_ADMIN","ROLE_ADMIN");

    private final String abbreviation;
    private final String keycloakRole;

    Role(String abbreviation, String keycloakRole){
        this.keycloakRole = keycloakRole;
        this.abbreviation = abbreviation;
    }

    public String getKeycloakRole() {
        return keycloakRole;
    }
}
