package tech.makers.aceplay.track;

import org.springframework.stereotype.Component;

@Component
public class TrackMapper {
    public TrackDto trackToDto(Track trackFromDatabase) {
        var trackDto = new TrackDto();
        trackDto.setId(trackFromDatabase.getId());
        trackDto.setTitle(trackFromDatabase.getTitle());
        trackDto.setArtist(trackFromDatabase.getArtist());
        trackDto.setPublicUrl(trackFromDatabase.getPublicUrl());

        return trackDto;
    }

    public Track dtoToTrack(TrackDto trackDtoFromUser) {
        var track = new Track();
        track.setTitle(trackDtoFromUser.getTitle());
        track.setArtist(trackDtoFromUser.getArtist());
        track.setPublicUrl(trackDtoFromUser.getPublicUrl());

        return track;
    }
}
