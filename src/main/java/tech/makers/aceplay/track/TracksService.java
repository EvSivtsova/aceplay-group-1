package tech.makers.aceplay.track;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tech.makers.aceplay.user.User;
import tech.makers.aceplay.user.UserRepository;
import tech.makers.aceplay.user.UserService;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class TracksService {
    @Autowired
    private TrackRepository trackRepository;

    public Iterable<Track> index() {
        return trackRepository.findAll();
    }

    @Autowired
    private UserService userService;

    public Track validateAndSaveTrack(Track track) {
        if (track.getTitle().equals(""))
            throw new IllegalArgumentException("Track title cannot be empty");

        if (track.getArtist().equals(""))
            throw new IllegalArgumentException("Track artist cannot be empty");

        return trackRepository.save(track);
    }

    public Track updateTrack(Long id, Track newTrack) {
        Track track = retrieveTrackWithId(id);
        track.setTitle(newTrack.getTitle());
        track.setArtist(newTrack.getArtist());
        trackRepository.save(track);
        return track;
    }

    public Track addUserOfTrack(Long id) {
        Track track = retrieveTrackWithId(id);
        track.getUsers().add(userService.getAuthenticatedUser());
        return trackRepository.save(track);
    }

    public void deleteTrack(Long id) {
        Track track = retrieveTrackWithId(id);
        trackRepository.delete(track);
    }

    private Track retrieveTrackWithId(Long id) {
        return trackRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "No track exists with id " + id));
    }

    public TrackRepository getTrackRepository() {
        return trackRepository;
    }
}
