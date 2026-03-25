/*
 Java实现泛型链表
要完成该题，我们需要先对C语言中的链表有完整的学习，然后，请用Java实现一个泛型链表并提供相应API。

boolean addList(Node node) 添加结点
void removeList0() 删除尾结点
void removelistByValue(int value) 根据节点值删除节点
int find(int value) 找到值为 value 的结点，返回这个结点下标（下标从 0 开始计算）
提示：具体的设计可以参考 LinkedList 的源码。
*/
/**
 * 泛型链表节点类
 * @param <T> 节点中存储的数据类型
 */
class Node<T> {
    T data;          // 节点存储的数据
    Node<T> next;    // 指向下一个节点的引用

    public Node(T data) {
        this.data = data;
        this.next = null;
    }
}

/**
 * 泛型链表类
 * @param <T> 链表中存储的数据类型
 */
class GenericLinkedList<T> {
    private Node<T> head;  // 头节点
    private int size;      // 链表大小

    /**
     * 构造函数：初始化空链表
     */
    public GenericLinkedList() {
        this.head = null;
        this.size = 0;
    }

    /**
     * 添加节点到链表末尾
     * @param node 要添加的节点
     * @return 添加成功返回true
     */
    public boolean addList(Node<T> node) {
        if (node == null) {
            return false;
        }

        if (head == null) {
            // 链表为空，直接将新节点设为头节点
            head = node;
        } else {
            // 链表不为空，遍历到末尾添加
            Node<T> current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = node;
        }
        size++;
        return true;
    }

    /**
     * 删除尾节点
     */
    public void removeList0() {
        if (head == null) {
            return; // 空链表，直接返回
        }

        if (head.next == null) {
            // 只有一个节点
            head = null;
        } else {
            // 有多个节点，找到倒数第二个节点
            Node<T> current = head;
            while (current.next.next != null) {
                current = current.next;
            }
            current.next = null; // 删除尾节点
        }
        size--;
    }

    /**
     * 根据节点值删除节点（删除第一个匹配的节点）
     * @param value 要删除的节点值
     */
    public void removelistByValue(T value) {
        if (head == null) {
            return; // 空链表，直接返回
        }

        // 如果头节点就是要删除的节点
        if (head.data.equals(value)) {
            head = head.next;
            size--;
            return;
        }

        // 查找要删除的节点
        Node<T> current = head;
        while (current.next != null) {
            if (current.next.data.equals(value)) {
                current.next = current.next.next;
                size--;
                return;
            }
            current = current.next;
        }
    }

    /**
     * 查找值为value的节点，返回节点下标
     * @param value 要查找的值
     * @return 节点下标（从0开始），如果未找到返回-1
     */
    public int find(T value) {
        Node<T> current = head;
        int index = 0;

        while (current != null) {
            if (current.data.equals(value)) {
                return index;
            }
            current = current.next;
            index++;
        }

        return -1; // 未找到
    }

    /**
     * 获取链表大小
     * @return 链表中节点的数量
     */
    public int size() {
        return size;
    }

    /**
     * 判断链表是否为空
     * @return 空返回true，否则返回false
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 打印链表中的所有元素（用于调试）
     */
    public void printList() {
        Node<T> current = head;
        System.out.print("链表内容: ");
        while (current != null) {
            System.out.print(current.data + " -> ");
            current = current.next;
        }
        System.out.println("null");
    }

    /**
     * 清空链表
     */
    public void clear() {
        head = null;
        size = 0;
    }
}

/**
 * 主类 - 作业测试类
 */
public class work0011_q03 {

    public static void main(String[] args) {
        // 测试Integer类型的链表
        System.out.println("=== 测试整数链表 ===");
        GenericLinkedList<Integer> intList = new GenericLinkedList<>();

        // 添加节点
        Node<Integer> node1 = new Node<>(10);
        Node<Integer> node2 = new Node<>(20);
        Node<Integer> node3 = new Node<>(30);
        Node<Integer> node4 = new Node<>(40);

        System.out.println("添加节点: 10, 20, 30, 40");
        intList.addList(node1);
        intList.addList(node2);
        intList.addList(node3);
        intList.addList(node4);

        intList.printList();
        System.out.println("链表大小: " + intList.size());

        // 测试查找功能
        System.out.println("\n--- 测试查找功能 ---");
        System.out.println("查找值20的下标: " + intList.find(20));
        System.out.println("查找值50的下标: " + intList.find(50));

        // 测试删除尾节点
        System.out.println("\n--- 测试删除尾节点 ---");
        intList.removeList0();
        System.out.print("删除尾节点后: ");
        intList.printList();
        System.out.println("当前链表大小: " + intList.size());

        // 测试删除指定值的节点
        System.out.println("\n--- 测试删除指定值节点 ---");
        intList.removelistByValue(20);
        System.out.print("删除值20后: ");
        intList.printList();
        System.out.println("当前链表大小: " + intList.size());

        // 测试删除不存在的值
        System.out.println("\n--- 测试删除不存在的值 ---");
        intList.removelistByValue(100);
        System.out.print("尝试删除值100后: ");
        intList.printList();

        // 测试String类型的链表
        System.out.println("\n=== 测试字符串链表 ===");
        GenericLinkedList<String> strList = new GenericLinkedList<>();

        Node<String> strNode1 = new Node<>("Java");
        Node<String> strNode2 = new Node<>("Python");
        Node<String> strNode3 = new Node<>("C++");
        Node<String> strNode4 = new Node<>("JavaScript");

        System.out.println("添加节点: Java, Python, C++, JavaScript");
        strList.addList(strNode1);
        strList.addList(strNode2);
        strList.addList(strNode3);
        strList.addList(strNode4);

        strList.printList();
        System.out.println("链表大小: " + strList.size());

        // 测试查找
        System.out.println("\n--- 测试查找功能 ---");
        System.out.println("查找'Python'的下标: " + strList.find("Python"));
        System.out.println("查找'C++'的下标: " + strList.find("C++"));
        System.out.println("查找'Go'的下标: " + strList.find("Go"));

        // 测试删除
        System.out.println("\n--- 测试删除指定值节点 ---");
        strList.removelistByValue("Python");
        System.out.print("删除'Python'后: ");
        strList.printList();

        System.out.println("\n--- 测试删除尾节点 ---");
        strList.removeList0();
        System.out.print("删除尾节点后: ");
        strList.printList();

        // 测试边界情况
        System.out.println("\n=== 测试边界情况 ===");
        GenericLinkedList<Integer> emptyList = new GenericLinkedList<>();
        System.out.println("空链表大小: " + emptyList.size());
        System.out.println("空链表是否为空: " + emptyList.isEmpty());
        System.out.println("在空链表中查找值10: " + emptyList.find(10));

        emptyList.removeList0();  // 测试删除空链表的尾节点
        System.out.println("删除空链表尾节点后大小: " + emptyList.size());

        // 测试单个节点链表
        System.out.println("\n--- 测试单个节点链表 ---");
        GenericLinkedList<Integer> singleList = new GenericLinkedList<>();
        Node<Integer> singleNode = new Node<>(100);
        singleList.addList(singleNode);
        singleList.printList();
        System.out.println("查找值100的下标: " + singleList.find(100));

        singleList.removeList0();
        System.out.print("删除尾节点后: ");
        singleList.printList();
        System.out.println("链表大小: " + singleList.size());

        System.out.println("\n=== 所有测试完成 ===");
    }
}
