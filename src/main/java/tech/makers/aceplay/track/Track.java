package tech.makers.aceplay.track;

import tech.makers.aceplay.user.User;

import javax.persistence.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

// https://www.youtube.com/watch?v=5r3QU09v7ig&t=2999s
@Entity
public class Track {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String title;

  private String artist;

  private URL publicUrl;


  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
          name="users_tracks",
          joinColumns={@JoinColumn(name="track_id")},
          inverseJoinColumns={@JoinColumn(name="user_id")}
          )
  private Set<User> users = new HashSet<>();

  public Track() { }

  public Track(String title, String artist, URL publicUrl) {
    this.title = title;
    this.artist = artist;
    this.publicUrl = publicUrl;
  }
  public Track(String title, String artist) {
    this.title = title;
    this.artist = artist;
    this.publicUrl = publicUrl;
  }

  public Track(String title, String artist, String publicUrl) throws MalformedURLException {
    this(title, artist, new URL(publicUrl));
  }

  public String toString() {
    return String.format(
        "Track[id=%d title='%s' artist='%s' publicUrl='%s']", id, title, artist, publicUrl);
  }

  public Long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getArtist() {
    return artist;
  }

  public void setArtist(String artist) {
    this.artist = artist;
  }

  public URL getPublicUrl() {
    return publicUrl;
  }

  public void setPublicUrl(String publicUrl) throws MalformedURLException {
    this.publicUrl = new URL(publicUrl);
  }

  public void setUsers(Set<User> users) {
    this.users = users;
  }

  public Set<User> getUsers() {
    return users;
  }

  public void setPublicUrl(URL publicUrl) {
    this.publicUrl = publicUrl;
  }
}
