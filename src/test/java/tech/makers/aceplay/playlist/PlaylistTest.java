package tech.makers.aceplay.playlist;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
// https://www.youtube.com/watch?v=L4vkcgRnw2g&t=1099s
class PlaylistTest {
  @Test
  void testConstructs() {
    Playlist subject = new Playlist("Hello, world!");
    assertEquals("Hello, world!", subject.getName());
    assertEquals(null, subject.getId());
  }

  @Test
  void testToString() {
    Playlist subject = new Playlist("Hello, world!");
    assertEquals(
        "Playlist[id=null name='Hello, world!']",
        subject.toString());
  }
}

