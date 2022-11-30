package tech.makers.aceplay.track;

import org.springframework.data.repository.CrudRepository;
import tech.makers.aceplay.user.User;

import java.util.Set;

// https://www.youtube.com/watch?v=5r3QU09v7ig&t=2856s
public interface TrackRepository extends CrudRepository<Track, Long> {
  Track findFirstByOrderByIdAsc();

  Set<Track> findByUsers(User user);
}
