package tech.makers.aceplay.track;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

import static org.springframework.http.HttpStatus.NOT_FOUND;

// https://www.youtube.com/watch?v=5r3QU09v7ig&t=2410s
@RestController
public class TracksController {
  @Autowired private TrackRepository trackRepository;

  @Autowired private TracksService tracksService;

  @GetMapping("/api/tracks")
  public Iterable<Track> index() {
    return tracksService.index();
  }

  @PostMapping("/api/tracks")
  public Track createAndAddUser(@RequestBody Track track) {
      tracksService.validateAndSaveTrack(track);
      return tracksService.addUserOfTrack(track.getId());
  }

  @PatchMapping("/api/tracks/{id}")
  public Track update(@PathVariable Long id, @RequestBody Track newTrack) {
    return tracksService.updateTrack(id, newTrack);
  }

  @PutMapping("/api/tracks/{id}")
  public Track addUserOfTrack(@PathVariable Long id) {
    return tracksService.addUserOfTrack(id);
  }

  @DeleteMapping("/api/tracks/{id}")
  public void delete(@PathVariable Long id) {
    tracksService.deleteTrack(id);
  }

  @GetMapping("/api/tracks/userLibraryIndex")
  public Set<Track> userLibraryIndex() {
    return tracksService.userLibraryIndex();
  }
}
