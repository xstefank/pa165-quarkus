package cz.muni.fi.pa165.socialnetwork.data.model;

import cz.muni.fi.pa165.socialnetwork.data.enums.ContactType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "contact")
public class Contact implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contact")
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "contact_type", length = 40)
    private ContactType contactType;
    @Column(name = "contact", length = 55)
    private String contact;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "person_id")
    private Person person;

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

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Contact)) {
            return false;
        }
        Contact contact1 = (Contact) o;
        return getContactType() == contact1.getContactType() && getContact().equals(contact1.getContact());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getContactType(), getContact());
    }

    @Override
    public String toString() {
        return "Contact{" +
            "id=" + id +
            ", contactType=" + contactType +
            ", contact='" + contact + '\'' +
            '}';
    }
}
