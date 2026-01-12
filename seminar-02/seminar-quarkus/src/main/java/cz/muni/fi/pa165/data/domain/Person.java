package cz.muni.fi.pa165.data.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "person")
public class Person implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String givenName;
    private String familyName;
    private String email;
    private String pwd;

    public Person() {
        id = 0L;
        givenName = "";
        familyName = "";
        email = "";
        pwd = "";
    }

    public Person(Long id, String givenName, String familyName, String email, String pwd) {
        setId(id);
        setGivenName(givenName);
        setFamilyName(familyName);
        setEmail(email);
        setPwd(pwd);
    }

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

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Person person)) {
            return false;
        }
        return this.getEmail().equals(person.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail());
    }

    @Override
    public String toString() {
        return "Person{" + "id=" + id + ", givenName='" + givenName + '\'' + ", familyName='" + familyName + '\'' + ", email='" + email + '\'' + '}';
    }
}
