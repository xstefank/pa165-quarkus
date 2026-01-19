package cz.muni.fi.pa165.socialnetwork.api;

public class PersonBasicViewDto {

    private Long id;
    private String givenName;
    private String familyName;
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "PersonDto{" +
            "id=" + id +
            ", givenName='" + givenName + '\'' +
            ", familyName='" + familyName + '\'' +
            ", email='" + email + '\'' +
            '}';
    }
}