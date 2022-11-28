package tech.makers.aceplay.playlist;

import org.springframework.data.repository.CrudRepository;
import tech.makers.aceplay.track.Track;

import java.util.Optional;

public interface PlaylistTrackRepository extends CrudRepository<PlaylistTrack, Long> {
    Optional<PlaylistTrack> findByPlaylistAndTrack(Playlist playlist, Track track);
}
