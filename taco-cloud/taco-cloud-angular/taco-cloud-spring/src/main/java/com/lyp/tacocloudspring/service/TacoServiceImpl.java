package com.lyp.tacocloudspring.service;

import com.lyp.tacocloudspring.dao.IngredientDao;
import com.lyp.tacocloudspring.dao.TacoDao;
import com.lyp.tacocloudspring.entity.Taco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class TacoServiceImpl implements TacoService {

    TacoDao tacoDao;
    IngredientDao ingredientDao;

    @Autowired
    public void setTacoDao(TacoDao tacoDao) {
        this.tacoDao = tacoDao;
    }

    @Autowired
    public void setIngredientDao(IngredientDao ingredientDao){
        this.ingredientDao = ingredientDao;
    }

    @Override
    public Taco saveTaco(Taco design) {
        tacoDao.saveTaco(design);
        String tacoId = String.valueOf(design.getId());

        for (String ingredientId : design.getIngredientId()) {
            saveIngredientToTaco(ingredientDao.findIngredientById(ingredientId).getId(), tacoId);
        }
        return design;
    }

    @Override
    public int saveIngredientToTaco(String ingredientId, String tacoId) {
        return tacoDao.saveIngredientToTaco(ingredientId, tacoId);
    }

    @Override
    public List<Taco> getTacosByOrderId(String orderId) {
        return tacoDao.getTacosByOrderId(orderId);
    }

    @Override
    public List<Taco> getTacosDesc12() {
        List<Taco> tacos = tacoDao.getTacosDesc12();
        for(Taco taco: tacos){
            taco.setIngredients(ingredientDao.getIngredientsByTacoId(String.valueOf(taco.getId())));
        }
        return tacos;
    }
}
