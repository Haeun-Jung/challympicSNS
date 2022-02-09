package com.ssafy.challympic.api.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class SearchDto {
    private int search_no;
    private int user_no;
    private int tag_no;
    private String tag_content;
    private String search_content;
    private Date search_regdate;
}
