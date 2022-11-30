package tech.makers.aceplay.track;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

// https://www.youtube.com/watch?v=5r3QU09v7ig&t=2410s
@RestController
public class TracksController {
  @Autowired private TrackRepository trackRepository;

  @Autowired private TracksService tracksService;

  @Autowired TrackMapper trackMapper;

  @GetMapping("/api/tracks")
  public Set<TrackDto> index() {
    Set<TrackDto> trackDtos = new HashSet<>();
    tracksService.index().forEach(track -> trackDtos.add(convertToDto(track)));
    return trackDtos;
  }

  @PostMapping("/api/tracks")
  public TrackDto createAndAddUser(@RequestBody TrackDto trackDto) {
      Track track = convertToEntity(trackDto);
      tracksService.validateAndSaveTrack(track);
      tracksService.addUserOfTrack(track.getId());
      return convertToDto(track);
  }

  @PatchMapping("/api/tracks/{id}")
  public TrackDto update(@PathVariable Long id, @RequestBody TrackDto newTrackDto) {
    return convertToDto(tracksService.updateTrack(id, convertToEntity(newTrackDto)));
  }

  @PutMapping("/api/tracks/{id}")
  public TrackDto addUserOfTrack(@PathVariable Long id) {
    return convertToDto(tracksService.addUserOfTrack(id));
  }

  @DeleteMapping("/api/tracks/{id}")
  public void delete(@PathVariable Long id) {
    tracksService.deleteTrack(id);
  }

  private Track convertToEntity(TrackDto trackDto) {
    return trackMapper.dtoToTrack(trackDto);
  }

  private TrackDto convertToDto(Track track) {
    return trackMapper.trackToDto(track);
  }
}
