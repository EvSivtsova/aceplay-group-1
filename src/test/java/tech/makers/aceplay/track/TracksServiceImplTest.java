package tech.makers.aceplay.track;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TracksServiceImplTest {
    @Mock
    TrackRepository mockRepository;
    @InjectMocks
    TracksService tracksService = new TracksService();

    @Mock
    Track mockTrack;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
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
}
