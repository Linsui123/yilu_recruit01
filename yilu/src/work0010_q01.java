/*
 先定义一个接口 Perarea，它有两个方法，分别是计算图形的面积 double get_area(); 和计算图形周长 double get_perimeter();。
再实现一个长方形和一个圆形的类，它们都实现了这个接口。
构造长为10，宽为5的长方形，以及半径为5的圆。
通过接口分别打印出这两个图形的面积和周长，并写出输出结果。
*/
interface Perarea {
    double get_area();
    double get_perimeter();
}

class Rectangle implements Perarea {
    private double length;
    private double width;

    public Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }

    @Override
    public double get_area() {
        return length * width;
    }

    @Override
    public double get_perimeter() {
        return 2 * (length + width);
    }
}

class Circle implements Perarea {
    private double radius;
    private static final double PI = 3.141592653589793;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public double get_area() {
        return PI * radius * radius;
    }

    @Override
    public double get_perimeter() {
        return 2 * PI * radius;
    }
}

public class work0010_q01 {
    public static void main(String[] args) {
        Perarea rectangle = new Rectangle(10, 5);
        Perarea circle = new Circle(5);

        System.out.println("长方形（长=10，宽=5）：");
        System.out.println("面积：" + rectangle.get_area());
        System.out.println("周长：" + rectangle.get_perimeter());
        System.out.println();

        System.out.println("圆形（半径=5）：");
        System.out.println("面积：" + circle.get_area());
        System.out.println("周长：" + circle.get_perimeter());
    }
}
