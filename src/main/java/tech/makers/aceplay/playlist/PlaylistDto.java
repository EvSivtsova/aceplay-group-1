package tech.makers.aceplay.playlist;

import tech.makers.aceplay.playlist_track.PlaylistTrack;

import java.util.Set;

public class PlaylistDto {
    private String name;
    private Set<PlaylistTrack> tracks;

    public PlaylistDto(String nameFromUser, Set<PlaylistTrack> tracksFromDatabase) {
        this.name = nameFromUser;
        this.tracks = tracksFromDatabase;
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
