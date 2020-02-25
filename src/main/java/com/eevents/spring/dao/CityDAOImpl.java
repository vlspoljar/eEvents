package com.eevents.spring.dao;

import com.eevents.spring.model.City;
import com.eevents.spring.model.CityType;
import com.eevents.spring.model.OrganizationalUnit;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.text.Collator;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Vlatko
 */
@Repository
public class CityDAOImpl implements CityDAO {

    private static final Logger logger = LoggerFactory.getLogger(EventDAOImpl.class);

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<City> listCities() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Object[]> resultList = session.createNativeQuery("SELECT city.id as cityId, city.name as cityName, city.city_type_id as cityTypeId, city.organizational_unit_id as organizationalUnitId, city_type.name as cityTypeName, city_type.active as cityTypeActive FROM city INNER JOIN city_type on city_type.id = city.city_type_id WHERE city_type.active = 'DA' ORDER BY city.name").list();
        List<City> listCities = new ArrayList<>();
        if (resultList != null && !resultList.isEmpty()) {
            City city;
            for (Object[] o : resultList) {
                if (o != null && o.length == 6) {
                    city = new City((Integer) o[0], (String) o[1], new CityType((Integer) o[2], (String) o[4], (String) o[5]), new OrganizationalUnit((Integer) o[3]));
                    listCities.add(city);
                }
            }
        }
        listCities = listCities.stream().sorted(Comparator.comparing(City::getName, Collator.getInstance(new Locale("hr", "HR")))).collect(Collectors.toList());
        String listCitiesToString = "";
        if (!listCities.isEmpty()) {
            listCitiesToString = listCities.stream().map( City::toString ).collect( Collectors.joining("\n"));
        }
        logger.info("Cities list: \n" + listCitiesToString);
        return listCities;
    }
}
