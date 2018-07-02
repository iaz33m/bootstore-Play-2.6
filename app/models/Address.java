package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.ebean.Finder;
import io.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.*;

@Entity
@Table(name = "addresses")
public class Address extends Model{

    @Id
    public Long id;

    @Constraints.Required
    public String streetAddress;

    @Constraints.Required
    public String city;

    @Constraints.Required
    public String state;

    @Constraints.Required
    public String country;

    @Constraints.Required
    public Integer zipCode;

    public Address(String streetAddress,String city,String state, String country, Integer zipCode) {
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipCode = zipCode;
    }


    @JsonIgnore
    @OneToOne
    public User user;

    public static Finder<String,Address> find = new Finder<>(Address.class);
}
