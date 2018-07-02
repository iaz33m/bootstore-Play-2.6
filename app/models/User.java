package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.ebean.Finder;
import io.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends Model {

    @Id
    @Constraints.Required
    @Constraints.Email
    public String email;

    @Constraints.Required
    public String name;

    @JsonIgnore
    @Constraints.Required
    public String password;

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "user")
    public Address address;

    @JsonIgnore
    @OneToMany(mappedBy="author",cascade = CascadeType.ALL)
    public List<Book> books;


    public static Finder<String,User> find = new Finder<>(User.class);

}
