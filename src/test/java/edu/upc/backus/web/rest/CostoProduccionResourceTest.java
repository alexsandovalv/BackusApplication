package edu.upc.backus.web.rest;

import edu.upc.backus.Application;
import edu.upc.backus.domain.CostoProduccion;
import edu.upc.backus.repository.CostoProduccionRepository;

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
 * Test class for the CostoProduccionResource REST controller.
 *
 * @see CostoProduccionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CostoProduccionResourceTest {


    private static final BigDecimal DEFAULT_MANO_OBRA = BigDecimal.ZERO;
    private static final BigDecimal UPDATED_MANO_OBRA = BigDecimal.ONE;

    private static final BigDecimal DEFAULT_HORA_MAQUINA = BigDecimal.ZERO;
    private static final BigDecimal UPDATED_HORA_MAQUINA = BigDecimal.ONE;

    private static final BigDecimal DEFAULT_TOTAL = BigDecimal.ZERO;
    private static final BigDecimal UPDATED_TOTAL = BigDecimal.ONE;

    @Inject
    private CostoProduccionRepository costoProduccionRepository;

    private MockMvc restCostoProduccionMockMvc;

    private CostoProduccion costoProduccion;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CostoProduccionResource costoProduccionResource = new CostoProduccionResource();
        ReflectionTestUtils.setField(costoProduccionResource, "costoProduccionRepository", costoProduccionRepository);
        this.restCostoProduccionMockMvc = MockMvcBuilders.standaloneSetup(costoProduccionResource).build();
    }

    @Before
    public void initTest() {
        costoProduccion = new CostoProduccion();
        costoProduccion.setManoObra(DEFAULT_MANO_OBRA);
        costoProduccion.setHoraMaquina(DEFAULT_HORA_MAQUINA);
        costoProduccion.setTotal(DEFAULT_TOTAL);
    }

    @Test
    @Transactional
    public void createCostoProduccion() throws Exception {
        int databaseSizeBeforeCreate = costoProduccionRepository.findAll().size();

        // Create the CostoProduccion
        restCostoProduccionMockMvc.perform(post("/api/costoProduccions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(costoProduccion)))
                .andExpect(status().isCreated());

        // Validate the CostoProduccion in the database
        List<CostoProduccion> costoProduccions = costoProduccionRepository.findAll();
        assertThat(costoProduccions).hasSize(databaseSizeBeforeCreate + 1);
        CostoProduccion testCostoProduccion = costoProduccions.get(costoProduccions.size() - 1);
        assertThat(testCostoProduccion.getManoObra()).isEqualTo(DEFAULT_MANO_OBRA);
        assertThat(testCostoProduccion.getHoraMaquina()).isEqualTo(DEFAULT_HORA_MAQUINA);
        assertThat(testCostoProduccion.getTotal()).isEqualTo(DEFAULT_TOTAL);
    }

    @Test
    @Transactional
    public void checkManoObraIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(costoProduccionRepository.findAll()).hasSize(0);
        // set the field null
        costoProduccion.setManoObra(null);

        // Create the CostoProduccion, which fails.
        restCostoProduccionMockMvc.perform(post("/api/costoProduccions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(costoProduccion)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<CostoProduccion> costoProduccions = costoProduccionRepository.findAll();
        assertThat(costoProduccions).hasSize(0);
    }

    @Test
    @Transactional
    public void checkHoraMaquinaIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(costoProduccionRepository.findAll()).hasSize(0);
        // set the field null
        costoProduccion.setHoraMaquina(null);

        // Create the CostoProduccion, which fails.
        restCostoProduccionMockMvc.perform(post("/api/costoProduccions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(costoProduccion)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<CostoProduccion> costoProduccions = costoProduccionRepository.findAll();
        assertThat(costoProduccions).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllCostoProduccions() throws Exception {
        // Initialize the database
        costoProduccionRepository.saveAndFlush(costoProduccion);

        // Get all the costoProduccions
        restCostoProduccionMockMvc.perform(get("/api/costoProduccions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(costoProduccion.getId().intValue())))
                .andExpect(jsonPath("$.[*].manoObra").value(hasItem(DEFAULT_MANO_OBRA.intValue())))
                .andExpect(jsonPath("$.[*].horaMaquina").value(hasItem(DEFAULT_HORA_MAQUINA.intValue())))
                .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.intValue())));
    }

    @Test
    @Transactional
    public void getCostoProduccion() throws Exception {
        // Initialize the database
        costoProduccionRepository.saveAndFlush(costoProduccion);

        // Get the costoProduccion
        restCostoProduccionMockMvc.perform(get("/api/costoProduccions/{id}", costoProduccion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(costoProduccion.getId().intValue()))
            .andExpect(jsonPath("$.manoObra").value(DEFAULT_MANO_OBRA.intValue()))
            .andExpect(jsonPath("$.horaMaquina").value(DEFAULT_HORA_MAQUINA.intValue()))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCostoProduccion() throws Exception {
        // Get the costoProduccion
        restCostoProduccionMockMvc.perform(get("/api/costoProduccions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCostoProduccion() throws Exception {
        // Initialize the database
        costoProduccionRepository.saveAndFlush(costoProduccion);
		
		int databaseSizeBeforeUpdate = costoProduccionRepository.findAll().size();

        // Update the costoProduccion
        costoProduccion.setManoObra(UPDATED_MANO_OBRA);
        costoProduccion.setHoraMaquina(UPDATED_HORA_MAQUINA);
        costoProduccion.setTotal(UPDATED_TOTAL);
        restCostoProduccionMockMvc.perform(put("/api/costoProduccions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(costoProduccion)))
                .andExpect(status().isOk());

        // Validate the CostoProduccion in the database
        List<CostoProduccion> costoProduccions = costoProduccionRepository.findAll();
        assertThat(costoProduccions).hasSize(databaseSizeBeforeUpdate);
        CostoProduccion testCostoProduccion = costoProduccions.get(costoProduccions.size() - 1);
        assertThat(testCostoProduccion.getManoObra()).isEqualTo(UPDATED_MANO_OBRA);
        assertThat(testCostoProduccion.getHoraMaquina()).isEqualTo(UPDATED_HORA_MAQUINA);
        assertThat(testCostoProduccion.getTotal()).isEqualTo(UPDATED_TOTAL);
    }

    @Test
    @Transactional
    public void deleteCostoProduccion() throws Exception {
        // Initialize the database
        costoProduccionRepository.saveAndFlush(costoProduccion);
		
		int databaseSizeBeforeDelete = costoProduccionRepository.findAll().size();

        // Get the costoProduccion
        restCostoProduccionMockMvc.perform(delete("/api/costoProduccions/{id}", costoProduccion.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CostoProduccion> costoProduccions = costoProduccionRepository.findAll();
        assertThat(costoProduccions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
