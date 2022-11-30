package tech.makers.aceplay.playlist;

import com.fasterxml.jackson.annotation.JsonGetter;
import tech.makers.aceplay.playlist_track.PlaylistTrack;
import tech.makers.aceplay.user.User;

import javax.persistence.*;
import java.util.*;

// https://www.youtube.com/watch?v=vreyOZxdb5Y&t=448s
@Entity
public class Playlist {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String name;

  @OneToMany(mappedBy = "playlist", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
  @OrderBy("addedAt")
  private Set<PlaylistTrack> tracks = new HashSet<>();

  @ManyToOne(cascade = CascadeType.REMOVE)
  private User owner;

  public Playlist() {}

  public Playlist(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setOwner(User owner) { this.owner = owner; }

  public User getOwner() { return owner; }

  public Long getId() {
    return id;
  }

  @JsonGetter("tracks")
  public Set<PlaylistTrack> getTracks() {
    return tracks;
  }

  public void setTracks(Set<PlaylistTrack> tracks) {
    this.tracks = tracks;
  }

  @Override
  public String toString() {
    return String.format("Playlist[id=%d name='%s']", id, name);
  }
}
