package com.eevents.spring.controller;

import com.eevents.spring.model.City;
import com.eevents.spring.model.CityType;
import com.eevents.spring.model.Event;
import com.eevents.spring.model.OrganizationalUnit;
import com.eevents.spring.service.CityService;
import com.eevents.spring.service.CityTypeService;
import com.eevents.spring.service.EventService;
import com.eevents.spring.service.OrganizationalUnitService;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Vlatko
 */
@Named
@ViewScoped
public class EventController implements Serializable {

    // services
    private EventService eventService;
    private CityService cityService;
    private OrganizationalUnitService organizationalUnitService;
    private CityTypeService cityTypeService;
    // lists
    private List<Event> listEvents;
    private List<City> listCities, listAllCities;
    private List<SelectItem> cities;
    private List<OrganizationalUnit> listRegions, listCounties, listAllCounties;
    private List<CityType> listCityTypes;
    private List<String> selectedRegions, selectedCounties, selectedCityTypes, selectedCities;
    // create and filter objects
    private Event createEvent, filterEvent;
    private boolean inputFree;
    private String filterFree;

    public EventController() {
    }

    @PostConstruct
    public void init() {
        // services
        setServices();
        // initialization of lists
        initLists();
        // initialization of create and filter objects
        resetInputFormAction();
        resetFilterFormAction();
    }

    private void setServices() {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("beans.xml");
        setEventService(classPathXmlApplicationContext);
        setCityService(classPathXmlApplicationContext);
        setOrganizationalUnitService(classPathXmlApplicationContext);
        setCityTypeService(classPathXmlApplicationContext);
    }

    private void initLists() {
        listEvents = this.eventService.listEvents();
        listAllCities = this.cityService.listCities();
        listCities = listAllCities;
        cities = new ArrayList<>();
        if (listCities != null && !listCities.isEmpty()) {
            for (City city : listCities) {
                cities.add(new SelectItem(city.getId(), city.getName()));
            }
        }
        listRegions = this.organizationalUnitService.listOrganizationalUnitsByType(1);
        selectedRegions = new ArrayList<>();
        listAllCounties = this.organizationalUnitService.listOrganizationalUnitsByType(2);;
        listCounties = listAllCounties;
        selectedCounties = new ArrayList<>();
        listCityTypes = this.cityTypeService.getListCityTypes();
        selectedCityTypes = new ArrayList<>();
    }

    public void resetInputFormAction() {
        createEvent = new Event();
        createEvent.setCityId(new City());
        setInputFree(false);
    }

    public void resetFilterFormAction() {
        filterEvent = new Event();
        filterEvent.setCityId(new City());
        setFilterFree("null");
        initLists();
    }

    public void changeRegion() {
        if (selectedRegions != null && !selectedRegions.isEmpty()) {
            listCounties = listAllCounties.stream().filter(county -> selectedRegions.contains(county.getOrganizationalUnitId().getId().toString())).collect(Collectors.toList());
        } else {
            listCounties = listAllCounties;
        }
    }

    public void changeCounty() {
        if (selectedCounties != null && !selectedCounties.isEmpty()) {
            listCities = listAllCities.stream().filter(city -> selectedCounties.contains(city.getOrganizationalUnitId().getId().toString())).collect(Collectors.toList());
        } else {
            listCities = listAllCities;
        }
    }

    public void changeCityType() {
        changeCounty();
        if (selectedCityTypes != null && !selectedCityTypes.isEmpty()) {
            listCities = listCities.stream().filter(city -> selectedCityTypes.contains(city.getCityTypeId().getId().toString())).collect(Collectors.toList());
        }
    }

    public void filterAction() {
        listEvents = this.eventService.listEvents();
        if (listEvents != null && !listEvents.isEmpty()) {
            if (filterEvent != null) {
                if (StringUtils.isNotBlank(filterEvent.getName())) listEvents = listEvents.stream().filter(event -> event.getName().equals(filterEvent.getName())).collect(Collectors.toList());
                if (filterEvent.getDateFrom() != null) listEvents = listEvents.stream().filter(event -> event.getDateFrom().after(filterEvent.getDateFrom())).collect(Collectors.toList());
                if (filterEvent.getDateTo() != null) listEvents = listEvents.stream().filter(event -> event.getDateFrom().before(filterEvent.getDateTo())).collect(Collectors.toList());
                if (StringUtils.isNotBlank(filterFree) && !filterFree.equals("null")) listEvents = listEvents.stream().filter(event -> event.getFreeEntrance().equals(filterFree)).collect(Collectors.toList());
            }
            if (selectedCities != null && !selectedCities.isEmpty()) {
                listEvents = listEvents.stream().filter(event -> selectedCities.contains(event.getCityId().getId().toString())).collect(Collectors.toList());
            }
        }
    }

    public void saveAction() {
        if (createEvent != null) {
            createEvent.setFreeEntrance(inputFree ? "DA" : "NE");
            if (StringUtils.isBlank(createEvent.getName())) {
                addMessage("Event name empty", false);
            } else if (createEvent.getDateFrom() == null) {
                addMessage("Event date from empty", false);
            } else if (createEvent.getCityId() == null || createEvent.getCityId().getId() == null) {
                addMessage("Event city empty", false);
            } else {
                if (createEvent.getId() == null) {
                    this.eventService.addEvent(createEvent);
                    addMessage("Event saved", true);
                } else {
                    this.eventService.updateEvent(createEvent);
                    addMessage("Event updated", true);
                }
                listEvents = this.eventService.listEvents();
            }
        } else {
            addMessage("Event empty", false);
        }
    }

