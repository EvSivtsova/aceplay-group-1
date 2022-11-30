package tech.makers.aceplay.playlist;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tech.makers.aceplay.playlist_track.PlaylistTrack;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class PlaylistMapperTest {

    private final PlaylistMapper playlistMapper = new PlaylistMapper();

    private final Set<PlaylistTrack> mockPlaylistTracks = new HashSet<>();

    @BeforeEach
    public void setup() {
        PlaylistTrack mockPlaylistTrack1 = mock(PlaylistTrack.class);
        PlaylistTrack mockPlaylistTrack2 = mock(PlaylistTrack.class);
        mockPlaylistTracks.add(mockPlaylistTrack1);
        mockPlaylistTracks.add(mockPlaylistTrack2);
    }

    @Test
    void playlistToDto_ReturnsPlaylistDtoMatchingOriginalPlaylist() {
        Playlist originalPlaylist = new Playlist("Name");
        originalPlaylist.setTracks(mockPlaylistTracks);
        PlaylistDto returnedPlaylistDto = playlistMapper.playlistToDto(originalPlaylist);

        assertThat(returnedPlaylistDto).usingRecursiveComparison().isEqualTo(originalPlaylist);

//        Long mockId = 1L;
//
//        Playlist expectedPlaylist = mock(Playlist.class);
//        when(expectedPlaylist.getId()).thenReturn(mockId);
//        when(expectedPlaylist.getName()).thenReturn("name");
//        when(expectedPlaylist.getTracks()).thenReturn(mockTracks);
//
//        PlaylistMapper playlistMapper = new PlaylistMapper();
//        PlaylistDto actual = playlistMapper.playlistToDto(expectedPlaylist);
//
//        assertEquals(expectedPlaylist.getId(), actual.getId());
//        assertEquals(expectedPlaylist.getName(), actual.getName());
//        assertEquals(expectedPlaylist.getTracks(), actual.getTracks());
    }

    @Test
    void dtoToPlaylist_ReturnsPlaylistMatchingOriginalPlaylistDto() {
        PlaylistDto originalPlaylistDto = new PlaylistDto("Name");
        Playlist returnedPlaylist = playlistMapper.dtoToPlaylist(originalPlaylistDto);

        assertThat(returnedPlaylist).usingRecursiveComparison()
                .ignoringFields("owner").isEqualTo(originalPlaylistDto);

//        PlaylistDto expectedPlaylist = mock(PlaylistDto.class);
//        when(expectedPlaylist.getName()).thenReturn("name");
//
//        PlaylistMapper playlistMapper = new PlaylistMapper();
//        Playlist actual = playlistMapper.dtoToPlaylist(expectedPlaylist);
//
//        assertEquals(expectedPlaylist.getName(), actual.getName());
    }
}
