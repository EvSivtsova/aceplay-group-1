package tech.makers.aceplay.track;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
//@AutoConfigureTestDatabase
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TracksServiceImplTest {

    @Mock
    private TrackRepository mockRepository;

    @Mock
    private Track mockTrack;

    private TracksService tracksService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        tracksService = new TracksService(mockRepository);
    }

    @Test
    public void canInjectTrackRepositoryIntoTrackService() {
        assertEquals(tracksService.getTrackRepository(), mockRepository);
    }

    @Test
    public void validateAndSaveTracks_CallsSaveOnRepo() {
        when(mockTrack.getArtist()).thenReturn("Not empty");
        when(mockTrack.getTitle()).thenReturn("Not empty");

        tracksService.validateAndSaveTrack(mockTrack);
        verify(mockRepository).save(mockTrack);
    }
}
