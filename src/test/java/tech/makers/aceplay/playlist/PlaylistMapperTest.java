package tech.makers.aceplay.playlist;

import org.junit.jupiter.api.Test;
import tech.makers.aceplay.playlist_track.PlaylistTrack;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlaylistMapperTest {

    @Test
    public void playlistToDto_ReturnsPlaylistDtoObject() {
        PlaylistTrack mockPlaylistTrack1 = mock(PlaylistTrack.class);
        PlaylistTrack mockPlaylistTrack2 = mock(PlaylistTrack.class);
        Set<PlaylistTrack> mockTracks = new HashSet<>();
        mockTracks.add(mockPlaylistTrack1);
        mockTracks.add(mockPlaylistTrack2);
        Long mockId = 1L;

        Playlist expectedPlaylist = mock(Playlist.class);
        when(expectedPlaylist.getId()).thenReturn(mockId);
        when(expectedPlaylist.getName()).thenReturn("name");
        when(expectedPlaylist.getTracks()).thenReturn(mockTracks);

        PlaylistMapper playlistMapper = new PlaylistMapper();
        PlaylistDto actual = playlistMapper.playlistToDto(expectedPlaylist);

        assertEquals(expectedPlaylist.getId(), actual.getId());
        assertEquals(expectedPlaylist.getName(), actual.getName());
        assertEquals(expectedPlaylist.getTracks(), actual.getTracks());
    }

    @Test
    public void dtoToPlaylist_ReturnsPlaylistObject() {
        PlaylistDto expectedPlaylist = mock(PlaylistDto.class);
        when(expectedPlaylist.getName()).thenReturn("name");

        PlaylistMapper playlistMapper = new PlaylistMapper();
        Playlist actual = playlistMapper.dtoToPlaylist(expectedPlaylist);

        assertEquals(expectedPlaylist.getName(), actual.getName());
    }
}
