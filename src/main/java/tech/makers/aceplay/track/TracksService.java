package tech.makers.aceplay.track;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class TracksService {
    @Autowired
    private TrackRepository trackRepository;

    public Iterable<Track> index() {
        return trackRepository.findAll();
    }

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
