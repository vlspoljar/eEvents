package com.eevents.spring.service;

import com.eevents.spring.dao.CityTypeDAO;
import com.eevents.spring.model.CityType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Vlatko
 */
@Service
public class CityTypeServiceImpl implements CityTypeService {

    private CityTypeDAO cityTypeDAO;

    public void setCityTypeDAO(CityTypeDAO cityTypeDAO) {
        this.cityTypeDAO = cityTypeDAO;
    }

    @Override
    @Transactional
    public List<CityType> getListCityTypes() {
        return this.cityTypeDAO.getListCityTypes();
    }
}
