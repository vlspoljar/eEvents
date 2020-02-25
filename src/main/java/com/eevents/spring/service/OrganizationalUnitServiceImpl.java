package com.eevents.spring.service;

import com.eevents.spring.dao.OrganizationalUnitDAO;
import com.eevents.spring.model.OrganizationalUnit;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Vlatko
 */
@Service
public class OrganizationalUnitServiceImpl implements OrganizationalUnitService {

    private OrganizationalUnitDAO organizationalUnitDAO;

    public void setOrganizationalUnitDAO(OrganizationalUnitDAO organizationalUnitDAO) {
        this.organizationalUnitDAO = organizationalUnitDAO;
    }

    @Override
    @Transactional
    public List<OrganizationalUnit> listOrganizationalUnitsByType(Integer typeId) {
        return this.organizationalUnitDAO.listOrganizationalUnitsByType(typeId);
    }

}
