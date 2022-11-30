package tech.makers.aceplay.track;

import java.net.MalformedURLException;
import java.net.URL;

public class TrackDto {
  private Long id;

  private String title;

  private String artist;

  private URL publicUrl;

  public TrackDto() {}

  public TrackDto(String title, String artist, String publicUrl) throws MalformedURLException {
    this.title = title;
    this.artist = artist;
    this.publicUrl = new URL(publicUrl);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public void setPublicUrl(URL publicUrl) {
    this.publicUrl = publicUrl;
  }
}
