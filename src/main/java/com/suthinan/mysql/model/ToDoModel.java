package com.suthinan.mysql.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ToDoModel {
    @NotBlank(message = "Title is mandatory")
    private String title;
    @NotBlank(message = "Detail is mandatory")
    private String detail;
    @NotBlank(message = "Name is mandatory")
    private String name;
}
