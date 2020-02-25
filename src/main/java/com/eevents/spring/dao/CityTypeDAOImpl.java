package com.eevents.spring.dao;

import com.eevents.spring.model.City;
import com.eevents.spring.model.CityType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Vlatko
 */
@Repository
public class CityTypeDAOImpl implements CityTypeDAO {

    private static final Logger logger = LoggerFactory.getLogger(EventDAOImpl.class);

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<CityType> getListCityTypes() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Object[]> resultList = session.createNativeQuery("SELECT city_type.id, city_type.name, city_type.active FROM city_type WHERE city_type.active = 'DA' ").list();
        List<CityType> listCityTypes = new ArrayList<>();
        if (resultList != null && !resultList.isEmpty()) {
            CityType cityType;
            for (Object[] o : resultList) {
                if (o != null && o.length == 3) {
                    cityType = new CityType((Integer) o[0], (String) o[1], (String) o[2]);
                    listCityTypes.add(cityType);
                }
            }
        }
        String listCityTypesToString = "";
        if (!listCityTypes.isEmpty()) {
            listCityTypesToString = listCityTypes.stream().map( CityType::toString ).collect( Collectors.joining("\n"));
        }
        logger.info("City types list: \n" + listCityTypesToString);
        return listCityTypes;
    }
}
