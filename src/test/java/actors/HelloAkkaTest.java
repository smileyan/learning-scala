package actors;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.JavaTestKit;
import akka.testkit.TestActorRef;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import scala.concurrent.duration.Duration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by huay on 28/01/2016.
 */
public class HelloAkkaTest {

    static ActorSystem system;

    @BeforeClass
    public static void setup() {
        system = ActorSystem.create();
    }

    @AfterClass
    public static void teardown() {
        system.shutdown();
        system.awaitTermination(Duration.create("10 seconds"));
    }

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
    public void testSetGreeter() {
        new JavaTestKit(system) {{
            final TestActorRef<HelloAkkaJava.Greeter> greeter =
                    TestActorRef.create(system, Props.create(HelloAkkaJava.Greeter.class), "greeter1");

            greeter.tell(new HelloAkkaJava.WhoToGreet("testkit"), getTestActor());

            Assert.assertEquals("hello, testkit", greeter.underlyingActor().greeting);
        }};
    }

    @Test
    public void testGetGreeter() {
        new JavaTestKit(system) {{

            final ActorRef greeter = system.actorOf(Props.create(HelloAkkaJava.Greeter.class), "greeter2");

            greeter.tell(new HelloAkkaJava.WhoToGreet("testkit"), getTestActor());
            greeter.tell(new HelloAkkaJava.Greet(), getTestActor());

            final HelloAkkaJava.Greeting greeting = expectMsgClass(HelloAkkaJava.Greeting.class);

            new Within(duration("10 seconds")) {
                protected void run() {
                    Assert.assertEquals("hello, testkit", greeting.message);
                }
            };
        }};
    }
}
