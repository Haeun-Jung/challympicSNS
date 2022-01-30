package com.ssafy.challympic.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class File {

    @Id @GeneratedValue
    private int file_no;

    private String file_name;

    private String file_path;

    private String file_savedname;
}
