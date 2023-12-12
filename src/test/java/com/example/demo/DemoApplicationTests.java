package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = NoteController.class)
@Import(TestConfig.class)
public class DemoApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private NoteRepository noteRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	public void setUp() {
		// Dodaj przykładową notatkę do bazy danych przed każdym testem
		Notes sampleNote = new Notes("Test Title", "Test Content", "testuser");
		when(noteRepository.save(sampleNote)).thenReturn(sampleNote);

		// Pobierz notatkę po ID
		when(noteRepository.findById(sampleNote.getId())).thenReturn(java.util.Optional.of(sampleNote));

		// Pobierz wszystkie notatki
		when(noteRepository.findAll()).thenReturn(Collections.singletonList(sampleNote));
	}

	@AfterEach
	public void tearDown() {
		// Wyczyść bazę danych po każdym teście
		noteRepository.deleteAll();
	}

	@Test
	public void shouldReturnAllNotes() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/notes/"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1));
	}

	@Test
	public void shouldReturnNoteById() throws Exception {
		Notes note = noteRepository.findAll().get(0);
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/notes/" + note.getId()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(note.getId().toString()));
	}

	@Test
	public void shouldCreateNote() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/notes/")
						.content(objectMapper.writeValueAsString(new Notes("New Note", "New Content", "testuser")))
						.contentType("application/json"))
				.andExpect(MockMvcResultMatchers.status().isCreated());

		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/notes/"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));
	}
}
