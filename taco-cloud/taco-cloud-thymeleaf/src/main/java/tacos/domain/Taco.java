package tacos.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class Taco {

    private Long id;
    private Date createdAt;

    @NotNull // 被注释的元素不能为null
    @Size(min = 5, message = "Name must be at least 5 characters long") // 验证被注释的对象的长度是否在给定范围内
    private String name;

    @Size(min = 1, message = "You must choose at least 1 ingredient")

    private List<String> ingredients = new ArrayList<>();

    private List<Ingredient> ingredientList = new ArrayList<>();

    public void setId(Long id) {
        this.id = id;
    }


}

