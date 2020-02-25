package com.eevents.spring.model;

import javax.persistence.*;

/**
 * Created by Vlatko
 */
@Entity
@Table(name = "organizational_unit_type")
public class OrganizationalUnitType {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "active")
    private String active;

    // constructors
    public OrganizationalUnitType() {
    }

    public OrganizationalUnitType(Integer id) {
        this.id = id;
    }

    // getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

}
