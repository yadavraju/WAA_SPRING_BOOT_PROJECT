package edu.mum.coffee.controller;

import java.net.URLDecoder;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.mum.coffee.domain.Order;
import edu.mum.coffee.domain.Person;
import edu.mum.coffee.service.PersonService;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    PersonService personService;
    
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(){
    	return "test raju";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Person> create(@RequestBody Person person) {
            person = personService.savePerson(person);
            return new ResponseEntity<Person>(person, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Person> retrieve(@PathVariable Long id) {
        Person existingPerson = personService.findById(id);

        if (existingPerson == null) {
            return new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Person>(existingPerson, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Person> update(@RequestBody Person person) {
        Person existingPerson = personService.findById(person.getId());

        if (existingPerson == null) {
            return new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
        } else {
            personService.savePerson(person);
            return new ResponseEntity<Person>(existingPerson,HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        Person existingPerson = personService.findById(id);
        if (existingPerson == null) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        } else {
            personService.removePerson(existingPerson);
            return new ResponseEntity<Void>(HttpStatus.GONE);
        }
    }
    
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public ResponseEntity<List<Person>> retrieveByPersonEmail(@PathParam("email") String email) {
        String decoded = URLDecoder.decode(email);
        List<Person> person = personService.findByEmail(decoded);
        if (person == null) {
            return new ResponseEntity<List<Person>>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<List<Person>>(person,HttpStatus.OK);
        }
    }
}