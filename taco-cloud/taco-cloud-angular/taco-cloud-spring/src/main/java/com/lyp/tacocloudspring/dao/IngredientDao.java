package com.lyp.tacocloudspring.dao;

import com.lyp.tacocloudspring.entity.Ingredient;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface IngredientDao {

    List<Ingredient> findAllIngredients();

    Ingredient findIngredientById(String id);

    List<Ingredient> getIngredientsByTacoId(String tacoId);
}
