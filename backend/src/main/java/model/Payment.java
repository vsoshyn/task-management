package model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@PrimaryKeyJoinColumn(name = "task", foreignKey = @ForeignKey(name = "payment_task_fk"))

@Getter @Setter
public class Payment extends Task {
    @Column
    private int cost;
}
