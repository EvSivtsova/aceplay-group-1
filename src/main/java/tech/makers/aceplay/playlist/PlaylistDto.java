package tech.makers.aceplay.playlist;

import tech.makers.aceplay.playlist_track.PlaylistTrack;

import java.util.Set;

public class PlaylistDto {

    private Long id;
    private String name;
    private Set<PlaylistTrack> tracks;

    public PlaylistDto(String nameFromUser) {
        this.name = nameFromUser;
    }

    public PlaylistDto(Long idFromDatabase, String nameFromDatabase, Set<PlaylistTrack> tracksFromDatabase) {
        this.id = idFromDatabase;
        this.name = nameFromDatabase;
        this.tracks = tracksFromDatabase;
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

    public Set<PlaylistTrack> getTracks() {
        return tracks;
    }

    public void setTracks(Set<PlaylistTrack> tracksFromDatabase) {
        this.tracks = tracksFromDatabase;
    }
}
