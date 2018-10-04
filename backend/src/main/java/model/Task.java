package model;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.hibernate.annotations.Type;
import view.Views;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table
@Inheritance(strategy = InheritanceType.JOINED)

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Maintenance.class, name = "maintenance"),
        @JsonSubTypes.Type(value = Payment.class, name = "payment"),
        @JsonSubTypes.Type(value = Purchase.class, name = "purchase")
})

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
@ToString(doNotUseGetters = true, of = {"id", "shared", "state"})
public class Task extends Auditable {
    @JsonView(Views.CartView.class)
    @Id
    @GeneratedValue(generator = "idGenerator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "idGenerator", allocationSize = 10)
    private Long id;
    @Column
    private String schedule;
    @Column
    private LocalDate dueDate;
    @Column
    @Type(type = "yes_no")
    private boolean shared;
    @Column
    @Enumerated(EnumType.STRING)
    private TaskState state = TaskState.OPENED;
    @Column
    @Enumerated(EnumType.STRING)
    private Priority priority;
    @Column
    private String target;
    @Column
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "parent_task_fk"))
    private Task parent;
    @Column
    @Lob
    private byte[] image;
    @JsonIgnore
    @ManyToMany(mappedBy = "tasks")
    private List<Cart> carts = new ArrayList<>();
}
