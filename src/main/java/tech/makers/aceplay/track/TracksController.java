package tech.makers.aceplay.track;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
  public Track create(@RequestBody Track track) {
      return tracksService.validateAndSaveTrack(track);
  }

  @PatchMapping("/api/tracks/{id}")
  public Track update(@PathVariable Long id, @RequestBody Track newTrack) {
    return tracksService.updateTrack(id, newTrack);
  }

  @DeleteMapping("/api/tracks/{id}")
  public void delete(@PathVariable Long id) {
    tracksService.deleteTrack(id);
  }
}
