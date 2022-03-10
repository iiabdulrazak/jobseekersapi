package com.jobseekers.mc1.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.jobseekers.mc1.IntegrationTest;
import com.jobseekers.mc1.domain.Jsdata;
import com.jobseekers.mc1.repository.JsdataRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link JsdataResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class JsdataResourceIT {

    private static final String DEFAULT_GENDER = "AAAAAAAAAA";
    private static final String UPDATED_GENDER = "BBBBBBBBBB";

    private static final String DEFAULT_REGION = "AAAAAAAAAA";
    private static final String UPDATED_REGION = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final Double DEFAULT_AGE = 0D;
    private static final Double UPDATED_AGE = 1D;

    private static final String DEFAULT_DEGREE = "AAAAAAAAAA";
    private static final String UPDATED_DEGREE = "BBBBBBBBBB";

    private static final String DEFAULT_MAJOR = "AAAAAAAAAA";
    private static final String UPDATED_MAJOR = "BBBBBBBBBB";

    private static final String DEFAULT_INISTITUTE = "AAAAAAAAAA";
    private static final String UPDATED_INISTITUTE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/jsdata";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private JsdataRepository jsdataRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJsdataMockMvc;

    private Jsdata jsdata;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Jsdata createEntity(EntityManager em) {
        Jsdata jsdata = new Jsdata()
            .gender(DEFAULT_GENDER)
            .region(DEFAULT_REGION)
            .city(DEFAULT_CITY)
            .age(DEFAULT_AGE)
            .degree(DEFAULT_DEGREE)
            .major(DEFAULT_MAJOR)
            .inistitute(DEFAULT_INISTITUTE);
        return jsdata;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Jsdata createUpdatedEntity(EntityManager em) {
        Jsdata jsdata = new Jsdata()
            .gender(UPDATED_GENDER)
            .region(UPDATED_REGION)
            .city(UPDATED_CITY)
            .age(UPDATED_AGE)
            .degree(UPDATED_DEGREE)
            .major(UPDATED_MAJOR)
            .inistitute(UPDATED_INISTITUTE);
        return jsdata;
    }

    @BeforeEach
    public void initTest() {
        jsdata = createEntity(em);
    }

    @Test
    @Transactional
    void createJsdata() throws Exception {
        int databaseSizeBeforeCreate = jsdataRepository.findAll().size();
        // Create the Jsdata
        restJsdataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jsdata)))
            .andExpect(status().isCreated());

        // Validate the Jsdata in the database
        List<Jsdata> jsdataList = jsdataRepository.findAll();
        assertThat(jsdataList).hasSize(databaseSizeBeforeCreate + 1);
        Jsdata testJsdata = jsdataList.get(jsdataList.size() - 1);
        assertThat(testJsdata.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testJsdata.getRegion()).isEqualTo(DEFAULT_REGION);
        assertThat(testJsdata.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testJsdata.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testJsdata.getDegree()).isEqualTo(DEFAULT_DEGREE);
        assertThat(testJsdata.getMajor()).isEqualTo(DEFAULT_MAJOR);
        assertThat(testJsdata.getInistitute()).isEqualTo(DEFAULT_INISTITUTE);
    }

    @Test
    @Transactional
    void createJsdataWithExistingId() throws Exception {
        // Create the Jsdata with an existing ID
        jsdata.setId(1L);

        int databaseSizeBeforeCreate = jsdataRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restJsdataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jsdata)))
            .andExpect(status().isBadRequest());

        // Validate the Jsdata in the database
        List<Jsdata> jsdataList = jsdataRepository.findAll();
        assertThat(jsdataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllJsdata() throws Exception {
        // Initialize the database
        jsdataRepository.saveAndFlush(jsdata);

        // Get all the jsdataList
        restJsdataMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jsdata.getId().intValue())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
            .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE.doubleValue())))
            .andExpect(jsonPath("$.[*].degree").value(hasItem(DEFAULT_DEGREE)))
            .andExpect(jsonPath("$.[*].major").value(hasItem(DEFAULT_MAJOR)))
            .andExpect(jsonPath("$.[*].inistitute").value(hasItem(DEFAULT_INISTITUTE)));
    }

    @Test
    @Transactional
    void getJsdata() throws Exception {
        // Initialize the database
        jsdataRepository.saveAndFlush(jsdata);

        // Get the jsdata
        restJsdataMockMvc
            .perform(get(ENTITY_API_URL_ID, jsdata.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(jsdata.getId().intValue()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER))
            .andExpect(jsonPath("$.region").value(DEFAULT_REGION))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE.doubleValue()))
            .andExpect(jsonPath("$.degree").value(DEFAULT_DEGREE))
            .andExpect(jsonPath("$.major").value(DEFAULT_MAJOR))
            .andExpect(jsonPath("$.inistitute").value(DEFAULT_INISTITUTE));
    }

    @Test
    @Transactional
    void getNonExistingJsdata() throws Exception {
        // Get the jsdata
        restJsdataMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewJsdata() throws Exception {
        // Initialize the database
        jsdataRepository.saveAndFlush(jsdata);

        int databaseSizeBeforeUpdate = jsdataRepository.findAll().size();

        // Update the jsdata
        Jsdata updatedJsdata = jsdataRepository.findById(jsdata.getId()).get();
        // Disconnect from session so that the updates on updatedJsdata are not directly saved in db
        em.detach(updatedJsdata);
        updatedJsdata
            .gender(UPDATED_GENDER)
            .region(UPDATED_REGION)
            .city(UPDATED_CITY)
            .age(UPDATED_AGE)
            .degree(UPDATED_DEGREE)
            .major(UPDATED_MAJOR)
            .inistitute(UPDATED_INISTITUTE);

        restJsdataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedJsdata.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedJsdata))
            )
            .andExpect(status().isOk());

        // Validate the Jsdata in the database
        List<Jsdata> jsdataList = jsdataRepository.findAll();
        assertThat(jsdataList).hasSize(databaseSizeBeforeUpdate);
        Jsdata testJsdata = jsdataList.get(jsdataList.size() - 1);
        assertThat(testJsdata.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testJsdata.getRegion()).isEqualTo(UPDATED_REGION);
        assertThat(testJsdata.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testJsdata.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testJsdata.getDegree()).isEqualTo(UPDATED_DEGREE);
        assertThat(testJsdata.getMajor()).isEqualTo(UPDATED_MAJOR);
        assertThat(testJsdata.getInistitute()).isEqualTo(UPDATED_INISTITUTE);
    }

    @Test
    @Transactional
    void putNonExistingJsdata() throws Exception {
        int databaseSizeBeforeUpdate = jsdataRepository.findAll().size();
        jsdata.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJsdataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, jsdata.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(jsdata))
            )
            .andExpect(status().isBadRequest());

        // Validate the Jsdata in the database
        List<Jsdata> jsdataList = jsdataRepository.findAll();
        assertThat(jsdataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchJsdata() throws Exception {
        int databaseSizeBeforeUpdate = jsdataRepository.findAll().size();
        jsdata.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJsdataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(jsdata))
            )
            .andExpect(status().isBadRequest());

        // Validate the Jsdata in the database
        List<Jsdata> jsdataList = jsdataRepository.findAll();
        assertThat(jsdataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamJsdata() throws Exception {
        int databaseSizeBeforeUpdate = jsdataRepository.findAll().size();
        jsdata.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJsdataMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jsdata)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Jsdata in the database
        List<Jsdata> jsdataList = jsdataRepository.findAll();
        assertThat(jsdataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateJsdataWithPatch() throws Exception {
        // Initialize the database
        jsdataRepository.saveAndFlush(jsdata);

        int databaseSizeBeforeUpdate = jsdataRepository.findAll().size();

        // Update the jsdata using partial update
        Jsdata partialUpdatedJsdata = new Jsdata();
        partialUpdatedJsdata.setId(jsdata.getId());

        partialUpdatedJsdata.gender(UPDATED_GENDER).degree(UPDATED_DEGREE).major(UPDATED_MAJOR);

        restJsdataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJsdata.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJsdata))
            )
            .andExpect(status().isOk());

        // Validate the Jsdata in the database
        List<Jsdata> jsdataList = jsdataRepository.findAll();
        assertThat(jsdataList).hasSize(databaseSizeBeforeUpdate);
        Jsdata testJsdata = jsdataList.get(jsdataList.size() - 1);
        assertThat(testJsdata.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testJsdata.getRegion()).isEqualTo(DEFAULT_REGION);
        assertThat(testJsdata.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testJsdata.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testJsdata.getDegree()).isEqualTo(UPDATED_DEGREE);
        assertThat(testJsdata.getMajor()).isEqualTo(UPDATED_MAJOR);
        assertThat(testJsdata.getInistitute()).isEqualTo(DEFAULT_INISTITUTE);
    }

    @Test
    @Transactional
    void fullUpdateJsdataWithPatch() throws Exception {
        // Initialize the database
        jsdataRepository.saveAndFlush(jsdata);

        int databaseSizeBeforeUpdate = jsdataRepository.findAll().size();

        // Update the jsdata using partial update
        Jsdata partialUpdatedJsdata = new Jsdata();
        partialUpdatedJsdata.setId(jsdata.getId());

        partialUpdatedJsdata
            .gender(UPDATED_GENDER)
            .region(UPDATED_REGION)
            .city(UPDATED_CITY)
            .age(UPDATED_AGE)
            .degree(UPDATED_DEGREE)
            .major(UPDATED_MAJOR)
            .inistitute(UPDATED_INISTITUTE);

        restJsdataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJsdata.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJsdata))
            )
            .andExpect(status().isOk());

        // Validate the Jsdata in the database
        List<Jsdata> jsdataList = jsdataRepository.findAll();
        assertThat(jsdataList).hasSize(databaseSizeBeforeUpdate);
        Jsdata testJsdata = jsdataList.get(jsdataList.size() - 1);
        assertThat(testJsdata.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testJsdata.getRegion()).isEqualTo(UPDATED_REGION);
        assertThat(testJsdata.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testJsdata.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testJsdata.getDegree()).isEqualTo(UPDATED_DEGREE);
        assertThat(testJsdata.getMajor()).isEqualTo(UPDATED_MAJOR);
        assertThat(testJsdata.getInistitute()).isEqualTo(UPDATED_INISTITUTE);
    }

    @Test
    @Transactional
    void patchNonExistingJsdata() throws Exception {
        int databaseSizeBeforeUpdate = jsdataRepository.findAll().size();
        jsdata.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJsdataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, jsdata.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(jsdata))
            )
            .andExpect(status().isBadRequest());

        // Validate the Jsdata in the database
        List<Jsdata> jsdataList = jsdataRepository.findAll();
        assertThat(jsdataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchJsdata() throws Exception {
        int databaseSizeBeforeUpdate = jsdataRepository.findAll().size();
        jsdata.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJsdataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(jsdata))
            )
            .andExpect(status().isBadRequest());

        // Validate the Jsdata in the database
        List<Jsdata> jsdataList = jsdataRepository.findAll();
        assertThat(jsdataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamJsdata() throws Exception {
        int databaseSizeBeforeUpdate = jsdataRepository.findAll().size();
        jsdata.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJsdataMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(jsdata)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Jsdata in the database
        List<Jsdata> jsdataList = jsdataRepository.findAll();
        assertThat(jsdataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteJsdata() throws Exception {
        // Initialize the database
        jsdataRepository.saveAndFlush(jsdata);

        int databaseSizeBeforeDelete = jsdataRepository.findAll().size();

        // Delete the jsdata
        restJsdataMockMvc
            .perform(delete(ENTITY_API_URL_ID, jsdata.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Jsdata> jsdataList = jsdataRepository.findAll();
        assertThat(jsdataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
