package model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table
@PrimaryKeyJoinColumn(name = "fk_task")

@Getter @Setter
@ToString(doNotUseGetters = true)
public class Purchase extends Task {
    @Column
    private int price;
    @Column
    private int amount;
    @OneToOne
    private Product product;
    @Column
    @Enumerated(EnumType.STRING)
    private Unit unit;
}
