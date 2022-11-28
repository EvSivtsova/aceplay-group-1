package tech.makers.aceplay.playlist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tech.makers.aceplay.playlist_track.PlaylistTrack;
import tech.makers.aceplay.playlist_track.PlaylistTrackService;
import tech.makers.aceplay.track.Track;
import tech.makers.aceplay.track.TrackRepository;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class PlaylistService {

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private TrackRepository trackRepository;

    @Autowired
    private PlaylistTrackService playlistTrackService;

    public Iterable<Playlist> getPlaylists() {
        return playlistRepository.findAll();
    }

    public Playlist createPlaylist(Playlist playlist) {
        if (playlist.getName().equals(""))
            throw new IllegalArgumentException("Playlist name cannot be empty");

        return playlistRepository.save(playlist);
    }
    public Playlist getPlaylistById(Long id) {
        return playlistRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "No playlist exists with id " + id));
    }

    public Track addTrackToPlaylist(Long playlistId, TrackIdentifierDto trackIdentifierDto) {
        Playlist playlist = getPlaylistById(playlistId);

        Track track = trackRepository.findById(trackIdentifierDto.getId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "No track exists with id " + trackIdentifierDto.getId()));

        PlaylistTrack playlistTrack = new PlaylistTrack(playlist, track);

        playlistTrackService.createPlaylistTrack(playlistTrack);

        return track;
    }

    public void deletePlaylist(Long id) {
        Playlist playlist = getPlaylistById(id);

        playlistRepository.delete(playlist);
    }

    public void deleteTrackFromPlaylist(Long playlistId, Long trackId) {
        Playlist playlist = getPlaylistById(playlistId);

        Track track = trackRepository.findById(trackId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "No track exists with id " + trackId));

        playlistTrackService.findAndDeletePlaylistTrack(playlist, track);
    }
}

