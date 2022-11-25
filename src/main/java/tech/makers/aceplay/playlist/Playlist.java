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
  public SortedSet<PlaylistTrack> getTracks() {
    SortedSet<PlaylistTrack> orderedTracks = new TreeSet<>(new AscendingTrack());
    orderedTracks.addAll(tracks);
    return orderedTracks;
  }

  @Override
  public String toString() {
    return String.format("Playlist[id=%d name='%s']", id, name);
  }
}

class AscendingTrack implements Comparator<PlaylistTrack> {
  public int compare(PlaylistTrack track1, PlaylistTrack track2) {
    return track1.getAddedAt().compareTo(track2.getAddedAt());
  }
}
