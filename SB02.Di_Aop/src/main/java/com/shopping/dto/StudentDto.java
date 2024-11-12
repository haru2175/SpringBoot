package com.shopping.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StudentDto {
    private String id;
    private String name;
    private int kor;
    private int eng;
    private int math;

    public StudentDto() {
    }

    public StudentDto(String id, String name, int kor, int eng, int math) {
        this.id = id;
        this.name = name;
        this.kor = kor;
        this.eng = eng;
        this.math = math;
    }
}
