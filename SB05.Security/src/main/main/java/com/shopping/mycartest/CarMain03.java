package com.shopping.mycartest;

import java.util.Scanner;


public class CarMain03 {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("자동차 선택 - 1(아반떼), 2(소나타): ");
        int carId = scanner.nextInt();
        Car03 car= null;

        switch(carId) {
            case 1:
                car = new Avante03("아반떼", 200, "ㅇㅇ");
                break;
            case 2:
                car = new Sonata03("소나타", 200, "ㅇㅇ");
                break;
            default:  //1, 2 외 다른 입력이 있을 시 프로그램 종료
                System.out.println("프로그램 종료");
                return;
        }

        Person03 bean = new Person03("박영희", 20, "남성", car);
        System.out.println("bean = " + bean);
    }
}
