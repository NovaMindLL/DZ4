public class Main2 {
    static class Person {
        private String name;
        private boolean polite = true;

        public Person(String name) {
            this.name = name;
        }

        public synchronized void passThrough(Person other) {
            while (polite) {
                if (other.polite) {
                    System.out.println(name + ": После вас!");
                    polite = false;
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                } else {
                    System.out.println(name + " проходит через дверь");
                    polite = true;
                    return;
                }
            }
        }
    }

    public static void main(String[] args) {
        Person person1 = new Person("Алиса");
        Person person2 = new Person("Боб");

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                person1.passThrough(person2);
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                person2.passThrough(person1);
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
