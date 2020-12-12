package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.SiteDaoRedisImpl;
import com.example.demo.model.Site;

//mark class as Controller
@RestController
public class JedisController {

	@Autowired
	SiteDaoRedisImpl dao;

	@PostMapping("/site")
	private String addSite(@RequestBody Site site) {
		dao.insert(site);

		return "Site successfully added";
	}
	

	@GetMapping("/site/{siteId}")
	private Site getSite(@PathVariable("siteId") int siteId) {
		return dao.findById(siteId);
	}

	@DeleteMapping("/site/{siteId}")
	private boolean delete(@PathVariable("siteId") long siteId) {
		return dao.delete(siteId);
	}

	@PutMapping("/site")
	
	private Site update(@RequestBody Site site) {

		return dao.update(site);
	}
}
