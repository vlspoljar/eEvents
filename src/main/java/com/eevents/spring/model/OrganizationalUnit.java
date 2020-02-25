package com.eevents.spring.model;

import javax.persistence.*;

/**
 * Created by Vlatko
 */
@Entity
@Table(name = "organizational_unit")
public class OrganizationalUnit {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @JoinColumn(name = "organizational_unit_type_id", nullable = false, referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private OrganizationalUnitType organizationalUnitTypeId;

    @JoinColumn(name = "organizational_unit_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private OrganizationalUnit organizationalUnitId;

    // construcotrs
    public OrganizationalUnit() {
    }

    public OrganizationalUnit(Integer id) {
        this.id = id;
    }

    public OrganizationalUnit(Integer id, String name, String description, OrganizationalUnitType organizationalUnitTypeId, OrganizationalUnit organizationalUnitId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.organizationalUnitTypeId = organizationalUnitTypeId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public OrganizationalUnitType getOrganizationalUnitTypeId() {
        return organizationalUnitTypeId;
    }

    public void setOrganizationalUnitTypeId(OrganizationalUnitType organizationalUnitTypeId) {
        this.organizationalUnitTypeId = organizationalUnitTypeId;
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
