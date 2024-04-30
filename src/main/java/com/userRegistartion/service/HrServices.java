package com.userRegistartion.service;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.userRegistartion.Exception.ResourceNotFoundException;
import com.userRegistartion.entity.Hr;
import com.userRegistartion.entity.Login;
import com.userRegistartion.reposatry.HrReposatry;

//import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class HrServices {

	@Autowired
	private HrReposatry hrRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private Key secKey;
//	@Autowired
//	private JwtConfig jwtConfig;

	public Hr createHr(Hr hr) {
		try {
			Optional<Hr> isEmailExistOptional=hrRepository.findByEmail(hr.getEmail());
			if (isEmailExistOptional.isPresent()) {
	            throw new RuntimeException("Email already exists");
	        }
			String hashedPaswordString=passwordEncoder.encode(hr.getPassword());
			hr.setPassword(hashedPaswordString);
			return hrRepository.save(hr);
		} catch (Exception e) {

			throw new RuntimeException("Failed to create HR: " + e.getMessage());
		}
	}

	public Optional<Hr> getHrById(Integer id) {
		try {
			Optional<Hr> hr = hrRepository.findById(id);
			if (hr.isPresent()) {
				return hr;
			} else {
			String	newId=id.toString();
				throw new ResourceNotFoundException("HR Id Not Found", newId, hr);
			}
		} catch (Exception e) {

			throw new RuntimeException("Failed to retrieve HR: " + e.getMessage());
		}
	}

	public List<Hr> getAllHrs() {
		try {
			return hrRepository.findAll();
		} catch (Exception e) {

			throw new RuntimeException("Failed to retrieve all HRs: " + e.getMessage());
		}
	}

	public Hr updateHr(int id, Hr updatedHr) {
		try {
			Optional<Hr> hr = hrRepository.findById(id);
			if (hr.isPresent()) {
				Hr existingHr = hr.get();
				existingHr.setName(updatedHr.getName());

				return hrRepository.save(existingHr);
			} else {
				throw new ResourceNotFoundException("HR", "id", id);
			}
		} catch (Exception e) {

			throw new RuntimeException("Failed to update HR: " + e.getMessage());
		}
	}

	public void deleteHr(Integer id) {
		try {
			Optional<Hr> hr = hrRepository.findById(id);
			if (hr.isPresent()) {
				hrRepository.deleteById(id);
			} else {
				throw new ResourceNotFoundException("HR", "id", id);
			}
		} catch (Exception e) {

			throw new RuntimeException("Failed to delete HR: " + e.getMessage());
		}
	}

	public ResponseEntity<String> loginUser(Login login) {
		System.out.println(login);
	    Optional<Hr> hrDataOptional = hrRepository.findByEmail(login.getEmail());
	    System.out.println(hrDataOptional.get().getPassword());
	    if (hrDataOptional.isPresent()) {
	        Hr hrData = hrDataOptional.get();
	        if (passwordEncoder.matches(login.getPassword(), hrData.getPassword())) {
	            String token = Jwts.builder().setSubject(login.getEmail()).setIssuedAt(new Date())
	                    .signWith(secKey, SignatureAlgorithm.HS256).compact();
	            System.out.println(token);
	            return ResponseEntity.ok(token);
	            
	        }
	    }
	    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("email or password is wrong or both");
	}


//	public ResponseEntity<Claims> tokenDetails(String token) {
//		Claims claims = jwtConfig.decodeToken(token);
//		return ResponseEntity.ok(claims);
//	}
}