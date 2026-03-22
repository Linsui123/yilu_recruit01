/*
设计一个类Triangle代表三角形：

属性包括三个边长。
实现求面积的方法 area()。area 方法要求在计算面积前需要判断该三角形是否成立（即每条边长大于0，且满足任意两条边之和大于第三边）。
如果不成立，则抛出自定义的 NotTriangle 异常类的实例。
如果成立则返回该三角形的面积。
要求：

        （1）编程实现 Triangle 类和 NotTriangle 类。
        （2）在 Triangle 类的 main 方法中，分别用三边长3.0, 4.0, 5.0 和1.0, 1.0, 2.0的两组值构造两个实例，计算各自面积。
        （3）针对上述数据，给出你的程序运行的输出。
补充知识：海伦公式求面积，计算平方根的方法为 Math.sqrt(double)。

尝试使用 try-catch 来捕获异常。
*/

class NotTriangle extends Exception{
    public NotTriangle(String message){
        super (message);
    }
}
class Triangle {
    private double a, b, c;
    public Triangle(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;}

        public double area() throws NotTriangle {
            if (a <= 0 || b <= 0 || c <= 0) {
                throw new NotTriangle("三角形边长必须大于0");
            }
            if (a + b <= c || a + c <= b || b + c <= a) {
                throw new NotTriangle("两边之和必须大于第三边");
            }
            double p = (a + b + c) / 2;
            double area = Math.sqrt(p * (p - a) * (p - b) * (p - c));
            return area;
        }
    }
public class work0001_q01 {
    public static void main(String[] args ) {
        System.out.println("测试第一组数据：3.0, 4.0, 5.0");
        Triangle t1 = new Triangle(3.0, 4.0, 5.0);
        try {
            double area1 = t1.area();
            System.out.println("三角形面积是：" + area1);
        } catch (NotTriangle e) {
            System.out.println("错误：" + e.getMessage());
        }

        System.out.println();  // 空行分隔

        // 测试第二组数据：1.0, 1.0, 2.0
        System.out.println("测试第二组数据：1.0, 1.0, 2.0");
        Triangle t2 = new Triangle(1.0, 1.0, 2.0);
        try {
            double area2 = t2.area();
            System.out.println("三角形面积是：" + area2);
        } catch (NotTriangle e) {
            System.out.println("错误：" + e.getMessage());
        }
    }


    }