package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table
@PrimaryKeyJoinColumn(name = "task", foreignKey = @ForeignKey(name = "maintenance_task_fk"))

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

@Getter @Setter
public class Maintenance extends Task {
    @Column(name = "m_cost")
    private int cost;
    @Column
    @Type(type = "yes_no")
    private boolean requiresSpecialist;
    @Embedded
    private Specialist specialist;
}
