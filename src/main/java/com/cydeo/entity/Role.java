package com.cydeo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table( name = "roles")
public class Role extends BaseEntity {
    private Long id;
    private String description;
    @OneToMany(mappedBy = "role")
    private List<User> user;


}
