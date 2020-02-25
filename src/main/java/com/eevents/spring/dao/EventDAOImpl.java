package com.eevents.spring.dao;

import com.eevents.spring.model.City;
import com.eevents.spring.model.Event;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Vlatko
 */
@Repository
public class EventDAOImpl implements EventDAO {

    private static final Logger logger = LoggerFactory.getLogger(EventDAOImpl.class);

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addEvent(Event event) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(event);
        session.flush();
        logger.info("Event saved successfully, Event details: " + event);
    }

    @Override
    public void updateEvent(Event event) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(event);
        session.flush();
        logger.info("Event updated successfully, Event details: " + event);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Event> listEvents() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Object[]> resultList = session.createNativeQuery("SELECT event.id as eventId, event.name as eventName, event.date_from, event.date_to, event.free_entrance, event.city_id as cityId, city.name as cityName FROM event INNER JOIN city ON city.id = event.city_id").list();
        List<Event> listEvents = new ArrayList<>();
        if (resultList != null && !resultList.isEmpty()) {
            Event event;
            for (Object[] o : resultList) {
                if (o != null && o.length == 7) {
                    event = new Event((Integer) o[0], (String) o[1], (Date) o[2], (Date) o[3], (String) o[4], new City((Integer) o[5], (String) o[6]));
                    listEvents.add(event);
                }
            }
        }
        String listEventsToString = "";
        if (!listEvents.isEmpty()) {
            listEventsToString = listEvents.stream().map( Event::toString ).collect( Collectors.joining("\n"));
        }
        logger.info("Events list: \n" + listEventsToString);
        return listEvents;
    }

    @Override
    public Event getEventById(Integer id) {
        Session session = this.sessionFactory.getCurrentSession();
        Event event = session.load(Event.class, id);
        logger.info("Event loaded successfully, Event details: " + event);
        return event;
    }

    @Override
    public void removeEventById(Integer id) {
        Session session = this.sessionFactory.getCurrentSession();
        Event event = session.load(Event.class, id);
        if (event != null) {
            session.delete(event);
            logger.info("Event deleted successfully, Event details: " + event);
        }
    }
}
