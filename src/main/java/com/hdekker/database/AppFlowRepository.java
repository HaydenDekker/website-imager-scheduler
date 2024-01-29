package com.hdekker.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hdekker.domain.AppFlow;

@Repository
public interface AppFlowRepository extends JpaRepository<AppFlow, Integer> {

}
