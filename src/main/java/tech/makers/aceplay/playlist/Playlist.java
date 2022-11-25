package tech.makers.aceplay.playlist;

import com.fasterxml.jackson.annotation.JsonGetter;
import javax.persistence.*;
import java.util.*;

// https://www.youtube.com/watch?v=vreyOZxdb5Y&t=448s
@Entity
public class Playlist {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String name;

  @OneToMany(mappedBy = "playlist", fetch = FetchType.EAGER)
  @OrderBy("addedAt")
  private Set<PlaylistTrack> tracks = new HashSet<>();

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

  public Long getId() {
    return id;
  }

  @JsonGetter("tracks")
  public Set<PlaylistTrack> getTracks() {
    return tracks;
  }

  @Override
  public String toString() {
    return String.format("Playlist[id=%d name='%s']", id, name);
  }
}
