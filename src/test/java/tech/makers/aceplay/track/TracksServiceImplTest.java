package tech.makers.aceplay.track;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;
import tech.makers.aceplay.user.User;
import tech.makers.aceplay.user.UserService;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TracksServiceImplTest {
    @Mock
    TrackRepository mockRepository;
    @InjectMocks
    TracksService tracksService = new TracksService();
    @Mock
    Track mockTrack;
    @Mock
    UserService mockUserService;
    @Mock
    User mockUser;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void index_CallsFindAll() {
        ArrayList<Track> emptyIterable = new ArrayList<Track>();
        assertEquals(emptyIterable, tracksService.index());
        verify(mockRepository).findAll();
    }

    @Test
    public void canInjectTrackRepositoryIntoTrackService() {
        assertEquals(mockRepository, tracksService.getTrackRepository());
    }

    @Test
    public void validateAndSaveTracks_CallsSaveOnRepo() {
        when(mockTrack.getArtist()).thenReturn("Not empty");
        when(mockTrack.getTitle()).thenReturn("Not empty");
        when(mockRepository.save(mockTrack)).thenReturn(mockTrack);

        assertEquals(mockTrack, tracksService.validateAndSaveTrack(mockTrack));
        verify(mockRepository).save(mockTrack);
    }

    @Test
    public void validateAndSaveTracks_throwsExceptionWhenNoTrackTitle()
            throws IllegalArgumentException {
        when(mockTrack.getArtist()).thenReturn("Not empty");
        when(mockTrack.getTitle()).thenReturn("");

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                tracksService.validateAndSaveTrack(mockTrack));

        assertEquals("Track title cannot be empty", exception.getMessage());
    }

    @Test
    public void validateAndSaveTracks_throwsExceptionWhenNoArtist()
            throws IllegalArgumentException {
        when(mockTrack.getArtist()).thenReturn("");
        when(mockTrack.getTitle()).thenReturn("Not empty");

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                tracksService.validateAndSaveTrack(mockTrack));

        assertEquals("Track artist cannot be empty", exception.getMessage());
    }

    @Test
    public void updateTrack_UpdatesTitleAndArtist()
            throws ResponseStatusException, MalformedURLException {
        when(mockTrack.getArtist()).thenReturn("New Artist");
        when(mockTrack.getTitle()).thenReturn("New Title");

        Track originalTrack = new Track("Title", "Artist", "https://example.org/");
        Long mockId = 1L;
        when(mockRepository.findById(mockId)).thenReturn(Optional.of(originalTrack));

        tracksService.updateTrack(mockId, mockTrack);
        assertEquals(mockTrack.getArtist(), originalTrack.getArtist());
        assertEquals(mockTrack.getTitle(), originalTrack.getTitle());
        verify(mockRepository).save(originalTrack);
    }

    @Test
    public void deleteTrack_CallsDeleteOnRepository()
            throws ResponseStatusException {
        Long mockId = 1L;
        when(mockRepository.findById(mockId)).thenReturn(Optional.of(mockTrack));

        tracksService.deleteTrack(mockId);
        verify(mockRepository).delete(mockTrack);
    }

    @Test
    public void addUserOfTrack_addsUserObjectToTrack()
            throws MalformedURLException {
        Track originalTrack = new Track("Title", "Artist", "https://example.org/");
        Long mockId = 1L;
        when(mockRepository.findById(mockId)).thenReturn(Optional.of(originalTrack));
        when(mockUserService.getAuthenticatedUser()).thenReturn(mockUser);
        when(mockRepository.save(originalTrack)).thenReturn(originalTrack);

        Set<User> updatedUsers = tracksService.addUserOfTrack(mockId).getUsers();
        assertEquals(mockUser, updatedUsers.iterator().next());

        verify(mockRepository).save(any(Track.class));
    }
}