package com.shopping.mycartest;

public class CarMain02 {
    public static void main(String[] args) {
        Avante02 avante = new Avante02();
        avante.setName("아반떼(BL)");
        avante.setPrice(200);
        avante.setComment("편안");

        Person02 bean = new Person02("박영희", 20, "남성", avante);
        System.out.println("bean = " + bean);
    }
}
