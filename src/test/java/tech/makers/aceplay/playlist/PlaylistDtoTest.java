package tech.makers.aceplay.playlist;

import org.junit.jupiter.api.Test;
import tech.makers.aceplay.playlist_track.PlaylistTrack;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

// https://www.youtube.com/watch?v=L4vkcgRnw2g&t=1099s
class PlaylistDtoTest {
  @Test
  void testSingleParameterConstructor() {
    PlaylistDto subject = new PlaylistDto("Hello, world!");
    assertEquals("Hello, world!", subject.getName());
  }

  @Test
  void testThreeParameterConstructor() {
    PlaylistTrack mockPlaylistTrack1 = mock(PlaylistTrack.class);
    PlaylistTrack mockPlaylistTrack2 = mock(PlaylistTrack.class);
    Set<PlaylistTrack> mockTracks = new HashSet<>();
    mockTracks.add(mockPlaylistTrack1);
    mockTracks.add(mockPlaylistTrack2);
    Long mockId = 1L;

    PlaylistDto subject = new PlaylistDto(mockId, "Hello, world!", mockTracks);
    assertEquals("Hello, world!", subject.getName());
  }
}

