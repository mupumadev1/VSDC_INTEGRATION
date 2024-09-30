package com.example.IMS.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
@Data
public class ClsDTO {
    @JsonProperty("cdCls")
    private String cdCls;

    @JsonProperty("cdClsNm")
    private String cdClsNm;

    @JsonProperty("userDfnNm1")
    private String userDfnNm1;

    @JsonProperty("dtlList")
    private List<DtlDTO> dtlList;

    // Getters and Setters
}
