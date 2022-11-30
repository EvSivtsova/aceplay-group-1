package tech.makers.aceplay.playlist;

import org.springframework.stereotype.Component;

@Component
public class PlaylistMapper {
    public PlaylistDto playlistToDto(Playlist playlist){
        return new PlaylistDto(playlist.getId(), playlist.getName(), playlist.getTracks());
    }

    public Playlist dtoToPlaylist(PlaylistDto playlistDto){
        return new Playlist(playlistDto.getName());
    }
}