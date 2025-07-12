package com.cydeo.entity;

import com.cydeo.enums.Status;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "projects")
@Getter
@Setter
@Where(clause = "is_delete=false")
@NoArgsConstructor
@AllArgsConstructor
public class Project extends BaseEntity {

    @Column(unique = true)
    private String projectCode;

    private String projectName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private User assignedManager;


    @Column(columnDefinition = "DATE")
    private LocalDate startDate;

    @Column(columnDefinition = "DATE")
    private LocalDate endDate;

    private String projectDetail;
    @Enumerated(EnumType.STRING)
    private Status projectStatus;


}
