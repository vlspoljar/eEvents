package com.eevents.spring.dao;

import com.eevents.spring.model.OrganizationalUnit;

import java.util.List;

/**
 * Created by Vlatko
 */
public interface OrganizationalUnitDAO {

    List<OrganizationalUnit> listOrganizationalUnitsByType(Integer typeId);

}
