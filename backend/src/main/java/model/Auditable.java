package model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import view.Views;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

@NoArgsConstructor
@Getter @Setter
abstract class Auditable extends Persistable {
    @JsonView(Views.AuditView.AuditDate.CreatedDate.class)
    @Temporal(TemporalType.DATE)
    @CreatedDate
    private Date createdDate = new Date();

    @JsonView(Views.AuditView.AuditDate.LastModifiedDate.class)
    @Temporal(TemporalType.DATE)
    @LastModifiedDate
    private Date lastModifiedDate = new Date();

    @JsonView(Views.AuditView.AuditUser.CreatedBy.class)
    @CreatedBy
    @ManyToOne(fetch = FetchType.LAZY)
    private User createdBy;

    @JsonView(Views.AuditView.AuditUser.LastModifiedBy.class)
    @LastModifiedBy
    @ManyToOne(fetch = FetchType.LAZY)
    private User lastModifiedBy;
}
