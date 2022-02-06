package com.ssafy.challympic.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter @Setter
@AllArgsConstructor
public class Media {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long file_no;
    
    // 원래 파일명
    private String file_name;

    // 파일 저장 경로
    private String file_path;

    // 저장된 파일명
    private String file_savedname;

    public Media() {}

    public Media(String file_name, String file_savedname, String file_path){
        this.file_name = file_name;
        this.file_savedname = file_savedname;
        this.file_path = file_path;
    }
}
