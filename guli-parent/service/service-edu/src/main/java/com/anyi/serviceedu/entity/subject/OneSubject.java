package com.anyi.serviceedu.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 安逸i
 * @version 1.0
 */
@Data
public class OneSubject {
    private String id;
    private String title;
    private List<TwoSubject> children=new ArrayList<>();
}
