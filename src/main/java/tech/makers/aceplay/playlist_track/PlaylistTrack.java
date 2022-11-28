package tech.makers.aceplay.playlist_track;

import tech.makers.aceplay.playlist.Playlist;
import tech.makers.aceplay.track.Track;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "playlist_tracks")
public class PlaylistTrack {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "playlist_id")
    private Playlist playlist;

    @ManyToOne
    @JoinColumn(name = "track_id")
    private Track track;

    @Column(name = "added_at")
    private LocalDateTime addedAt;

    public PlaylistTrack() {
    }

    public PlaylistTrack(Playlist playlist, Track track) {
        this.playlist = playlist;
        this.track = track;
        this.addedAt = LocalDateTime.now();
    }
    public Long getId() {
        return id;
    }

    public Track getTrack() {
        return track;
    }

    public LocalDateTime getAddedAt() {
        return addedAt;
    }
}