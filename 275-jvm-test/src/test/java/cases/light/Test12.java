package cases.light;

import cases.TestUtil;

public class Test12 {
    public static void main(String[] args) {
        show(new Cat());
        show(new Dog());

        Animal a = new Cat();
        a.eat();
        Cat c = (Cat)a;
        c.work();
    }

    public static void show(Animal a)  {
        a.eat();
        // 类型判断
        if (a instanceof Cat)  {
            Cat c = (Cat)a;
            c.work();
        } else if (a instanceof Dog) {
            Dog c = (Dog)a;
            c.work();
        }
    }
}

abstract class Animal {
    abstract void eat();
}

class Cat extends Animal {
    public void eat() {
        TestUtil.reach(1);
    }
    public void work() {
        TestUtil.reach(2);
    }
}

class Dog extends Animal {
    public void eat() {
        TestUtil.reach(3);
    }
    public void work() {
        TestUtil.reach(4);
    }
}
