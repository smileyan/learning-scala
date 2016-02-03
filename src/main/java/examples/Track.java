package examples;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author richard
 */
public final class Track {

    private final String name;
    private final int length;

    public Track(String name, int length) {
        this.name = name;
        this.length = length;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the length of the track in milliseconds.
     */
    public int getLength() {
        return length;
    }

    public Track copy() {
        return new Track(name, length);
    }

    public Set<String> findLongTracks(List<Album> albums) {
        Set<String> trackNames = new HashSet<>();
        for (Album album: albums) {
            for (Track track: album.getTrackList()) {
                if (track.getLength() > 60) {
                    String name = track.getName();
                    trackNames.add(name);
                }
            }
        }
        return trackNames;
    }

    public Set<String> findLongTracks_re(List<Album> albums) {
        return albums.stream()
                     .flatMap(album -> album.getTracks())
                     .filter(track -> track.getLength() > 60)
                     .map(track -> track.getName())
                     .collect(Collectors.toSet());
    }

    public Set<String> getNationalityStartsWith(Album album, String tag) {
        return album.getMusicians()
                    .filter(artist -> artist.getName().startsWith(tag))
                    .map(artist -> artist.getNationality())
                    .collect(Collectors.toSet());
    }
}
