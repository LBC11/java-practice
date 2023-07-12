package next.reflection;

public class TimeRunner {

    @ElapsedTime
    void run(int time) throws InterruptedException {

        for(int i = 0; i < time; i++) {
            Thread.sleep(1);
        }
    }
}
