package tech.makers.aceplay.playlist;

import tech.makers.aceplay.track.TrackDto;

import java.util.HashSet;
import java.util.Set;

public class PlaylistDto {

    private Long id;
    private String name;
    private Set<TrackDto> tracks = new HashSet<>();

    public PlaylistDto(String nameFromUser) {
        this.name = nameFromUser;
    }

    public PlaylistDto(Long idFromDatabase, String nameFromDatabase, Set<TrackDto> tracksDtos) {
        this.id = idFromDatabase;
        this.name = nameFromDatabase;
        this.tracks = tracksDtos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String nameFromDatabase) {
        this.name = nameFromDatabase;
    }

    public Set<TrackDto> getTracks() {
        return tracks;
    }

    public void setTracks(Set<TrackDto> tracksFromDatabase) {
        this.tracks = tracksFromDatabase;
    }
}
