package com.userRegistartion.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.userRegistartion.entity.Hr;
import com.userRegistartion.entity.Login;
import com.userRegistartion.service.HrServices;

@RestController
@RequestMapping("/hr")
public class HrController {

    @Autowired
    private HrServices hrServices;

    @PostMapping("/create")
    public ResponseEntity<?> createHr(@RequestBody(required = false) Hr hr) {
        try {
            if (hr == null) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("HR Details is not provided");
            }
            
            Hr createdHr = hrServices.createHr(hr);
            return new ResponseEntity<>(createdHr, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<?> getHrById(@PathVariable("id") Integer id) {
        try {
            Optional<Hr> hr = hrServices.getHrById(id);
            return new ResponseEntity<>(hr, HttpStatus.OK);
        } catch (RuntimeException e) {
        	 return ResponseEntity
                     .status(HttpStatus.BAD_REQUEST)
                     .body( e.getMessage());
        
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Hr>> getAllHrs() {
        try {
            List<Hr> hrs = hrServices.getAllHrs();
            return new ResponseEntity<>(hrs, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateHr(@PathVariable("id") int id, @RequestBody(required = false) Hr updatedHr) {
        try {
            if (updatedHr == null) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Input is not provided");
            }

            Hr hr = hrServices.updateHr(id, updatedHr);
            return new ResponseEntity<>(hr, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteHr(@PathVariable("id") Integer id) {
        try {
            hrServices.deleteHr(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody(required = false) Login login) {
        try {
            if (login == null) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Input is not provided");
            }

            ResponseEntity<String> response = hrServices.loginUser(login);
            return response;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
