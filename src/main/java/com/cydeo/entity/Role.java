package com.cydeo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Fetch;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Table( name = "roles")
public class Role extends BaseEntity {
    private Long id;
    private String description;
    @OneToMany(mappedBy = "role",fetch = FetchType.EAGER)
    private List<User> user;


}
