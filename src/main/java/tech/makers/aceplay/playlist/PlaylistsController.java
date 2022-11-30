package tech.makers.aceplay.playlist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.makers.aceplay.track.Track;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

// https://www.youtube.com/watch?v=vreyOZxdb5Y&t=0s
@RestController
public class PlaylistsController {
  @Autowired
  private PlaylistService playlistService;

  @Autowired PlaylistMapper playlistMapper;

  @GetMapping("/api/playlists")
  public Set<PlaylistDto> playlists() {
    Set<PlaylistDto> playlistDtos = new HashSet<>();
    playlistService.getPlaylists().forEach(playlist -> playlistDtos.add(convertToDto(playlist)));
    return playlistDtos;
  }

  @PostMapping("/api/playlists")
  public PlaylistDto create(@RequestBody PlaylistDto playlistDTO) {
    Playlist playlist = convertToEntity(playlistDTO);
    return convertToDto(playlistService.createPlaylist(playlist));
  }

  @GetMapping("/api/playlists/{id}")
  public PlaylistDto get(@PathVariable Long id) {
    return convertToDto(playlistService.getPlaylistById(id));
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
    return playlistMapper.dtoToPlaylist(playlistDto);
  }

  private PlaylistDto convertToDto(Playlist playlist) {
    return playlistMapper.playlistToDto(playlist);
  }
}
