package com.userRegistartion.reposatry;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.userRegistartion.entity.Hr;
@Repository
public interface HrReposatry extends JpaRepository<Hr, Integer> {
	public List<Hr> findByName(String name);

	 Optional<Hr> findByEmail(String email); 
}
