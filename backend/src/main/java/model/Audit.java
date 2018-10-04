package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

@Getter @Setter
@ToString(doNotUseGetters = true)
public class Audit {
    @Id
    @GeneratedValue(generator = "idGenerator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "idGenerator", allocationSize = 10)
    private long id;
    @ManyToOne
    @JoinColumn(foreignKey=@ForeignKey(name="audit_user_fk"))
    private User user;
    @Column
    private String target;
    @Column
    private String action;
    @Column
    private String oldValue;
    @Column
    private String newValue;
    @Column
    private Instant date;
}
