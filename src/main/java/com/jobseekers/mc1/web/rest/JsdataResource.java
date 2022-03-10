package com.jobseekers.mc1.web.rest;

import com.jobseekers.mc1.domain.Jsdata;
import com.jobseekers.mc1.repository.JsdataRepository;
import com.jobseekers.mc1.service.JsdataService;
import com.jobseekers.mc1.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.jobseekers.mc1.domain.Jsdata}.
 */
@RestController
@RequestMapping("/api")
public class JsdataResource {

    private final Logger log = LoggerFactory.getLogger(JsdataResource.class);

    private static final String ENTITY_NAME = "jobseekersapiJsdata";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JsdataService jsdataService;

    private final JsdataRepository jsdataRepository;

    public JsdataResource(JsdataService jsdataService, JsdataRepository jsdataRepository) {
        this.jsdataService = jsdataService;
        this.jsdataRepository = jsdataRepository;
    }

    /**
     * {@code POST  /jsdata} : Create a new jsdata.
     *
     * @param jsdata the jsdata to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jsdata, or with status {@code 400 (Bad Request)} if the jsdata has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/jsdata")
    public ResponseEntity<Jsdata> createJsdata(@Valid @RequestBody Jsdata jsdata) throws URISyntaxException {
        log.debug("REST request to save Jsdata : {}", jsdata);
        if (jsdata.getId() != null) {
            throw new BadRequestAlertException("A new jsdata cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Jsdata result = jsdataService.save(jsdata);
        return ResponseEntity
            .created(new URI("/api/jsdata/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /jsdata/:id} : Updates an existing jsdata.
     *
     * @param id the id of the jsdata to save.
     * @param jsdata the jsdata to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jsdata,
     * or with status {@code 400 (Bad Request)} if the jsdata is not valid,
     * or with status {@code 500 (Internal Server Error)} if the jsdata couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/jsdata/{id}")
    public ResponseEntity<Jsdata> updateJsdata(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Jsdata jsdata
    ) throws URISyntaxException {
        log.debug("REST request to update Jsdata : {}, {}", id, jsdata);
        if (jsdata.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, jsdata.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!jsdataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Jsdata result = jsdataService.save(jsdata);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jsdata.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /jsdata/:id} : Partial updates given fields of an existing jsdata, field will ignore if it is null
     *
     * @param id the id of the jsdata to save.
     * @param jsdata the jsdata to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jsdata,
     * or with status {@code 400 (Bad Request)} if the jsdata is not valid,
     * or with status {@code 404 (Not Found)} if the jsdata is not found,
     * or with status {@code 500 (Internal Server Error)} if the jsdata couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/jsdata/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Jsdata> partialUpdateJsdata(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Jsdata jsdata
    ) throws URISyntaxException {
        log.debug("REST request to partial update Jsdata partially : {}, {}", id, jsdata);
        if (jsdata.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, jsdata.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!jsdataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Jsdata> result = jsdataService.partialUpdate(jsdata);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jsdata.getId().toString())
        );
    }

    /**
     * {@code GET  /jsdata} : get all the jsdata.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jsdata in body.
     */
    @GetMapping("/jsdata")
    public List<Jsdata> getAllJsdata() {
        log.debug("REST request to get all Jsdata");
        return jsdataService.findAll();
    }

    /**
     * {@code GET  /jsdata/:id} : get the "id" jsdata.
     *
     * @param id the id of the jsdata to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jsdata, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/jsdata/{id}")
    public ResponseEntity<Jsdata> getJsdata(@PathVariable Long id) {
        log.debug("REST request to get Jsdata : {}", id);
        Optional<Jsdata> jsdata = jsdataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(jsdata);
    }

    /**
     * {@code DELETE  /jsdata/:id} : delete the "id" jsdata.
     *
     * @param id the id of the jsdata to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/jsdata/{id}")
    public ResponseEntity<Void> deleteJsdata(@PathVariable Long id) {
        log.debug("REST request to delete Jsdata : {}", id);
        jsdataService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
