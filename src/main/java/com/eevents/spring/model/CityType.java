package com.eevents.spring.model;

import javax.persistence.*;

/**
 * Created by Vlatko
 */
@Entity
@Table(name = "city_type")
public class CityType {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "active")
    private String active;

    // constructors
    public CityType() {
    }

    public CityType(Integer id) {
        this.id = id;
    }

    public CityType(Integer id, String name, String active) {
        this.id = id;
        this.name = name;
        this.active = active;
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

    @Override
    public String toString() {
        return "id=" + id + ", name=" + name;
    }
}
