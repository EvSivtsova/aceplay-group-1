package tech.makers.aceplay.playlist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.makers.aceplay.track.Track;

import java.text.ParseException;

// https://www.youtube.com/watch?v=vreyOZxdb5Y&t=0s
@RestController
public class PlaylistsController {
  @Autowired
  private PlaylistService playlistService;

  @Autowired PlaylistMapper playlistMapper;

  @GetMapping("/api/playlists")
  public Iterable<Playlist> playlists() {
  ///to add conversion
    return playlistService.getPlaylists();
  }

  @PostMapping("/api/playlists")
  public PlaylistDto create(@RequestBody PlaylistDto playlistDTO) {
    Playlist playlist = convertToEntity(playlistDTO);
    PlaylistDto playlistDto = convertToDto(playlistService.createPlaylist(playlist));
    return playlistDto;
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

  private Playlist convertToEntity(PlaylistDto playlistDto) {
    Playlist playlist = playlistMapper.dtoToPlaylist(playlistDto);
    return playlist;
  }

  private PlaylistDto convertToDto(Playlist playlist) {
    PlaylistDto playlistDto = playlistMapper.playlistToDto(playlist);
//    postDto.setSubmissionDate(post.getSubmissionDate(),
//            userService.getCurrentUser().getPreference().getTimezone());
    return playlistDto;
  }
}
