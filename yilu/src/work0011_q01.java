/*
. 生产者与消费者模型
现在有生产者和消费者两种角色，生产者可以产生资料。消费者可以对生产者产生的资料进行消费。

要求:
对生产者和消费者进行抽象形成接口 (interface)（思考一下每种角色应有什么功能，不限于上述）;
按照你自己的需求实现消费者和生产者 (implements)（如：芝士雪豹）;
依据多线程知识，使用你实现的类进行多个消费者并发消费生产者的资料的活动（尝试多种加锁方式）;
并谈谈如果不使用锁可能会出现哪些问题。
注：你可能需要为生产资料提供一个类。
 */
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.atomic.AtomicInteger;

public class work0011_q01 {

    // ========== 生产资料类 ==========
    static class Product {
        private static final AtomicInteger idGenerator = new AtomicInteger(0);
        private final int id;
        private final String name;

        public Product(String name) {
            this.id = idGenerator.incrementAndGet();
            this.name = name;
        }

        @Override
        public String toString() {
            return "产品{id=" + id + ", name='" + name + "'}";
        }
    }

    // ========== 存储接口 ==========
    interface Storage {
        void put(Product product) throws InterruptedException;
        Product take() throws InterruptedException;
    }

    // ========== 方式1：synchronized实现 ==========
    static class SynchronizedStorage implements Storage {
        private final Queue<Product> queue = new LinkedList<>();
        private final int capacity;

        public SynchronizedStorage(int capacity) {
            this.capacity = capacity;
        }

        @Override
        public synchronized void put(Product product) throws InterruptedException {
            while (queue.size() == capacity) {
                wait();
            }
            queue.offer(product);
            System.out.println(Thread.currentThread().getName() + " 生产: " + product + ", 库存:" + queue.size());
            notifyAll();
        }

        @Override
        public synchronized Product take() throws InterruptedException {
            while (queue.isEmpty()) {
                wait();
            }
            Product product = queue.poll();
            System.out.println(Thread.currentThread().getName() + " 消费: " + product + ", 库存:" + queue.size());
            notifyAll();
            return product;
        }
    }

    // ========== 方式2：ReentrantLock实现 ==========
    static class LockStorage implements Storage {
        private final Queue<Product> queue = new LinkedList<>();
        private final int capacity;
        private final ReentrantLock lock = new ReentrantLock();
        private final Condition notFull = lock.newCondition();
        private final Condition notEmpty = lock.newCondition();

        public LockStorage(int capacity) {
            this.capacity = capacity;
        }

        @Override
        public void put(Product product) throws InterruptedException {
            lock.lock();
            try {
                while (queue.size() == capacity) {
                    notFull.await();
                }
                queue.offer(product);
                System.out.println(Thread.currentThread().getName() + " 生产: " + product + ", 库存:" + queue.size());
                notEmpty.signal();
            } finally {
                lock.unlock();
            }
        }

        @Override
        public Product take() throws InterruptedException {
            lock.lock();
            try {
                while (queue.isEmpty()) {
                    notEmpty.await();
                }
                Product product = queue.poll();
                System.out.println(Thread.currentThread().getName() + " 消费: " + product + ", 库存:" + queue.size());
                notFull.signal();
                return product;
            } finally {
                lock.unlock();
            }
        }
    }

    // ========== 生产者 ==========
    static class Producer implements Runnable {
        private final Storage storage;
        private final String name;
        private volatile boolean running = true;
        private int count = 0;

        public Producer(Storage storage, String name) {
            this.storage = storage;
            this.name = name;
        }

        @Override
        public void run() {
            while (running) {
                try {
                    Thread.sleep((long) (Math.random() * 1000));
                    Product product = new Product(name + "-" + (++count));
                    storage.put(product);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }

        public void stop() {
            running = false;
        }
    }

    // ========== 消费者 ==========
    static class Consumer implements Runnable {
        private final Storage storage;
        private volatile boolean running = true;

        public Consumer(Storage storage) {
            this.storage = storage;
        }

        @Override
        public void run() {
            while (running) {
                try {
                    Thread.sleep((long) (Math.random() * 1500));
                    storage.take();
                } catch (InterruptedException e) {
                    break;
                }
            }
        }

        public void stop() {
            running = false;
        }
    }

    // ========== 测试 ==========
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== 测试1: Synchronized方式 ===");
        testStorage(new SynchronizedStorage(3));

        System.out.println("\n=== 测试2: ReentrantLock方式 ===");
        testStorage(new LockStorage(3));
    }

    private static void testStorage(Storage storage) throws InterruptedException {
        // 创建2个生产者
        Producer p1 = new Producer(storage, "生产者A");
        Producer p2 = new Producer(storage, "生产者B");

        // 创建3个消费者
        Consumer c1 = new Consumer(storage);
        Consumer c2 = new Consumer(storage);
        Consumer c3 = new Consumer(storage);

        // 启动线程
        Thread pt1 = new Thread(p1);
        Thread pt2 = new Thread(p2);
        Thread ct1 = new Thread(c1);
        Thread ct2 = new Thread(c2);
        Thread ct3 = new Thread(c3);

        pt1.start();
        pt2.start();
        ct1.start();
        ct2.start();
        ct3.start();

        // 运行3秒
        Thread.sleep(3000);

        // 停止所有
        p1.stop();
        p2.stop();
        c1.stop();
        c2.stop();
        c3.stop();

        pt1.join();
        pt2.join();
        ct1.join();
        ct2.join();
        ct3.join();

        System.out.println("测试完成");
    }
}

