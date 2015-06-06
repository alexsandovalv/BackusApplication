package edu.upc.backus.web.rest;

import edu.upc.backus.Application;
import edu.upc.backus.domain.ProgramaProduccion;
import edu.upc.backus.repository.ProgramaProduccionRepository;

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
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ProgramaProduccionResource REST controller.
 *
 * @see ProgramaProduccionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ProgramaProduccionResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");


    private static final BigDecimal DEFAULT_CANTIDAD = BigDecimal.ZERO;
    private static final BigDecimal UPDATED_CANTIDAD = BigDecimal.ONE;
    private static final String DEFAULT_DIA_TRABAJADA = "SAMPLE_TEXT";
    private static final String UPDATED_DIA_TRABAJADA = "UPDATED_TEXT";

    private static final DateTime DEFAULT_FECHA_PROGRAMADA = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_FECHA_PROGRAMADA = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_FECHA_PROGRAMADA_STR = dateTimeFormatter.print(DEFAULT_FECHA_PROGRAMADA);

    @Inject
    private ProgramaProduccionRepository programaProduccionRepository;

    private MockMvc restProgramaProduccionMockMvc;

    private ProgramaProduccion programaProduccion;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProgramaProduccionResource programaProduccionResource = new ProgramaProduccionResource();
        ReflectionTestUtils.setField(programaProduccionResource, "programaProduccionRepository", programaProduccionRepository);
        this.restProgramaProduccionMockMvc = MockMvcBuilders.standaloneSetup(programaProduccionResource).build();
    }

    @Before
    public void initTest() {
        programaProduccion = new ProgramaProduccion();
        programaProduccion.setCantidad(DEFAULT_CANTIDAD);
        programaProduccion.setDiaTrabajada(DEFAULT_DIA_TRABAJADA);
        programaProduccion.setFechaProgramada(DEFAULT_FECHA_PROGRAMADA);
    }

    @Test
    @Transactional
    public void createProgramaProduccion() throws Exception {
        int databaseSizeBeforeCreate = programaProduccionRepository.findAll().size();

        // Create the ProgramaProduccion
        restProgramaProduccionMockMvc.perform(post("/api/programaProduccions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(programaProduccion)))
                .andExpect(status().isCreated());

        // Validate the ProgramaProduccion in the database
        List<ProgramaProduccion> programaProduccions = programaProduccionRepository.findAll();
        assertThat(programaProduccions).hasSize(databaseSizeBeforeCreate + 1);
        ProgramaProduccion testProgramaProduccion = programaProduccions.get(programaProduccions.size() - 1);
        assertThat(testProgramaProduccion.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
        assertThat(testProgramaProduccion.getDiaTrabajada()).isEqualTo(DEFAULT_DIA_TRABAJADA);
        assertThat(testProgramaProduccion.getFechaProgramada().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_FECHA_PROGRAMADA);
    }

    @Test
    @Transactional
    public void checkCantidadIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(programaProduccionRepository.findAll()).hasSize(0);
        // set the field null
        programaProduccion.setCantidad(null);

        // Create the ProgramaProduccion, which fails.
        restProgramaProduccionMockMvc.perform(post("/api/programaProduccions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(programaProduccion)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<ProgramaProduccion> programaProduccions = programaProduccionRepository.findAll();
        assertThat(programaProduccions).hasSize(0);
    }

    @Test
    @Transactional
    public void checkDiaTrabajadaIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(programaProduccionRepository.findAll()).hasSize(0);
        // set the field null
        programaProduccion.setDiaTrabajada(null);

        // Create the ProgramaProduccion, which fails.
        restProgramaProduccionMockMvc.perform(post("/api/programaProduccions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(programaProduccion)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<ProgramaProduccion> programaProduccions = programaProduccionRepository.findAll();
        assertThat(programaProduccions).hasSize(0);
    }

    @Test
    @Transactional
    public void checkFechaProgramadaIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(programaProduccionRepository.findAll()).hasSize(0);
        // set the field null
        programaProduccion.setFechaProgramada(null);

        // Create the ProgramaProduccion, which fails.
        restProgramaProduccionMockMvc.perform(post("/api/programaProduccions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(programaProduccion)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<ProgramaProduccion> programaProduccions = programaProduccionRepository.findAll();
        assertThat(programaProduccions).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllProgramaProduccions() throws Exception {
        // Initialize the database
        programaProduccionRepository.saveAndFlush(programaProduccion);

        // Get all the programaProduccions
        restProgramaProduccionMockMvc.perform(get("/api/programaProduccions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(programaProduccion.getId().intValue())))
                .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD.intValue())))
                .andExpect(jsonPath("$.[*].diaTrabajada").value(hasItem(DEFAULT_DIA_TRABAJADA.toString())))
                .andExpect(jsonPath("$.[*].fechaProgramada").value(hasItem(DEFAULT_FECHA_PROGRAMADA_STR)));
    }

    @Test
    @Transactional
    public void getProgramaProduccion() throws Exception {
        // Initialize the database
        programaProduccionRepository.saveAndFlush(programaProduccion);

        // Get the programaProduccion
        restProgramaProduccionMockMvc.perform(get("/api/programaProduccions/{id}", programaProduccion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(programaProduccion.getId().intValue()))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD.intValue()))
            .andExpect(jsonPath("$.diaTrabajada").value(DEFAULT_DIA_TRABAJADA.toString()))
            .andExpect(jsonPath("$.fechaProgramada").value(DEFAULT_FECHA_PROGRAMADA_STR));
    }

    @Test
    @Transactional
    public void getNonExistingProgramaProduccion() throws Exception {
        // Get the programaProduccion
        restProgramaProduccionMockMvc.perform(get("/api/programaProduccions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProgramaProduccion() throws Exception {
        // Initialize the database
        programaProduccionRepository.saveAndFlush(programaProduccion);
		
		int databaseSizeBeforeUpdate = programaProduccionRepository.findAll().size();

        // Update the programaProduccion
        programaProduccion.setCantidad(UPDATED_CANTIDAD);
        programaProduccion.setDiaTrabajada(UPDATED_DIA_TRABAJADA);
        programaProduccion.setFechaProgramada(UPDATED_FECHA_PROGRAMADA);
        restProgramaProduccionMockMvc.perform(put("/api/programaProduccions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(programaProduccion)))
                .andExpect(status().isOk());

        // Validate the ProgramaProduccion in the database
        List<ProgramaProduccion> programaProduccions = programaProduccionRepository.findAll();
        assertThat(programaProduccions).hasSize(databaseSizeBeforeUpdate);
        ProgramaProduccion testProgramaProduccion = programaProduccions.get(programaProduccions.size() - 1);
        assertThat(testProgramaProduccion.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testProgramaProduccion.getDiaTrabajada()).isEqualTo(UPDATED_DIA_TRABAJADA);
        assertThat(testProgramaProduccion.getFechaProgramada().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_FECHA_PROGRAMADA);
    }

    @Test
    @Transactional
    public void deleteProgramaProduccion() throws Exception {
        // Initialize the database
        programaProduccionRepository.saveAndFlush(programaProduccion);
		
		int databaseSizeBeforeDelete = programaProduccionRepository.findAll().size();

        // Get the programaProduccion
        restProgramaProduccionMockMvc.perform(delete("/api/programaProduccions/{id}", programaProduccion.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ProgramaProduccion> programaProduccions = programaProduccionRepository.findAll();
        assertThat(programaProduccions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
