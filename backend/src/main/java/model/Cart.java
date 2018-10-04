package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

@Getter @Setter
@ToString(doNotUseGetters = true)
public class Cart extends Persistable {
    @Id
    @GeneratedValue(generator = "idGenerator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "idGenerator", allocationSize = 10)
    private Long id;
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(foreignKey=@ForeignKey(name="cart_user_fk"))
    private User owner;
    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "cart"),
            inverseJoinColumns = @JoinColumn(name = "task"),
            foreignKey = @ForeignKey(name = "cart_fk"),
            inverseForeignKey = @ForeignKey(name = "task_fk"))
    private List<Task> tasks = new ArrayList<>();
}
