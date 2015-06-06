package edu.upc.backus.web.rest;

import edu.upc.backus.Application;
import edu.upc.backus.domain.MedidasLts;
import edu.upc.backus.repository.MedidasLtsRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MedidasLtsResource REST controller.
 *
 * @see MedidasLtsResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class MedidasLtsResourceTest {

    private static final String DEFAULT_NOMBRE = "SAMPLE_TEXT";
    private static final String UPDATED_NOMBRE = "UPDATED_TEXT";

    private static final BigDecimal DEFAULT_CANTIDAD = BigDecimal.ZERO;
    private static final BigDecimal UPDATED_CANTIDAD = BigDecimal.ONE;

    @Inject
    private MedidasLtsRepository medidasLtsRepository;

    private MockMvc restMedidasLtsMockMvc;

    private MedidasLts medidasLts;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MedidasLtsResource medidasLtsResource = new MedidasLtsResource();
        ReflectionTestUtils.setField(medidasLtsResource, "medidasLtsRepository", medidasLtsRepository);
        this.restMedidasLtsMockMvc = MockMvcBuilders.standaloneSetup(medidasLtsResource).build();
    }

    @Before
    public void initTest() {
        medidasLts = new MedidasLts();
        medidasLts.setNombre(DEFAULT_NOMBRE);
        medidasLts.setCantidad(DEFAULT_CANTIDAD);
    }

    @Test
    @Transactional
    public void createMedidasLts() throws Exception {
        int databaseSizeBeforeCreate = medidasLtsRepository.findAll().size();

        // Create the MedidasLts
        restMedidasLtsMockMvc.perform(post("/api/medidasLtss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(medidasLts)))
                .andExpect(status().isCreated());

        // Validate the MedidasLts in the database
        List<MedidasLts> medidasLtss = medidasLtsRepository.findAll();
        assertThat(medidasLtss).hasSize(databaseSizeBeforeCreate + 1);
        MedidasLts testMedidasLts = medidasLtss.get(medidasLtss.size() - 1);
        assertThat(testMedidasLts.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testMedidasLts.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(medidasLtsRepository.findAll()).hasSize(0);
        // set the field null
        medidasLts.setNombre(null);

        // Create the MedidasLts, which fails.
        restMedidasLtsMockMvc.perform(post("/api/medidasLtss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(medidasLts)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<MedidasLts> medidasLtss = medidasLtsRepository.findAll();
        assertThat(medidasLtss).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllMedidasLtss() throws Exception {
        // Initialize the database
        medidasLtsRepository.saveAndFlush(medidasLts);

        // Get all the medidasLtss
        restMedidasLtsMockMvc.perform(get("/api/medidasLtss"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(medidasLts.getId().intValue())))
                .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
                .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD.intValue())));
    }

    @Test
    @Transactional
    public void getMedidasLts() throws Exception {
        // Initialize the database
        medidasLtsRepository.saveAndFlush(medidasLts);

        // Get the medidasLts
        restMedidasLtsMockMvc.perform(get("/api/medidasLtss/{id}", medidasLts.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(medidasLts.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMedidasLts() throws Exception {
        // Get the medidasLts
        restMedidasLtsMockMvc.perform(get("/api/medidasLtss/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMedidasLts() throws Exception {
        // Initialize the database
        medidasLtsRepository.saveAndFlush(medidasLts);
		
		int databaseSizeBeforeUpdate = medidasLtsRepository.findAll().size();

        // Update the medidasLts
        medidasLts.setNombre(UPDATED_NOMBRE);
        medidasLts.setCantidad(UPDATED_CANTIDAD);
        restMedidasLtsMockMvc.perform(put("/api/medidasLtss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(medidasLts)))
                .andExpect(status().isOk());

        // Validate the MedidasLts in the database
        List<MedidasLts> medidasLtss = medidasLtsRepository.findAll();
        assertThat(medidasLtss).hasSize(databaseSizeBeforeUpdate);
        MedidasLts testMedidasLts = medidasLtss.get(medidasLtss.size() - 1);
        assertThat(testMedidasLts.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testMedidasLts.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
    }

    @Test
    @Transactional
    public void deleteMedidasLts() throws Exception {
        // Initialize the database
        medidasLtsRepository.saveAndFlush(medidasLts);
		
		int databaseSizeBeforeDelete = medidasLtsRepository.findAll().size();

        // Get the medidasLts
        restMedidasLtsMockMvc.perform(delete("/api/medidasLtss/{id}", medidasLts.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<MedidasLts> medidasLtss = medidasLtsRepository.findAll();
        assertThat(medidasLtss).hasSize(databaseSizeBeforeDelete - 1);
    }
}