    public void deleteAction() {
        if (createEvent != null && createEvent.getId() != null) {
            this.eventService.removeEventById(createEvent.getId());
            addMessage("Event deleted", true);
            resetInputFormAction();
            listEvents = this.eventService.listEvents();
        } else {
            addMessage("Event not selected", false);
        }
    }

    public void onRowSelect(AjaxBehaviorEvent event) {
        Object selected = ((SelectEvent)event).getObject();
        if (selected instanceof Event) {
            createEvent = (Event) selected;
            setInputFree(StringUtils.isNoneBlank(createEvent.getFreeEntrance()) && createEvent.getFreeEntrance().equals("DA"));
        }
    }

    public void addMessage(String summary, boolean info) {
        FacesMessage message = new FacesMessage(info ? FacesMessage.SEVERITY_INFO : FacesMessage.SEVERITY_WARN, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    // getters and setters
    @Autowired(required=true)
    @Qualifier(value="eventService")
    public void setEventService(ClassPathXmlApplicationContext classPathXmlApplicationContext) {
        this.eventService = classPathXmlApplicationContext.getBean("eventService", EventService.class);
    }

    @Autowired(required=true)
    @Qualifier(value="cityService")
    public void setCityService(ClassPathXmlApplicationContext classPathXmlApplicationContext) {
        this.cityService = classPathXmlApplicationContext.getBean("cityService", CityService.class);
    }

    @Autowired(required=true)
    @Qualifier(value="organizationalUnitService")
    public void setOrganizationalUnitService(ClassPathXmlApplicationContext classPathXmlApplicationContext) {
        this.organizationalUnitService = classPathXmlApplicationContext.getBean("organizationalUnitService", OrganizationalUnitService.class);
    }

    @Autowired(required=true)
    @Qualifier(value="cityTypeService")
    public void setCityTypeService(ClassPathXmlApplicationContext classPathXmlApplicationContext) {
        this.cityTypeService = classPathXmlApplicationContext.getBean("cityTypeService", CityTypeService.class);
    }

    public List<Event> getListEvents() {
        return listEvents;
    }

    public void setListEvents(List<Event> listEvents) {
        this.listEvents = listEvents;
    }

    public List<City> getListCities() {
        return listCities;
    }

    public void setListCities(List<City> listCities) {
        this.listCities = listCities;
    }

    public List<City> getListAllCities() {
        return listAllCities;
    }

    public void setListAllCities(List<City> listAllCities) {
        this.listAllCities = listAllCities;
    }

    public List<SelectItem> getCities() {
        return cities;
    }

    public void setCities(List<SelectItem> cities) {
        this.cities = cities;
    }

    public List<OrganizationalUnit> getListRegions() {
        return listRegions;
    }

    public void setListRegions(List<OrganizationalUnit> listRegions) {
        this.listRegions = listRegions;
    }

    public List<OrganizationalUnit> getListCounties() {
        return listCounties;
    }

    public void setListCounties(List<OrganizationalUnit> listCounties) {
        this.listCounties = listCounties;
    }

    public List<OrganizationalUnit> getListAllCounties() {
        return listAllCounties;
    }

    public void setListAllCounties(List<OrganizationalUnit> listAllCounties) {
        this.listAllCounties = listAllCounties;
    }

    public List<CityType> getListCityTypes() {
        return listCityTypes;
    }

    public void setListCityTypes(List<CityType> listCityTypes) {
        this.listCityTypes = listCityTypes;
    }

    public List<String> getSelectedRegions() {
        return selectedRegions;
    }

    public void setSelectedRegions(List<String> selectedRegions) {
        this.selectedRegions = selectedRegions;
    }

    public List<String> getSelectedCounties() {
        return selectedCounties;
    }

    public void setSelectedCounties(List<String> selectedCounties) {
        this.selectedCounties = selectedCounties;
    }

    public List<String> getSelectedCityTypes() {
        return selectedCityTypes;
    }

    public void setSelectedCityTypes(List<String> selectedCityTypes) {
        this.selectedCityTypes = selectedCityTypes;
    }

    public List<String> getSelectedCities() {
        return selectedCities;
    }

    public void setSelectedCities(List<String> selectedCities) {
        this.selectedCities = selectedCities;
    }

    public Event getCreateEvent() {
        return createEvent;
    }

    public void setCreateEvent(Event createEvent) {
        this.createEvent = createEvent;
    }

    public Event getFilterEvent() {
        return filterEvent;
    }

    public void setFilterEvent(Event filterEvent) {
        this.filterEvent = filterEvent;
    }

    public boolean isInputFree() {
        return inputFree;
    }

    public void setInputFree(boolean inputFree) {
        this.inputFree = inputFree;
    }

    public String getFilterFree() {
        return filterFree;
    }

    public void setFilterFree(String filterFree) {
        this.filterFree = filterFree;
    }
}
