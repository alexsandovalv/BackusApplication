package edu.upc.backus.web.rest;

import edu.upc.backus.Application;
import edu.upc.backus.domain.Maquina;
import edu.upc.backus.repository.MaquinaRepository;

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
 * Test class for the MaquinaResource REST controller.
 *
 * @see MaquinaResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class MaquinaResourceTest {


    private static final BigDecimal DEFAULT_COSTO = BigDecimal.ZERO;
    private static final BigDecimal UPDATED_COSTO = BigDecimal.ONE;
    private static final String DEFAULT_NOMBRE = "SAMPLE_TEXT";
    private static final String UPDATED_NOMBRE = "UPDATED_TEXT";

    @Inject
    private MaquinaRepository maquinaRepository;

    private MockMvc restMaquinaMockMvc;

    private Maquina maquina;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MaquinaResource maquinaResource = new MaquinaResource();
        ReflectionTestUtils.setField(maquinaResource, "maquinaRepository", maquinaRepository);
        this.restMaquinaMockMvc = MockMvcBuilders.standaloneSetup(maquinaResource).build();
    }

    @Before
    public void initTest() {
        maquina = new Maquina();
        maquina.setCosto(DEFAULT_COSTO);
        maquina.setNombre(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void createMaquina() throws Exception {
        int databaseSizeBeforeCreate = maquinaRepository.findAll().size();

        // Create the Maquina
        restMaquinaMockMvc.perform(post("/api/maquinas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(maquina)))
                .andExpect(status().isCreated());

        // Validate the Maquina in the database
        List<Maquina> maquinas = maquinaRepository.findAll();
        assertThat(maquinas).hasSize(databaseSizeBeforeCreate + 1);
        Maquina testMaquina = maquinas.get(maquinas.size() - 1);
        assertThat(testMaquina.getCosto()).isEqualTo(DEFAULT_COSTO);
        assertThat(testMaquina.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(maquinaRepository.findAll()).hasSize(0);
        // set the field null
        maquina.setNombre(null);

        // Create the Maquina, which fails.
        restMaquinaMockMvc.perform(post("/api/maquinas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(maquina)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Maquina> maquinas = maquinaRepository.findAll();
        assertThat(maquinas).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllMaquinas() throws Exception {
        // Initialize the database
        maquinaRepository.saveAndFlush(maquina);

        // Get all the maquinas
        restMaquinaMockMvc.perform(get("/api/maquinas"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(maquina.getId().intValue())))
                .andExpect(jsonPath("$.[*].costo").value(hasItem(DEFAULT_COSTO.intValue())))
                .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())));
    }

    @Test
    @Transactional
    public void getMaquina() throws Exception {
        // Initialize the database
        maquinaRepository.saveAndFlush(maquina);

        // Get the maquina
        restMaquinaMockMvc.perform(get("/api/maquinas/{id}", maquina.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(maquina.getId().intValue()))
            .andExpect(jsonPath("$.costo").value(DEFAULT_COSTO.intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMaquina() throws Exception {
        // Get the maquina
        restMaquinaMockMvc.perform(get("/api/maquinas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMaquina() throws Exception {
        // Initialize the database
        maquinaRepository.saveAndFlush(maquina);
		
		int databaseSizeBeforeUpdate = maquinaRepository.findAll().size();

        // Update the maquina
        maquina.setCosto(UPDATED_COSTO);
        maquina.setNombre(UPDATED_NOMBRE);
        restMaquinaMockMvc.perform(put("/api/maquinas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(maquina)))
                .andExpect(status().isOk());

        // Validate the Maquina in the database
        List<Maquina> maquinas = maquinaRepository.findAll();
        assertThat(maquinas).hasSize(databaseSizeBeforeUpdate);
        Maquina testMaquina = maquinas.get(maquinas.size() - 1);
        assertThat(testMaquina.getCosto()).isEqualTo(UPDATED_COSTO);
        assertThat(testMaquina.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void deleteMaquina() throws Exception {
        // Initialize the database
        maquinaRepository.saveAndFlush(maquina);
		
		int databaseSizeBeforeDelete = maquinaRepository.findAll().size();

        // Get the maquina
        restMaquinaMockMvc.perform(delete("/api/maquinas/{id}", maquina.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Maquina> maquinas = maquinaRepository.findAll();
        assertThat(maquinas).hasSize(databaseSizeBeforeDelete - 1);
    }
}
