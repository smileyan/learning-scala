package hadoop.c16;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by huay on 6/06/2016.
 */
public class Range {

    private final int start;
    private final int end;

    public Range(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public static java.util.List<Range> parse(String rangeSpec) {
        if (rangeSpec.length() == 0) {
            return Collections.emptyList();
        }
        List<Range> ranges = new ArrayList<>();
        String[] specs = rangeSpec.split(",");
        for (String spec: specs) {
            String[] split = spec.split("-");
            try {
                ranges.add(new Range(Integer.parseInt(split[0]), Integer.parseInt(split[1])));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }
        return ranges;
    }

    public int getEnd() {
        return end;
    }

    public String getSubstring(String line) {
        return line.substring(start - 1, end);
    }
}
