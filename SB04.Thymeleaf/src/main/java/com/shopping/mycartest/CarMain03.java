package com.shopping.mycartest;

import java.util.Scanner;

public class CarMain03 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("어떤 차를 타시겠습니까? 1(아반테), 2(소나타) : ");
        int menu = scan.nextInt() ;
        Car03 car = null ; // 다형성
        switch (menu){
            case 1:
                car = new Avante03("아반떼 쥐색", 200, "편안해요.") ;
                break ;
            case 2:
                car = new Sonata03("NF 소나타", 400, "시승감 좋아요") ;
                break ;
            default:
                System.out.println("프로그램을 종료합니다.");
                return;
        }
        Person03 bean = new Person03("박영희", 20, "여자", car);
        System.out.println(bean);
    }
}
