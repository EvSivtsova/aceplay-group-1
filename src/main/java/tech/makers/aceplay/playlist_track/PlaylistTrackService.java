package tech.makers.aceplay.playlist_track;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tech.makers.aceplay.playlist.Playlist;
import tech.makers.aceplay.track.Track;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class PlaylistTrackService {

    @Autowired
    private PlaylistTrackRepository playlistTrackRepository;

    public PlaylistTrack createPlaylistTrack(PlaylistTrack playlistTrack) {
        return playlistTrackRepository.save(playlistTrack);
    }

    public void findAndDeletePlaylistTrack(Playlist playlist, Track track) {
        PlaylistTrack playlistTrack = findPlaylistTrack(playlist, track);
        deletePlaylistTrack(playlistTrack);
    }

    private PlaylistTrack findPlaylistTrack(Playlist playlist, Track track) {
        return playlistTrackRepository.findByPlaylistAndTrack(playlist, track)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "No track exists with id " + track.getId() + " on playlist with id " + playlist.getId()));
    }

    private void deletePlaylistTrack(PlaylistTrack playlistTrack) {
        playlistTrackRepository.delete(playlistTrack);
    }



}
