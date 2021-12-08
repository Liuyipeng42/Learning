package tacos.service;

import tacos.domain.Ingredient;

import java.util.List;


public interface IngredientService {

    List<Ingredient> findAllIngredients();

    Ingredient findIngredientById(String id);

    List<Ingredient> getIngredientsByTacoId(String tacoId);

}
