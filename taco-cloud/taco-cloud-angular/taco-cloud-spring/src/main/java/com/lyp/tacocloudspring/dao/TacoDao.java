package com.lyp.tacocloudspring.dao;

import com.lyp.tacocloudspring.entity.Taco;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface TacoDao {
    int saveTaco(Taco design);

    int saveIngredientToTaco(@Param("ingredientId") String ingredientId,
                             @Param("tacoId") String tacoId);

    List<Taco> getTacosByOrderId(String orderId);

    List<Taco> getTacosDesc12();

}
