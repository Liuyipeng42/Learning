package com.lyp.tacocloudspring.service;

import com.lyp.tacocloudspring.entity.Ingredient;

import java.util.List;


public interface IngredientService {

    List<Ingredient> findAllIngredients();

    Ingredient findIngredientById(String id);

    List<Ingredient> getIngredientsByTacoId(String tacoId);

}
