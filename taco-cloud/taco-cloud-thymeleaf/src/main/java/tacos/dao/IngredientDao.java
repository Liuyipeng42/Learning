package tacos.dao;

import org.apache.ibatis.annotations.Mapper;
import tacos.domain.Ingredient;

import java.util.List;


@Mapper
public interface IngredientDao {

    List<Ingredient> findAllIngredients();

    Ingredient findIngredientById(String id);

    List<Ingredient> getIngredientsByTacoId(String tacoId);
}
