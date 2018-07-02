package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.ebean.Finder;
import io.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tags")
public class Tag extends Model {

    @Id
    @GeneratedValue
    public Integer id;

    @Constraints.Required
    public String name;


    @Column(columnDefinition = "TEXT")
    public String description;

    @JsonIgnore
    @ManyToMany(mappedBy = "tags")
    public List<Book> books;

    public Tag(Integer id){
        this.id = id;
    }

    public static Finder<Integer,Tag> find = new Finder<>(Tag.class);
}
