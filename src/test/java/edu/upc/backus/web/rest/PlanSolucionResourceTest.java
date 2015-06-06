package edu.upc.backus.web.rest;

import edu.upc.backus.Application;
import edu.upc.backus.domain.PlanSolucion;
import edu.upc.backus.repository.PlanSolucionRepository;

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
 * Test class for the PlanSolucionResource REST controller.
 *
 * @see PlanSolucionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PlanSolucionResourceTest {

    private static final String DEFAULT_DIA_TRABAJADA = "SAMPLE_TEXT";
    private static final String UPDATED_DIA_TRABAJADA = "UPDATED_TEXT";

    private static final BigDecimal DEFAULT_CANTIDAD = BigDecimal.ZERO;
    private static final BigDecimal UPDATED_CANTIDAD = BigDecimal.ONE;

    @Inject
    private PlanSolucionRepository planSolucionRepository;

    private MockMvc restPlanSolucionMockMvc;

    private PlanSolucion planSolucion;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PlanSolucionResource planSolucionResource = new PlanSolucionResource();
        ReflectionTestUtils.setField(planSolucionResource, "planSolucionRepository", planSolucionRepository);
        this.restPlanSolucionMockMvc = MockMvcBuilders.standaloneSetup(planSolucionResource).build();
    }

    @Before
    public void initTest() {
        planSolucion = new PlanSolucion();
        planSolucion.setDiaTrabajada(DEFAULT_DIA_TRABAJADA);
        planSolucion.setCantidad(DEFAULT_CANTIDAD);
    }

    @Test
    @Transactional
    public void createPlanSolucion() throws Exception {
        int databaseSizeBeforeCreate = planSolucionRepository.findAll().size();

        // Create the PlanSolucion
        restPlanSolucionMockMvc.perform(post("/api/planSolucions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(planSolucion)))
                .andExpect(status().isCreated());

        // Validate the PlanSolucion in the database
        List<PlanSolucion> planSolucions = planSolucionRepository.findAll();
        assertThat(planSolucions).hasSize(databaseSizeBeforeCreate + 1);
        PlanSolucion testPlanSolucion = planSolucions.get(planSolucions.size() - 1);
        assertThat(testPlanSolucion.getDiaTrabajada()).isEqualTo(DEFAULT_DIA_TRABAJADA);
        assertThat(testPlanSolucion.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
    }

    @Test
    @Transactional
    public void getAllPlanSolucions() throws Exception {
        // Initialize the database
        planSolucionRepository.saveAndFlush(planSolucion);

        // Get all the planSolucions
        restPlanSolucionMockMvc.perform(get("/api/planSolucions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(planSolucion.getId().intValue())))
                .andExpect(jsonPath("$.[*].diaTrabajada").value(hasItem(DEFAULT_DIA_TRABAJADA.toString())))
                .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD.intValue())));
    }

    @Test
    @Transactional
    public void getPlanSolucion() throws Exception {
        // Initialize the database
        planSolucionRepository.saveAndFlush(planSolucion);

        // Get the planSolucion
        restPlanSolucionMockMvc.perform(get("/api/planSolucions/{id}", planSolucion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(planSolucion.getId().intValue()))
            .andExpect(jsonPath("$.diaTrabajada").value(DEFAULT_DIA_TRABAJADA.toString()))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPlanSolucion() throws Exception {
        // Get the planSolucion
        restPlanSolucionMockMvc.perform(get("/api/planSolucions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlanSolucion() throws Exception {
        // Initialize the database
        planSolucionRepository.saveAndFlush(planSolucion);
		
		int databaseSizeBeforeUpdate = planSolucionRepository.findAll().size();

        // Update the planSolucion
        planSolucion.setDiaTrabajada(UPDATED_DIA_TRABAJADA);
        planSolucion.setCantidad(UPDATED_CANTIDAD);
        restPlanSolucionMockMvc.perform(put("/api/planSolucions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(planSolucion)))
                .andExpect(status().isOk());

        // Validate the PlanSolucion in the database
        List<PlanSolucion> planSolucions = planSolucionRepository.findAll();
        assertThat(planSolucions).hasSize(databaseSizeBeforeUpdate);
        PlanSolucion testPlanSolucion = planSolucions.get(planSolucions.size() - 1);
        assertThat(testPlanSolucion.getDiaTrabajada()).isEqualTo(UPDATED_DIA_TRABAJADA);
        assertThat(testPlanSolucion.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
    }

    @Test
    @Transactional
    public void deletePlanSolucion() throws Exception {
        // Initialize the database
        planSolucionRepository.saveAndFlush(planSolucion);
		
		int databaseSizeBeforeDelete = planSolucionRepository.findAll().size();

        // Get the planSolucion
        restPlanSolucionMockMvc.perform(delete("/api/planSolucions/{id}", planSolucion.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PlanSolucion> planSolucions = planSolucionRepository.findAll();
        assertThat(planSolucions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
