package tech.makers.aceplay.playlist;

import org.junit.jupiter.api.Test;
import tech.makers.aceplay.playlist_track.PlaylistTrack;
import tech.makers.aceplay.track.Track;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PlaylistMapperTest {
//    @Test
//    public void playlistToDto_RealObjects_ReturnsPlaylistDtoMatchingOriginalPlaylist() throws MalformedURLException {
//        Set<PlaylistTrack> playlistTracks = new HashSet<>();
//        PlaylistTrack mockPlaylistTrack = mock(PlaylistTrack.class);
//        playlistTracks.add(mockPlaylistTrack);
//
//        Track track = new Track("Artist", "Name", new URL("http://www.fgfgfgd.com"));
//        when(mockPlaylistTrack.getTrack()).thenReturn(track);
//
//        Playlist originalPlaylist = new Playlist("Name");
//        originalPlaylist.setTracks(playlistTracks);
//
//        PlaylistMapper playlistMapper = new PlaylistMapper();
//        PlaylistDto returnedPlaylistDto = playlistMapper.playlistToDto(originalPlaylist);
//
//        assertThat(returnedPlaylistDto).usingRecursiveComparison().isEqualTo(originalPlaylist);
//    }

    @Test
    public void dtoToPlaylist_MocksOnly_ReturnsPlaylistMatchingOriginalPlaylistDto() {
        PlaylistDto expectedPlaylist = mock(PlaylistDto.class);
        when(expectedPlaylist.getName()).thenReturn("name");

        PlaylistMapper playlistMapper = new PlaylistMapper();
        Playlist actual = playlistMapper.dtoToPlaylist(expectedPlaylist);

        assertEquals(expectedPlaylist.getName(), actual.getName());
    }

    @Test
    public void dtoToPlaylist_RealObjects_ReturnsPlaylistMatchingOriginalPlaylistDto() {
        PlaylistDto originalPlaylistDto = new PlaylistDto("Name");

        PlaylistMapper playlistMapper = new PlaylistMapper();
        Playlist returnedPlaylist = playlistMapper.dtoToPlaylist(originalPlaylistDto);

        assertThat(returnedPlaylist).usingRecursiveComparison()
                .ignoringFields("owner").isEqualTo(originalPlaylistDto);
    }
}