package com.example.demo.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model object representing a solar power installation.
 */
public class Site implements  Comparable<Site>{
    private Long id;

    private Double capacity;
    private Integer panels;

    private String address;
    private String city;
    private String state;
    private String postalCode;

    

    public Site() {
    }

    public Site(long id, double capacity, Integer panels, String address, String city,
                String state, String postalCode) {
        this.id = id;
        this.capacity = capacity;
        this.panels = panels;
        this.address = address;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
    }

    // Build a new Site from a Map<String, String>.
    // Note: we explicitly exclude lastReportingTime and meterReadingTime,
    // as these fields are volatile.
    public Site(Map<String, String> fields) {
        if (!fields.containsKey("id") || !fields.containsKey("capacity") || !fields.containsKey(
                "panels")) {
            throw new IllegalArgumentException("Map<String, String> used to build a Site must " +
                    "contain keys 'id', 'capacity', and 'panels'.");
        }
        this.id = Long.valueOf(fields.getOrDefault("id", null));
        this.capacity = Double.valueOf(fields.getOrDefault("capacity", null));
        this.panels = Integer.valueOf(fields.getOrDefault("panels", null));
        this.address = fields.getOrDefault("address", null);
        this.city = fields.getOrDefault("city", null);
        this.state = fields.getOrDefault("state", null);
        this.postalCode = fields.getOrDefault("postalCode", null);
   
    }

    @JsonProperty
    public Long getId() {
        return id;
    }

    @JsonProperty
    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty
    public Double getCapacity() {
        return capacity;
    }

    @JsonProperty
    public void setCapacity(Double capacity) {
        this.capacity = capacity;
    }

    @JsonProperty
    public Integer getPanels() {
        return panels;
    }

    @JsonProperty
    public void setPanels(Integer panels) {
        this.panels = panels;
    }

    @JsonProperty
    public String getAddress() {
        return address;
    }

    @JsonProperty
    public void setAddress(String address) {
        this.address = address;
    }

    @JsonProperty
    public String getCity() {
        return city;
    }

    @JsonProperty
    public void setCity(String city) {
        this.city = city;
    }

    @JsonProperty
    public String getState() {
        return state;
    }

    @JsonProperty
    public void setState(String state) {
        this.state = state;
    }

    @JsonProperty
    public String getPostalCode() {
        return postalCode;
    }

    @JsonProperty
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

 
    // Create a Map<String, String> from this Site.
    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap();
        map.put("id", String.valueOf(id));
        map.put("capacity", String.valueOf(capacity));
        map.put("panels", String.valueOf(panels));
        map.put("address", address);
        map.put("city", city);
        map.put("state", state);
        map.put("postalCode", postalCode);
       

        return map;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Site that = (Site) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(capacity, that.capacity) &&
                Objects.equals(panels, that.panels) &&
                Objects.equals(address, that.address) &&
                Objects.equals(city, that.city) &&
                Objects.equals(state, that.state) &&
                Objects.equals(postalCode, that.postalCode);
                
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, capacity, panels, address, city, state, postalCode);
    }

    @Override
    public String toString() {
        return "Site{" +
                "id=" + id +
                ", capacity=" + capacity +
                ", panels=" + panels +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                '}';
    }

    @Override
    public int compareTo(Site o) {
    	
           return id.compareTo(o.id);
    }
}
