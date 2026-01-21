package cz.muni.fi.pa165.socialnetwork.api;

import cz.muni.fi.pa165.socialnetwork.data.enums.ContactType;

public class PersonContactDto {

    private Long id;
    private ContactType contactType;
    private String contact;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ContactType getContactType() {
        return contactType;
    }

    public void setContactType(ContactType contactType) {
        this.contactType = contactType;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "PersonContactDto{" +
            "id=" + id +
            ", contactType=" + contactType +
            ", contact='" + contact + '\'' +
            '}';
    }
}
