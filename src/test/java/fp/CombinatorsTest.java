package fp;

import examples.Track;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by huay on 2/02/2016.
 */
public class CombinatorsTest {
    @Test
    public void testStreamCollect() {
        List<String> collected = Stream.of("a", "b", "c")
                .collect(Collectors.toList());
        Assert.assertEquals(Arrays.asList("a", "b", "c"), collected);
    }

    @Test
    public void testForLoop() {
        List<String> collected = new ArrayList<>();
        for (String string: Arrays.asList("a", "b", "c")) {
            String uppercaseString = string.toUpperCase();
            collected.add(uppercaseString);
        }

        Assert.assertEquals(Arrays.asList("A", "B", "C"), collected);
    }

    @Test
    public void testStreamMap() {
        List<String> collected = Stream.of("a", "b", "c")
                .map(string -> string.toUpperCase())
                .collect(Collectors.toList());
        Assert.assertEquals(Arrays.asList("A","B","C"), collected);
    }

    @Test
    public void testIfLoop() {
        List<String> beginningWithNumbers = new ArrayList<>();
        for (String value : Arrays.asList("a", "1abc", "abc1")) {
            if (Character.isDigit(value.charAt(0))) {
                beginningWithNumbers.add(value);
            }
        }

        Assert.assertEquals(Arrays.asList("1abc"), beginningWithNumbers);
    }

    @Test
    public void testStreamFilter() {
        List<String> beginningWithNumbers = Stream.of("a","1abc","abc1")
                                                  .filter(value -> Character.isDigit(value.charAt(0)))
                                                  .collect(Collectors.toList());
        Assert.assertEquals(Arrays.asList("1abc"), beginningWithNumbers);
    }

    @Test
    public void testFlatMap() {
        List<Integer> together = Stream.of(Arrays.asList(1, 2), Arrays.asList(3, 4))
                                       .flatMap(numbers -> numbers.stream())
                                       .collect(Collectors.toList());
        Assert.assertEquals(Arrays.asList(1,2,3,4), together);
    }

    @Test
    public void testMin() {
        List<Track> tracks = Arrays.asList(new Track("Bakai", 524),
                                           new Track("Violets for Your Furs", 378),
                                           new Track("Time Was", 451));
        Track shortestTrack = tracks.stream()
                                    .min(Comparator.comparing(track -> track.getLength()))
                                    .get();

//        for (Track track: tracks) {
//            if (track.getLength() < shortestTrack.getLength()) {
//                shortestTrack = track;
//            }
//        }

        Assert.assertEquals(tracks.get(1), shortestTrack);
    }

    @Test
    public void testReduce() {
//        Object accumulator = initialValue;
//        for (Object element: elements) {
//            accumulator = combine(accumulator, element)
//        }
    }
}
