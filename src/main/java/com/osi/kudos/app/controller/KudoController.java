package com.osi.kudos.app.controller;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.osi.kudos.app.exception.ResourceNotFoundException;
import com.osi.kudos.app.model.Employee;
import com.osi.kudos.app.model.Kudo;
import com.osi.kudos.app.repository.EmployeeRepository;
import com.osi.kudos.app.repository.KudoRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class KudoController {
	@Autowired
	private KudoRepository kudoRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;

	@GetMapping("/kudos")
	public List<Kudo> getAllKudos() {
		return kudoRepository.findAll();
	}

	@GetMapping("/kudos/{id}")
	public ResponseEntity<Kudo> getKudoById(@PathVariable(value = "id") Long kudoId)
			throws ResourceNotFoundException {
		Kudo kudo = kudoRepository.findById(kudoId)
				.orElseThrow(() -> new ResourceNotFoundException("Kudo not found for this id :: " + kudoId));
		return ResponseEntity.ok().body(kudo);
	}
	
	@PostMapping("/kudos")
	public Kudo createKudo(@Valid @RequestBody Kudo kudo) throws ResourceNotFoundException {
		Long employeeId = Long.valueOf(kudo.getEid());
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));
		System.out.println("employee="+employee);
		Kudo kudoSave = new Kudo();
		kudoSave.setKudoComment(kudo.getKudoComment());
		kudoSave.setKudoFrom(kudo.getKudoFrom());
		kudoSave.setEid(kudo.getEid());
		List<Kudo> kudoList = employee.getKudoList();
		kudoList.add(kudoSave);
		System.out.println("###Kudo:"+kudoSave);
//		employee.setKudoList(kudoList);
		return kudoRepository.save(kudoSave);
	}

}
