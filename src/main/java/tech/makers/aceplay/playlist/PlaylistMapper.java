package tech.makers.aceplay.playlist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.makers.aceplay.playlist_track.PlaylistTrack;
import tech.makers.aceplay.track.TrackDto;
import tech.makers.aceplay.track.TrackMapper;

import java.util.HashSet;
import java.util.Set;

@Component
public class PlaylistMapper {
    @Autowired
    TrackMapper trackMapper;

    public PlaylistDto playlistToDto(Playlist playlist){
        Set<TrackDto> trackDtos = new HashSet<>();
        for (PlaylistTrack playlistTrack : playlist.getTracks()) {
            TrackDto trackDto = trackMapper.trackToDto(playlistTrack.getTrack());
            trackDtos.add(trackDto);
        }
        return new PlaylistDto(playlist.getId(), playlist.getName(), trackDtos);
    }

    public Playlist dtoToPlaylist(PlaylistDto playlistDto){
        return new Playlist(playlistDto.getName());
    }
}