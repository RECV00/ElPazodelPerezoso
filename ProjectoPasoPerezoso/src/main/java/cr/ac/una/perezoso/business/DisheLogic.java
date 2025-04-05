/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.business;

import cr.ac.una.perezoso.data.DisheData;
import cr.ac.una.perezoso.domain.Dishe;
import java.sql.SQLException;
import java.util.LinkedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author keyna
 */
@Service
public class DisheLogic {
    @Autowired
    private final DisheData disheD;
    
    public DisheLogic(DisheData disheD){
    this.disheD=disheD;
    }
    
     public LinkedList<Dishe> listDishes() throws SQLException, ClassNotFoundException{
        return disheD.listDishes();
    }
     public void addDishe(Dishe dishe) throws SQLException, ClassNotFoundException{
            disheD.insertDish(dishe);
    }
     
    public void updateDishe(Dishe dishe)throws SQLException, ClassNotFoundException{
        disheD.updateDish(dishe);
    }

    public Dishe getDisheByID(int disheID) throws SQLException, ClassNotFoundException {
        return disheD.getDishById(disheID);
    }
    
    public LinkedList<Dishe> searchDisheByName(String name) throws SQLException, ClassNotFoundException {
    return disheD.searchDishesByName(name);
}
    public LinkedList<Dishe> searchDisheByID(int cabinID) throws SQLException, ClassNotFoundException {
    return disheD.searchDishesById(cabinID);
    }
    
    public void  deleteDishe (int disheID) throws SQLException, ClassNotFoundException {
         disheD.deleteDish(disheID);
    }
}
