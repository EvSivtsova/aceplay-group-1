package tech.makers.aceplay.playlist_track;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import tech.makers.aceplay.playlist.Playlist;
import tech.makers.aceplay.track.Track;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlaylistTrackServiceTest {

    @InjectMocks
    private PlaylistTrackService playlistTrackService;

    @Mock
    private PlaylistTrackRepository mockPlaylistTrackRepository;

    private Playlist playlist;
    private Track track;

    @BeforeEach
    public void setup() {
        playlist = new Playlist("playlist name");
        track = new Track("title", "artist");
    }

    @Test
    void createPlaylistTrack_savesPlaylistTrack() {
        PlaylistTrack playlistTrackToSave = new PlaylistTrack(playlist, track);
        when(mockPlaylistTrackRepository.save(any(PlaylistTrack.class))).thenReturn(playlistTrackToSave);

        PlaylistTrack actual = playlistTrackService.createPlaylistTrack(playlistTrackToSave);

        assertThat(actual).usingRecursiveComparison().isEqualTo(playlistTrackToSave);
        verify(mockPlaylistTrackRepository, times(1)).save(any(PlaylistTrack.class));
        verifyNoMoreInteractions(mockPlaylistTrackRepository);
    }

    @Test
    void findAndDeletePlaylistTrack_WhenTrackExistsOnPlaylist_DeletesPlaylistTrack() {
        PlaylistTrack playlistTrackToDelete = new PlaylistTrack(playlist, track);
        when(mockPlaylistTrackRepository.findByPlaylistAndTrack(any(Playlist.class), any(Track.class))).thenReturn(Optional.of(playlistTrackToDelete));

        doNothing().when(mockPlaylistTrackRepository).delete(any(PlaylistTrack.class));

        playlistTrackService.findAndDeletePlaylistTrack(playlist, track);

        verify(mockPlaylistTrackRepository, times(1)).findByPlaylistAndTrack(any(Playlist.class), any(Track.class));
        verifyNoMoreInteractions(mockPlaylistTrackRepository);
    }

    @Test
    void findAndDeletePlaylistTrack_WhenTrackDoesntExistOnPlaylist_ThrowsError() {
        when(mockPlaylistTrackRepository.findByPlaylistAndTrack(any(Playlist.class), any(Track.class))).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> playlistTrackService.findAndDeletePlaylistTrack(playlist, track));

        verify(mockPlaylistTrackRepository, times(1)).findByPlaylistAndTrack(any(Playlist.class), any(Track.class));
        verifyNoMoreInteractions(mockPlaylistTrackRepository);
    }


}
