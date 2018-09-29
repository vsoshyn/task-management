package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

@Getter @Setter
@ToString(doNotUseGetters = true)
public class Config {
    @Id
    @Enumerated(EnumType.STRING)
    @Column
    private ConfigName configName;
    @Enumerated(EnumType.STRING)
    @Column
    private ValueType valueType;
    @Column
    private String stringValue;
    @Type(type = "yes_no")
    @Column
    private Boolean booleanValue;
    @Column
    private Integer intValue;
}

enum ValueType {INTEGER, BOOLEAN, STRING}