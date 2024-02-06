package com.hdekker.database;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hdekker.domain.ImageRetrievalEvent;

@Repository
public interface ImageRetrivalEventRepository extends JpaRepository<ImageRetrievalEvent, String>{

	List<ImageRetrievalEvent> findAllByWebsiteName(String websiteName);
	
}
