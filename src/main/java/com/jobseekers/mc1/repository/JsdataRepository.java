package com.jobseekers.mc1.repository;

import com.jobseekers.mc1.domain.Jsdata;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Jsdata entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JsdataRepository extends JpaRepository<Jsdata, Long> {}
