package cases.medium;

class Child extends Parent implements Human {
    Child() {
    }

    public int foo() {
        return 1;
    }

    public int bar() {
        return this.foo();
    }
}
