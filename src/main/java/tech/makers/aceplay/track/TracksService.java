package tech.makers.aceplay.track;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TracksService {

    @Autowired
    private TrackRepository trackRepository;

    public Track validateAndSaveTrack(Track track) {
        if (track.getTitle().equals(""))
            throw new IllegalArgumentException("Track title cannot be empty");

        if (track.getArtist().equals(""))
            throw new IllegalArgumentException("Track artist cannot be empty");

        return trackRepository.save(track);
    }
}
