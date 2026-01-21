package cz.muni.fi.pa165.socialnetwork.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PersonDetailedViewDto {

    private Long id;
    private String givenName;
    private String familyName;
    private String email;
    private List<PersonContactDto> contacts = new ArrayList<>();

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

    public List<PersonContactDto> getContacts() {
        return contacts;
    }

    public void setContacts(List<PersonContactDto> contacts) {
        this.contacts = contacts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersonDetailedViewDto personDetailDto)) {
            return false;
        }
        return getEmail().equals(personDetailDto.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail());
    }

    @Override
    public String toString() {
        return "PersonDetailedViewDto{" +
            "id=" + id +
            ", givenName='" + givenName + '\'' +
            ", familyName='" + familyName + '\'' +
            ", email='" + email + '\'' +
            ", contacts=" + contacts +
            '}';
    }
}
