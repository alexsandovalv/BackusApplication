package edu.upc.backus.web.rest;

import edu.upc.backus.Application;
import edu.upc.backus.domain.CapacidadProduccion;
import edu.upc.backus.repository.CapacidadProduccionRepository;

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
 * Test class for the CapacidadProduccionResource REST controller.
 *
 * @see CapacidadProduccionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CapacidadProduccionResourceTest {


    private static final BigDecimal DEFAULT_CAPACIDAD = BigDecimal.ZERO;
    private static final BigDecimal UPDATED_CAPACIDAD = BigDecimal.ONE;

    private static final BigDecimal DEFAULT_VELOCIDAD = BigDecimal.ZERO;
    private static final BigDecimal UPDATED_VELOCIDAD = BigDecimal.ONE;

    @Inject
    private CapacidadProduccionRepository capacidadProduccionRepository;

    private MockMvc restCapacidadProduccionMockMvc;

    private CapacidadProduccion capacidadProduccion;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CapacidadProduccionResource capacidadProduccionResource = new CapacidadProduccionResource();
        ReflectionTestUtils.setField(capacidadProduccionResource, "capacidadProduccionRepository", capacidadProduccionRepository);
        this.restCapacidadProduccionMockMvc = MockMvcBuilders.standaloneSetup(capacidadProduccionResource).build();
    }

    @Before
    public void initTest() {
        capacidadProduccion = new CapacidadProduccion();
        capacidadProduccion.setCapacidad(DEFAULT_CAPACIDAD);
        capacidadProduccion.setVelocidad(DEFAULT_VELOCIDAD);
    }

    @Test
    @Transactional
    public void createCapacidadProduccion() throws Exception {
        int databaseSizeBeforeCreate = capacidadProduccionRepository.findAll().size();

        // Create the CapacidadProduccion
        restCapacidadProduccionMockMvc.perform(post("/api/capacidadProduccions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(capacidadProduccion)))
                .andExpect(status().isCreated());

        // Validate the CapacidadProduccion in the database
        List<CapacidadProduccion> capacidadProduccions = capacidadProduccionRepository.findAll();
        assertThat(capacidadProduccions).hasSize(databaseSizeBeforeCreate + 1);
        CapacidadProduccion testCapacidadProduccion = capacidadProduccions.get(capacidadProduccions.size() - 1);
        assertThat(testCapacidadProduccion.getCapacidad()).isEqualTo(DEFAULT_CAPACIDAD);
        assertThat(testCapacidadProduccion.getVelocidad()).isEqualTo(DEFAULT_VELOCIDAD);
    }

    @Test
    @Transactional
    public void checkCapacidadIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(capacidadProduccionRepository.findAll()).hasSize(0);
        // set the field null
        capacidadProduccion.setCapacidad(null);

        // Create the CapacidadProduccion, which fails.
        restCapacidadProduccionMockMvc.perform(post("/api/capacidadProduccions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(capacidadProduccion)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<CapacidadProduccion> capacidadProduccions = capacidadProduccionRepository.findAll();
        assertThat(capacidadProduccions).hasSize(0);
    }

    @Test
    @Transactional
    public void checkVelocidadIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(capacidadProduccionRepository.findAll()).hasSize(0);
        // set the field null
        capacidadProduccion.setVelocidad(null);

        // Create the CapacidadProduccion, which fails.
        restCapacidadProduccionMockMvc.perform(post("/api/capacidadProduccions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(capacidadProduccion)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<CapacidadProduccion> capacidadProduccions = capacidadProduccionRepository.findAll();
        assertThat(capacidadProduccions).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllCapacidadProduccions() throws Exception {
        // Initialize the database
        capacidadProduccionRepository.saveAndFlush(capacidadProduccion);

        // Get all the capacidadProduccions
        restCapacidadProduccionMockMvc.perform(get("/api/capacidadProduccions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(capacidadProduccion.getId().intValue())))
                .andExpect(jsonPath("$.[*].capacidad").value(hasItem(DEFAULT_CAPACIDAD.intValue())))
                .andExpect(jsonPath("$.[*].velocidad").value(hasItem(DEFAULT_VELOCIDAD.intValue())));
    }

    @Test
    @Transactional
    public void getCapacidadProduccion() throws Exception {
        // Initialize the database
        capacidadProduccionRepository.saveAndFlush(capacidadProduccion);

        // Get the capacidadProduccion
        restCapacidadProduccionMockMvc.perform(get("/api/capacidadProduccions/{id}", capacidadProduccion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(capacidadProduccion.getId().intValue()))
            .andExpect(jsonPath("$.capacidad").value(DEFAULT_CAPACIDAD.intValue()))
            .andExpect(jsonPath("$.velocidad").value(DEFAULT_VELOCIDAD.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCapacidadProduccion() throws Exception {
        // Get the capacidadProduccion
        restCapacidadProduccionMockMvc.perform(get("/api/capacidadProduccions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCapacidadProduccion() throws Exception {
        // Initialize the database
        capacidadProduccionRepository.saveAndFlush(capacidadProduccion);
		
		int databaseSizeBeforeUpdate = capacidadProduccionRepository.findAll().size();

        // Update the capacidadProduccion
        capacidadProduccion.setCapacidad(UPDATED_CAPACIDAD);
        capacidadProduccion.setVelocidad(UPDATED_VELOCIDAD);
        restCapacidadProduccionMockMvc.perform(put("/api/capacidadProduccions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(capacidadProduccion)))
                .andExpect(status().isOk());

        // Validate the CapacidadProduccion in the database
        List<CapacidadProduccion> capacidadProduccions = capacidadProduccionRepository.findAll();
        assertThat(capacidadProduccions).hasSize(databaseSizeBeforeUpdate);
        CapacidadProduccion testCapacidadProduccion = capacidadProduccions.get(capacidadProduccions.size() - 1);
        assertThat(testCapacidadProduccion.getCapacidad()).isEqualTo(UPDATED_CAPACIDAD);
        assertThat(testCapacidadProduccion.getVelocidad()).isEqualTo(UPDATED_VELOCIDAD);
    }

    @Test
    @Transactional
    public void deleteCapacidadProduccion() throws Exception {
        // Initialize the database
        capacidadProduccionRepository.saveAndFlush(capacidadProduccion);
		
		int databaseSizeBeforeDelete = capacidadProduccionRepository.findAll().size();

        // Get the capacidadProduccion
        restCapacidadProduccionMockMvc.perform(delete("/api/capacidadProduccions/{id}", capacidadProduccion.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CapacidadProduccion> capacidadProduccions = capacidadProduccionRepository.findAll();
        assertThat(capacidadProduccions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
