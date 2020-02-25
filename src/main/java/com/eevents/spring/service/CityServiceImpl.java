package com.eevents.spring.service;

import com.eevents.spring.dao.CityDAO;
import com.eevents.spring.model.City;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Vlatko
 */
@Service
public class CityServiceImpl implements CityService {

    private CityDAO cityDAO;

    public void setCityDAO(CityDAO cityDAO) {
        this.cityDAO = cityDAO;
    }

    @Override
    @Transactional
    public List<City> listCities() {
        return this.cityDAO.listCities();
    }
}
