package wsb.employeemanagement.employee.domain;

public enum Role {
    EMPLOYEE("ROLE_EMPLOYEE", "EMPLOYEE"),
    PDL("ROLE_PDL","PDL"),
    PM("ROLE_PM","PM"),
    STAFFING_MANAGER("ROLE_STAFFING_MANAGER","STAFFING_MANAGER");

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
