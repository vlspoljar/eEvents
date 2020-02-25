package com.eevents.spring.service;

import com.eevents.spring.dao.EventDAO;
import com.eevents.spring.model.Event;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Vlatko
 */
@Service
public class EventServiceImpl implements EventService {

    private EventDAO eventDAO;

    public void setEventDAO(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }

    @Override
    @Transactional
    public void addEvent(Event event) {
        this.eventDAO.addEvent(event);
    }

    @Override
    @Transactional
    public void updateEvent(Event event) {
        this.eventDAO.updateEvent(event);
    }

    @Override
    @Transactional
    public List<Event> listEvents() {
        return this.eventDAO.listEvents();
    }

    @Override
    @Transactional
    public Event getEventById(Integer id) {
        return this.eventDAO.getEventById(id);
    }

    @Override
    @Transactional
    public void removeEventById(Integer id) {
        this.eventDAO.removeEventById(id);
    }

}
