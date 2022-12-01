package tech.makers.aceplay.track;

import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;

import static org.assertj.core.api.Assertions.assertThat;

class TrackMapperTest {

    private final TrackMapper trackMapper= new TrackMapper();

    @Test
    void trackToDTO_ReturnsTrackDtoMatchingOriginalTrack() throws MalformedURLException {
        Track originalTrack = new Track("Title", "Artist", "https://example.org/track.mp3");
        TrackDto returnedTrackDto = trackMapper.trackToDto(originalTrack);

        assertThat(returnedTrackDto).usingRecursiveComparison().isEqualTo(originalTrack);
    }

    @Test
    void dtoToTrack_ReturnsTrackMatchingOriginalTrackDto() throws MalformedURLException {
        TrackDto originalTrackDto = new TrackDto("Title", "Artist", "https://example.org/track.mp3");
        Track returnedTrack = trackMapper.dtoToTrack(originalTrackDto);

        assertThat(returnedTrack).usingRecursiveComparison()
                .ignoringFields("users").isEqualTo(originalTrackDto);
    }
}
