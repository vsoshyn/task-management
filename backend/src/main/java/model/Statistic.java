package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;

@Entity
@Table

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

@Getter @Setter
@ToString(doNotUseGetters = true)
public class Statistic {
    @Id
    @GeneratedValue(generator = "idGenerator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "idGenerator", allocationSize = 10)
    private long id;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "statistic_task_fk"))
    private Task task;
    @Column
    private int cost;
    @Column
    private Instant date;
    @Column
    private String action;
}
