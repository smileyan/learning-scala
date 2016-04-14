package akka;

import actors.MyUntypedActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.japi.Creator;

/**
 * Created by huay on 14/04/2016.
 */
public class DemoActor extends UntypedActor {

    public static Props props(final int magicNumber) {
        return Props.create(new Creator<DemoActor>() {
            private static final long serialVersionUID = 1l;

            @Override
            public DemoActor create() throws Exception {
                return new DemoActor(magicNumber);
            }
        });
    }

    final int magicNumber;

    public DemoActor(int magicNumber) {
        this.magicNumber = magicNumber;
    }

    @Override
    public void onReceive(Object message) throws Exception {

    }
}

class DemoMessagesActor extends UntypedActor {
    static public class Greeting {
        private final String from;

        public Greeting(String from) {
            this.from = from;
        }

        public String getGreeting() {
            return from;
        }
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Greeting) {
            getSender().tell("Hello" + ((Greeting) message).getGreeting(), getSelf());
        } else {
            unhandled(message);
        }
    }
}

class Main {
    final ActorSystem system = ActorSystem.create("MySystem");
    final ActorRef myActor = system.actorOf(Props.create(MyUntypedActor.class));

    public static void main(String[] args) {
    }
}
