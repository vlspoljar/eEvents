package com.eevents.spring.service;

import com.eevents.spring.model.OrganizationalUnit;

import java.util.List;

/**
 * Created by Vlatko
 */
public interface OrganizationalUnitService {

    List<OrganizationalUnit> listOrganizationalUnitsByType(Integer typeId);

}
