package com.example.demo.dao;

import java.util.Set;

import com.example.demo.model.Site;

public interface SiteDao {
    void insert(Site site);
    Site findById(long id);
    Set<Site> findAll();
    boolean delete(long id);
    Site update(Site site);
}
