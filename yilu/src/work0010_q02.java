/*
编写矩形类与继承类：

（1）编写一个矩形类 Rect，包含成员：

矩形的宽 width；矩形的高 height。
两个构造方法：一个带有两个参数，用于 width 和 height 属性初始化；一个不带参数，将矩形初始化为宽和高都为10。
两个实例方法：一个求矩形面积 area()，另一个求矩形周长 perimeter()。
（2）通过继承 Rect 类，编写一个具有确定位置的矩形类 PlainRect，其确定位置用矩形的左上角坐标来标识。添加成员：

两个属性（矩形左上角坐标 startX 和 startY）。
两个构造方法：一个带4个参数，用于对 startX、startY、width 和 height 属性初始化；一个不带参数，将矩形初始化为左上角坐标、长和宽都为0的矩形。
一个实例方法：判断某个点是否在矩形内部 isInside(double x, double y)。如在矩形内，返回 true；否则，返回 false。
（3）编写 PlainRect 类的测试程序：

创建一个左上角坐标为（10，10），长为20，宽为10的 PlainRect 对象；
计算并打印输出矩形的面积和周长；
判断点(25.5，13)是否在矩形内，并打印输出相关信息。
 */

class Rect {
    protected double width;
    protected double height;

    public Rect(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public Rect() {
        this.width = 10;
        this.height = 10;
    }

    public double area() {
        return width * height;
    }

    public double perimeter() {
        return 2 * (width + height);
    }
}

class PlainRect extends Rect {
    private double startX;
    private double startY;

    public PlainRect(double startX, double startY, double width, double height) {
        super(width, height);
        this.startX = startX;
        this.startY = startY;
    }

    public PlainRect() {
        super(0, 0);
        this.startX = 0;
        this.startY = 0;
    }

    public boolean isInside(double x, double y) {
        return (x >= startX && x <= startX + width &&
                y >= startY && y <= startY + height);
    }
}

public class work0010_q02 {
    public static void main(String[] args) {
        PlainRect plainRect = new PlainRect(10, 10, 20, 10);

        System.out.println("面积：" + plainRect.area());
        System.out.println("周长：" + plainRect.perimeter());

        double x = 25.5;
        double y = 13;
        System.out.println("点(" + x + ", " + y + ")是否在矩形内：" + plainRect.isInside(x, y));
    }
}

