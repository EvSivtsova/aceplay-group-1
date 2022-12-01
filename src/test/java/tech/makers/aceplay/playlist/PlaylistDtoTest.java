package tech.makers.aceplay.playlist;

import org.junit.jupiter.api.Test;
import tech.makers.aceplay.track.TrackDto;

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
    TrackDto mockPlaylistTrack1 = mock(TrackDto.class);
    TrackDto mockPlaylistTrack2 = mock(TrackDto.class);
    Set<TrackDto> mockTracks = new HashSet<>();
    mockTracks.add(mockPlaylistTrack1);
    mockTracks.add(mockPlaylistTrack2);
    Long mockId = 1L;

    PlaylistDto subject = new PlaylistDto(mockId, "Hello, world!", mockTracks);
    assertEquals("Hello, world!", subject.getName());
  }
}

