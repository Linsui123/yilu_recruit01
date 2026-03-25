/*
2. 攻击程序
实现功能：一名玩家分别受到 Creep-A, Creep-B 攻击三次，每次减少 20hp 值，玩家初始 hp 值为 100，当玩家 hp 值为 0 时，玩家死亡。

输出示例：

shell
Creep-A attack...
Creep-A: 当前player的hp值= 80
Creep-A attack...
Creep-A: 当前player的hp值= 60
Creep-A attack...
Creep-A: 当前player的hp值= 40
Creep-A end.
Creep-B attack...
Creep-B: 当前player的hp值= 20
Creep-B attack...
Creep-B: 当前player的hp值= 0
Creep-B: player is dead.
Creep-B end.
用同步块和同步方法两种方式实现。
 */

public class work0011_q02 {

    // 玩家类
    static class Player {
        private int hp = 100;

        // 同步方法 - 保证线程安全
        public synchronized void beAttacked(String creepName) {
            if (hp <= 0) {
                System.out.println(creepName + ": player is already dead.");
                return;
            }

            hp -= 20;
            if (hp > 0) {
                System.out.println(creepName + ": 当前player的hp值= " + hp);
            } else {
                System.out.println(creepName + ": 当前player的hp值= " + hp);
                System.out.println(creepName + ": player is dead.");
            }
        }

        public int getHp() {
            return hp;
        }
    }

    // 怪物类
    static class Creep extends Thread {
        private String name;
        private Player player;

        public Creep(String name, Player player) {
            this.name = name;
            this.player = player;
        }

        @Override
        public void run() {
            for (int i = 0; i < 3; i++) {
                System.out.println(name + " attack...");
                player.beAttacked(name);
                try {
                    Thread.sleep(100); // 模拟攻击间隔
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(name + " end.");
        }
    }

    public static void main(String[] args) {
        Player player = new Player();

        Creep creepA = new Creep("Creep-A", player);
        Creep creepB = new Creep("Creep-B", player);

        creepA.start();
        creepB.start();

        try {
            creepA.join();
            creepB.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class work0011_q02 {

    // 玩家类
    static class Player {
        private int hp = 100;
        private final Object lock = new Object(); // 锁对象

        public void beAttacked(String creepName) {
            // 同步块 - 保证线程安全
            synchronized (lock) {
                if (hp <= 0) {
                    System.out.println(creepName + ": player is already dead.");
                    return;
                }

                hp -= 20;
                if (hp > 0) {
                    System.out.println(creepName + ": 当前player的hp值= " + hp);
                } else {
                    System.out.println(creepName + ": 当前player的hp值= " + hp);
                    System.out.println(creepName + ": player is dead.");
                }
            }
        }

        public int getHp() {
            return hp;
        }
    }

    // 怪物类
    static class Creep extends Thread {
        private String name;
        private Player player;

        public Creep(String name, Player player) {
            this.name = name;
            this.player = player;
        }

        @Override
        public void run() {
            for (int i = 0; i < 3; i++) {
                System.out.println(name + " attack...");
                player.beAttacked(name);
                try {
                    Thread.sleep(100); // 模拟攻击间隔
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(name + " end.");
        }
    }

    public static void main(String[] args) {
        Player player = new Player();

        Creep creepA = new Creep("Creep-A", player);
        Creep creepB = new Creep("Creep-B", player);

        creepA.start();
        creepB.start();

        try {
            creepA.join();
            creepB.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
