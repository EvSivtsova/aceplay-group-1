package tech.makers.aceplay.playlist;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import tech.makers.aceplay.playlist_track.PlaylistTrack;
import tech.makers.aceplay.playlist_track.PlaylistTrackService;
import tech.makers.aceplay.track.Track;
import tech.makers.aceplay.track.TrackRepository;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class PlaylistServiceTest {
    @InjectMocks
    private PlaylistService playlistService;
    @Mock
    private PlaylistRepository mockPlaylistRepository;
    @Mock
    private TrackRepository mockTrackRepository;
    @Mock
    private PlaylistTrackService mockPlaylistTrackService;

    @Test
    void getPlaylist_ReturnsAllPlaylists() {
        when(mockPlaylistRepository.findAll()).thenReturn(Set.of(new Playlist(), new Playlist()));

        assertThat(playlistService.getPlaylists()).hasSize(2);
        verify(mockPlaylistRepository, times(1)).findAll();
        verifyNoMoreInteractions(mockPlaylistRepository);
    }

    @Test
    void createPlaylist_WhenPlaylistNameValid_SavesNewPlaylist() {
        Playlist playlistToSave = new Playlist("My first playlist");
        when(mockPlaylistRepository.save(any(Playlist.class))).thenReturn(playlistToSave);

        Playlist actual = playlistService.createPlaylist(playlistToSave);

        assertThat(actual).usingRecursiveComparison().isEqualTo(playlistToSave);
        verify(mockPlaylistRepository, times(1)).save(any(Playlist.class));
        verifyNoMoreInteractions(mockPlaylistRepository);
    }

    @Test
    void createPlaylist_WhenPlaylistNameNotValid_ThrowsError() {
        Playlist playlistToSave = new Playlist("");
        assertThrows(IllegalArgumentException.class, () -> playlistService.createPlaylist(playlistToSave));

        verify(mockPlaylistRepository, times(0)).save(any(Playlist.class));
        verifyNoMoreInteractions(mockPlaylistRepository);
    }

    @Test
    void getPlaylistById_WhenPlaylistExists_FindsAndReturnsPlaylist() {
        Playlist expectedPlaylist = new Playlist("Another great playlist");
        when(mockPlaylistRepository.findById(anyLong())).thenReturn(Optional.of(expectedPlaylist));

        Playlist actual = playlistService.getPlaylistById(getRandomLong());

        assertThat(actual).usingRecursiveComparison().isEqualTo(expectedPlaylist);
        verify(mockPlaylistRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(mockPlaylistRepository);
    }

    @Test
    void getPlaylistById_WhenPlaylistDoesntExist_ReturnsNotFound() {
        when(mockPlaylistRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> playlistService.getPlaylistById(getRandomLong()));

        verify(mockPlaylistRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(mockPlaylistRepository);
    }

    @Test
    void deletePlaylist_WhenPlaylistExists_DeletesPlaylist() {
        doNothing().when(mockPlaylistRepository).delete(any(Playlist.class));

        Playlist expectedPlaylist = new Playlist("Another great playlist");
        when(mockPlaylistRepository.findById(anyLong())).thenReturn(Optional.of(expectedPlaylist));

        playlistService.deletePlaylist(getRandomLong());

        verify(mockPlaylistRepository, times(1)).findById(anyLong());
        verify(mockPlaylistRepository, times(1)).delete(any(Playlist.class));
        verifyNoMoreInteractions(mockPlaylistRepository);
    }

    @Test
    void deletePlaylist_WhenPlaylistDoesntExist_ThrowsError() {
        when(mockPlaylistRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> playlistService.deletePlaylist(getRandomLong()));
        verify(mockPlaylistRepository, times(1)).findById(anyLong());
        verify(mockPlaylistRepository, times(0)).delete(any(Playlist.class));
        verifyNoMoreInteractions(mockPlaylistRepository);
    }

    @Test
    void addTrackToPlaylist_WhenPlaylistExists_AndTrackExists_SavesTrackToPlaylist() {
        Playlist expectedPlaylist = new Playlist("Another great playlist");
        when(mockPlaylistRepository.findById(anyLong())).thenReturn(Optional.of(expectedPlaylist));

        Track expectedTrack = new Track("Track Title", "Track Artist");
        when(mockTrackRepository.findById(null)).thenReturn(Optional.of(expectedTrack));

        PlaylistTrack expectedPlaylistTrack = new PlaylistTrack(expectedPlaylist, expectedTrack);
        when(mockPlaylistTrackService.createPlaylistTrack(any(PlaylistTrack.class))).thenReturn(null);

        Track actual = playlistService.addTrackToPlaylist(getRandomLong(), new TrackIdentifierDto());

        assertThat(actual).usingRecursiveComparison().isEqualTo(expectedTrack);
        verify(mockPlaylistRepository, times(1)).findById(anyLong());
        verify(mockTrackRepository, times(1)).findById(null);
        verify(mockPlaylistTrackService, times(1)).createPlaylistTrack(any(PlaylistTrack.class));
        verifyNoMoreInteractions(mockPlaylistRepository);
        verifyNoMoreInteractions(mockPlaylistTrackService);
        verifyNoMoreInteractions(mockTrackRepository);
    }

    @Test
    void addTrackToPlaylist_WhenPlaylistDoesntExist_AndTrackExists_ThrowsError() {
        when(mockPlaylistRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> playlistService.addTrackToPlaylist(getRandomLong(), new TrackIdentifierDto()));

        verify(mockPlaylistRepository, times(1)).findById(anyLong());
        verify(mockTrackRepository, times(0)).findById(null);
        verify(mockPlaylistTrackService, times(0)).createPlaylistTrack(any(PlaylistTrack.class));
        verifyNoMoreInteractions(mockPlaylistRepository);
        verifyNoMoreInteractions(mockPlaylistTrackService);
        verifyNoMoreInteractions(mockTrackRepository);
    }

    @Test
    void addTrackToPlaylist_WhenPlaylistExists_AndTrackDoesntExist_ThrowsError() {
        Playlist expectedPlaylist = new Playlist("Another great playlist");
        when(mockPlaylistRepository.findById(anyLong())).thenReturn(Optional.of(expectedPlaylist));

        when(mockTrackRepository.findById(null)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> playlistService.addTrackToPlaylist(getRandomLong(), new TrackIdentifierDto()));

        verify(mockPlaylistRepository, times(1)).findById(anyLong());
        verify(mockTrackRepository, times(1)).findById(null);
        verify(mockPlaylistTrackService, times(0)).createPlaylistTrack(any(PlaylistTrack.class));
        verifyNoMoreInteractions(mockPlaylistRepository);
        verifyNoMoreInteractions(mockPlaylistTrackService);
        verifyNoMoreInteractions(mockTrackRepository);
    }

    private long getRandomLong() {
        return 1L + (long) (Math.random() * (10L - 1L));
    }
}

