package option;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;


/**
 * Created by huay on 24/01/2016.
 */
public class NoneTest {
    private List<Option<String>> names = null;

    @Before
    public void setup() {
        names = new ArrayList<Option<String>>();
        names.add(new Some<String>("Dean"));
        names.add(new None<String>());
        names.add(new Some<String>("M"));
    }

    @Test
    public void getOrElseUseValueForSomeAndAlternativeForNone() {
        String[] expected = {"Dean", "Unkown!", "M"};

        System.out.println("*** Using getOrElse:");
        for (int i = 0; i < names.size(); i++) {
            Option<String> name = names.get(i);
            String value = name.getOrElse("Unkown!");
            System.out.println(name + ": " + value);
            assertEquals(expected[i], value);
        }
    }

    @Test
    public void hasNextWithGetUsesOnlyValuesForSomes() {
        String[] expected = {"Dean", "Unkown!", "M"};

        System.out.println("*** Using hasValue:");
        for (int i = 0; i < names.size(); i++) {
            Option<String> name = names.get(i);
            if(name.hasValue()) {
                String value = name.get();
                System.out.println(name + ": " + value);
                assertEquals(expected[i], value);
            }
        }
    }

    static Option<String> wrap(String s) {
        if (s == null) {
            return new None<String>();
        } else {
            return new Some<String>(s);
        }
    }


}