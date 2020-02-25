package com.eevents.spring.model;

import javax.persistence.*;

/**
 * Created by Vlatko
 */
@Entity
@Table(name = "city")
public class City {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @JoinColumn(name = "city_type_id", nullable = false, referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private CityType cityTypeId;

    @JoinColumn(name = "organizational_unit_id", nullable = false, referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private OrganizationalUnit organizationalUnitId;

    // construcotrs
    public City() {
    }

    public City(Integer id) {
        this.id = id;
    }

    public City(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public City(Integer id, String name, CityType cityTypeId, OrganizationalUnit organizationalUnitId) {
        this.id = id;
        this.name = name;
        this.cityTypeId = cityTypeId;
        this.organizationalUnitId = organizationalUnitId;
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

    public CityType getCityTypeId() {
        return cityTypeId;
    }

    public void setCityTypeId(CityType cityTypeId) {
        this.cityTypeId = cityTypeId;
    }

    public OrganizationalUnit getOrganizationalUnitId() {
        return organizationalUnitId;
    }

    public void setOrganizationalUnitId(OrganizationalUnit organizationalUnitId) {
        this.organizationalUnitId = organizationalUnitId;
    }

    @Override
    public String toString() {
        return "id=" + id + ", name=" + name;
    }
}
