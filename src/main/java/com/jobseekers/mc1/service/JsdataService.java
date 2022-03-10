package com.jobseekers.mc1.service;

import com.jobseekers.mc1.domain.Jsdata;
import com.jobseekers.mc1.repository.JsdataRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Jsdata}.
 */
@Service
@Transactional
public class JsdataService {

    private final Logger log = LoggerFactory.getLogger(JsdataService.class);

    private final JsdataRepository jsdataRepository;

    public JsdataService(JsdataRepository jsdataRepository) {
        this.jsdataRepository = jsdataRepository;
    }

    /**
     * Save a jsdata.
     *
     * @param jsdata the entity to save.
     * @return the persisted entity.
     */
    public Jsdata save(Jsdata jsdata) {
        log.debug("Request to save Jsdata : {}", jsdata);
        return jsdataRepository.save(jsdata);
    }

    /**
     * Partially update a jsdata.
     *
     * @param jsdata the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Jsdata> partialUpdate(Jsdata jsdata) {
        log.debug("Request to partially update Jsdata : {}", jsdata);

        return jsdataRepository
            .findById(jsdata.getId())
            .map(existingJsdata -> {
                if (jsdata.getGender() != null) {
                    existingJsdata.setGender(jsdata.getGender());
                }
                if (jsdata.getRegion() != null) {
                    existingJsdata.setRegion(jsdata.getRegion());
                }
                if (jsdata.getCity() != null) {
                    existingJsdata.setCity(jsdata.getCity());
                }
                if (jsdata.getAge() != null) {
                    existingJsdata.setAge(jsdata.getAge());
                }
                if (jsdata.getDegree() != null) {
                    existingJsdata.setDegree(jsdata.getDegree());
                }
                if (jsdata.getMajor() != null) {
                    existingJsdata.setMajor(jsdata.getMajor());
                }
                if (jsdata.getInistitute() != null) {
                    existingJsdata.setInistitute(jsdata.getInistitute());
                }

                return existingJsdata;
            })
            .map(jsdataRepository::save);
    }

    /**
     * Get all the jsdata.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Jsdata> findAll() {
        log.debug("Request to get all Jsdata");
        return jsdataRepository.findAll();
    }

    /**
     * Get one jsdata by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Jsdata> findOne(Long id) {
        log.debug("Request to get Jsdata : {}", id);
        return jsdataRepository.findById(id);
    }

    /**
     * Delete the jsdata by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Jsdata : {}", id);
        jsdataRepository.deleteById(id);
    }
}
