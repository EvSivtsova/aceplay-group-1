package tech.makers.aceplay.track;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import tech.makers.aceplay.track.Track;
import tech.makers.aceplay.track.TrackRepository;
import tech.makers.aceplay.user.User;
import tech.makers.aceplay.user.UserRepository;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// https://www.youtube.com/watch?v=L4vkcgRnw2g&t=908s
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TracksControllerIntegrationTest {
  @Autowired private MockMvc mvc;

  @Autowired private TrackRepository repository;

  @Autowired private UserRepository userRepository;

  @Autowired private TracksService tracksService;
  @Test
  @WithMockUser
  void WhenLoggedIn_AndThereAreNoTracks_TracksIndexReturnsNoTracks() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/api/tracks").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$", hasSize(0)));
  }

  @Test
  @WithMockUser
  void WhenLoggedIn_AndThereAreTracks_TracksIndexReturnsTracks() throws Exception {
    repository.save(new Track("Blue Line Swinger", "Yo La Tengo", "http://example.org/track.mp3"));
    repository.save(new Track("Morning Light", "Girls", "http://example.org/track.mp3"));

    mvc.perform(MockMvcRequestBuilders.get("/api/tracks").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].title").value("Blue Line Swinger"))
        .andExpect(jsonPath("$[0].artist").value("Yo La Tengo"))
        .andExpect(jsonPath("$[0].publicUrl").value("http://example.org/track.mp3"))
        .andExpect(jsonPath("$[1].title").value("Morning Light"));
  }

  @Test
  void WhenLoggedOut_TracksIndexReturnsForbidden() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/api/tracks").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser
  void WhenLoggedIn_TracksPostCreatesNewTrack() throws Exception {
    User user = userRepository.save(new User("user", "password"));

    mvc.perform(
            MockMvcRequestBuilders.post("/api/tracks")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\": \"Blue Line Swinger\", \"artist\": \"Yo La Tengo\", \"publicUrl\": \"https://example.org/track.mp3\"}"))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.title").value("Blue Line Swinger"))
        .andExpect(jsonPath("$.artist").value("Yo La Tengo"))
        .andExpect(jsonPath("$.publicUrl").value("https://example.org/track.mp3"));

    Track track = repository.findFirstByOrderByIdAsc();
    assertEquals("Blue Line Swinger", track.getTitle());
    assertEquals("https://example.org/track.mp3", track.getPublicUrl().toString());

    assertEquals(user.getId(), track.getUsers().iterator().next().getId());
    assertEquals(user.getPassword(), track.getUsers().iterator().next().getPassword());
    assertEquals(user.getUsername(), track.getUsers().iterator().next().getUsername());
  }

  @Test
  @WithMockUser
  void TracksAreNotSavedToTheDataBase_WhenTitleIsEmpty() throws Exception {
    mvc.perform(
            MockMvcRequestBuilders.post("/api/tracks")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"title\": \"\", \"artist\": \"Yo La Tengo\", \"publicUrl\": \"https://example.org/track.mp3\"}"))
            .andExpect(status().isBadRequest())
            .andExpect(result -> assertTrue(result.getResolvedException() instanceof IllegalArgumentException))
            .andExpect(result -> assertEquals("Track title cannot be empty", Objects.requireNonNull(result.getResolvedException()).getMessage()));

    assertEquals(0, repository.count());
  }

  @Test
  @WithMockUser
  void TracksAreNotSavedToTheDataBase_WhenArtistIsEmpty() throws Exception {
    mvc.perform(
                    MockMvcRequestBuilders.post("/api/tracks")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"title\": \"example track\", \"artist\": \"\", \"publicUrl\": \"https://example.org/track.mp3\"}"))
            .andExpect(status().isBadRequest())
            .andExpect(result -> assertTrue(result.getResolvedException() instanceof IllegalArgumentException))
            .andExpect(result -> assertEquals("Track artist cannot be empty", Objects.requireNonNull(result.getResolvedException()).getMessage()));

    assertEquals(0, repository.count());
  }

  @Test
  void WhenLoggedOut_TrackPostIsForbidden() throws Exception {
    mvc.perform(
        MockMvcRequestBuilders.post("/api/tracks")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"title\": \"Blue Line Swinger\"}"))
        .andExpect(status().isForbidden());
    assertEquals(0, repository.count());
  }

  @Test
  @WithMockUser
  void WhenLoggedIn_TrackUpdateUpdatesTrack() throws Exception {
    Track track = repository.save(new Track("Blue Line Swinger", "Yo La Tengo", "https://example.org/track.mp3"));
    mvc.perform(
            MockMvcRequestBuilders.patch("/api/tracks/" + track.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\": \"Stars\", \"artist\": \"Hum\"}"))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.title").value("Stars"))
        .andExpect(jsonPath("$.artist").value("Hum"))
        .andExpect(jsonPath("$.publicUrl").value("https://example.org/track.mp3"));

    Track updatedTrack = repository.findById(track.getId()).orElseThrow();
    assertEquals("Stars", updatedTrack.getTitle());
  }

  @Test
  @WithMockUser
  void WhenLoggedIn_ButNoTrack_TrackUpdateThrows404() throws Exception {
    mvc.perform(
            MockMvcRequestBuilders.patch("/api/tracks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\": \"Stars\"}"))
        .andExpect(status().isNotFound());
  }

  @Test
  void WhenLoggedOut_TrackUpdateIsForbidden() throws Exception {
    Track track = repository.save(new Track("Blue Line Swinger", "Yo La Tengo", "http://example.org/track.mp3"));
    mvc.perform(
            MockMvcRequestBuilders.patch("/api/tracks/" + track.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\": \"Stars\"}"))
        .andExpect(status().isForbidden());

    Track updatedTrack = repository.findById(track.getId()).orElseThrow();
    assertEquals("Blue Line Swinger", updatedTrack.getTitle());
  }


  @Test
  @WithMockUser
  void WhenLoggedIn_TrackDeleteDeletesTrack() throws Exception {
    Track track = repository.save(new Track("Blue Line Swinger", "Yo La Tengo", "https://example.org/track.mp3"));

    mvc.perform(
            MockMvcRequestBuilders.delete("/api/tracks/" + track.getId()))
        .andExpect(status().isOk());

    assertEquals(0, repository.count());
  }

  @Test
  @WithMockUser
  void WhenLoggedIn_ButNoTrack_TrackDeleteThrows404() throws Exception {
    mvc.perform(
            MockMvcRequestBuilders.delete("/api/tracks/1"))
        .andExpect(status().isNotFound());
  }

  @Test
  void WhenLoggedOut_TrackDeleteIsForbidden() throws Exception {
    Track track = repository.save(new Track("Blue Line Swinger", "Yo La Tengo", "http://example.org/track.mp3"));

    mvc.perform(
            MockMvcRequestBuilders.delete("/api/tracks/" + track.getId()))
        .andExpect(status().isForbidden());

    assertEquals(1, repository.count());
  }

  @Test
  @WithMockUser
  void WhenLoggedIn_addUserOfTrack_addsUserToUsers() throws Exception {
    Track track = repository.save(new Track("Blue Line Swinger", "Yo La Tengo", "https://example.org/track.mp3"));
    User user = userRepository.save(new User("user", "password"));

    mvc.perform(
                    MockMvcRequestBuilders.put("/api/tracks/" + track.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.title").value("Blue Line Swinger"))
            .andExpect(jsonPath("$.artist").value("Yo La Tengo"))
            .andExpect(jsonPath("$.publicUrl").value("https://example.org/track.mp3"))
            .andExpect(jsonPath("$.users[0].username").value("user"))
            .andExpect(jsonPath("$.users[0].id").value(user.getId())
            );

    Track updatedTrack = repository.findFirstByOrderByIdAsc();
    assertEquals(user.getId(), updatedTrack.getUsers().iterator().next().getId());
    assertEquals(user.getPassword(), updatedTrack.getUsers().iterator().next().getPassword());
    assertEquals(user.getUsername(), updatedTrack.getUsers().iterator().next().getUsername());
  }

  @Test
  @WithMockUser
  void  WhenLoggedIn_userOnlySeesTheirSavedSongs() throws Exception {
    Track track = repository.save(new Track("Blue Line Swinger", "Yo La Tengo", "https://example.org/track.mp3"));
    User user = userRepository.save(new User("user", "password"));
    tracksService.addUserOfTrack(track.getId());
    mvc.perform(
            MockMvcRequestBuilders
                    .get("/api/tracks/userLibraryIndex")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("[{\"title\": \"example track\", \"artist\": \"\", \"publicUrl\": \"https://example.org/track.mp3\"}]")
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("Blue Line Swinger"))
            .andExpect(jsonPath("$.artist").value("Yo La Tengo"))
            .andExpect(jsonPath("$.publicUrl").value("https://example.org/track.mp3"));
  }
}
