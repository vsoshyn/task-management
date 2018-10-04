package model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table
@PrimaryKeyJoinColumn(name = "task", foreignKey = @ForeignKey(name = "purchase_task_fk"))

@Getter @Setter
@ToString(doNotUseGetters = true)
public class Purchase extends Task {
    @Column
    private int price;
    @Column
    private int amount;
    @OneToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "purchase_product_fk"))
    private Product product;
    @Column
    @Enumerated(EnumType.STRING)
    private Unit unit;
}
