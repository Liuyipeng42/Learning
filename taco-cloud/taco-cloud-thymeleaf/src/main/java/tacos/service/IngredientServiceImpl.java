package tacos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tacos.dao.IngredientDao;
import tacos.domain.Ingredient;

import java.util.List;

@Service
public class IngredientServiceImpl implements IngredientService {

    private IngredientDao ingredientDao;

    @Autowired
    public void setIngredientDao(IngredientDao ingredientDao){
        this.ingredientDao = ingredientDao;
    }

    @Override
    public List<Ingredient> findAllIngredients() {
        return ingredientDao.findAllIngredients();
    }

    @Override
    public Ingredient findIngredientById(String id) {
        return ingredientDao.findIngredientById(id);
    }

    @Override
    public List<Ingredient> getIngredientsByTacoId(String tacoId) {
        return ingredientDao.getIngredientsByTacoId(tacoId);
    }
}
