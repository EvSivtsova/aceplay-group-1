package tech.makers.aceplay.playlist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tech.makers.aceplay.track.Track;
import tech.makers.aceplay.track.TrackRepository;

import javax.xml.bind.ValidationException;
import java.util.Objects;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

// https://www.youtube.com/watch?v=vreyOZxdb5Y&t=0s
@RestController
public class PlaylistsController {
  @Autowired
  private PlaylistService playlistService;

  @GetMapping("/api/playlists")
  public Iterable<Playlist> playlists() {
    return playlistService.getPlaylists();
  }

  @PostMapping("/api/playlists")
  public Playlist create(@RequestBody Playlist playlist) {
    return playlistService.createPlaylist(playlist);
  }

  @GetMapping("/api/playlists/{id}")
  public Playlist get(@PathVariable Long id) {
    return playlistService.getPlaylistById(id);
  }

  @PutMapping("/api/playlists/{playlistId}/tracks")
  public Track addTrack(@PathVariable Long playlistId, @RequestBody TrackIdentifierDto trackIdentifierDto) {
    return playlistService.addTrackToPlaylist(playlistId, trackIdentifierDto);
  }

  @DeleteMapping("/api/playlists/{id}")
  public void deletePlaylist(@PathVariable Long id) {
    playlistService.deletePlaylist(id);
  }

  @DeleteMapping("/api/playlists/{playlist_id}/tracks/{track_id}")
  public void deleteTrackFromPlaylist(@PathVariable("playlist_id") Long playlistId, @PathVariable("track_id") Long trackId) {
    playlistService.deleteTrackFromPlaylist(playlistId, trackId);
  }
}
