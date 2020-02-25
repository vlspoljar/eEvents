package com.eevents.spring.dao;

import com.eevents.spring.model.Event;

import java.util.List;

/**
 * Created by Vlatko
 */
public interface EventDAO {

    void addEvent(Event event);

    void updateEvent(Event event);

    List<Event> listEvents();

    Event getEventById(Integer id);

    void removeEventById(Integer id);

}
