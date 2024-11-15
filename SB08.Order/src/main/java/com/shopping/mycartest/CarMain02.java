package com.shopping.mycartest;

public class CarMain02 {
    public static void main(String[] args) {
        Avante02 avante = new Avante02();
        avante.setName("아반떼 검은차");
        avante.setPrice(200);
        avante.setComment("편안해요");

        // avante 객체를 외부에서 만들어, 생성자를 통하여 주입 시키고 있습니다.
        // 예제 1번보다는 결합력이 다소 느슨합니다.(loose coupling)
        Person02 bean = new Person02("박영희", 20, "여자", avante);
        System.out.println(bean);
    }
}
