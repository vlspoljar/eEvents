package com.eevents.spring.dao;

import com.eevents.spring.model.City;
import com.eevents.spring.model.OrganizationalUnit;
import com.eevents.spring.model.OrganizationalUnitType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Created by Vlatko
 */
@Repository
public class OrganizationalUnitDAOImpl implements OrganizationalUnitDAO {

    private static final Logger logger = LoggerFactory.getLogger(EventDAOImpl.class);

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<OrganizationalUnit> listOrganizationalUnitsByType(Integer typeId) {
        Session session = this.sessionFactory.getCurrentSession();
        List<Object[]> resultList = session.createNativeQuery("SELECT organizational_unit.id, organizational_unit.name, organizational_unit.description, organizational_unit.organizational_unit_type_id, organizational_unit.organizational_unit_id FROM organizational_unit INNER JOIN organizational_unit_type on organizational_unit_type.id = organizational_unit.organizational_unit_type_id WHERE organizational_unit.organizational_unit_type_id = :typeId AND organizational_unit_type.active = 'DA' ORDER BY organizational_unit.name").setParameter("typeId", typeId).list();
        List<OrganizationalUnit> listOrganizationalUnits = new ArrayList<>();
        if (resultList != null && !resultList.isEmpty()) {
            OrganizationalUnit organizationalUnit;
            for (Object[] o : resultList) {
                if (o != null && o.length == 5) {
                    organizationalUnit = new OrganizationalUnit((Integer) o[0], (String) o[1], (String) o[2], new OrganizationalUnitType((Integer) o[3]), new OrganizationalUnit((Integer) o[4]));
                    listOrganizationalUnits.add(organizationalUnit);
                }
            }
        }
        listOrganizationalUnits = listOrganizationalUnits.stream().sorted(Comparator.comparing(OrganizationalUnit::getName, Collator.getInstance(new Locale("hr", "HR")))).collect(Collectors.toList());
        String listorganizationalUnitsToString = "";
        if (!listOrganizationalUnits.isEmpty()) {
            listorganizationalUnitsToString = listOrganizationalUnits.stream().map( OrganizationalUnit::toString ).collect( Collectors.joining("\n"));
        }
        logger.info("Organizational units list: \n" + listorganizationalUnitsToString);
        return listOrganizationalUnits;
    }
}
