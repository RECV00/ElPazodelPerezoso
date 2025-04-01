/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.business;

import cr.ac.una.perezoso.data.CabinData;
import cr.ac.una.perezoso.domain.Cabin;
import java.sql.SQLException;
import java.util.LinkedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author keyna
 */
@Service
public class CabinLogic {
    
    @Autowired
    private final CabinData cabinD;
    
    public CabinLogic(CabinData cabinD){
    this.cabinD=cabinD;
    }
    
     public LinkedList<Cabin> listCabins() throws SQLException, ClassNotFoundException{
        return cabinD.listCabins();
    }
     public void addCabins(Cabin cabin) throws SQLException, ClassNotFoundException{
            cabinD.insertCabin(cabin);
    }
     
    public void updateCabins(Cabin cabin)throws SQLException, ClassNotFoundException{
        cabinD.updateCabin(cabin);
    }

    public Cabin getCabinsBYID(int cabinID) throws SQLException, ClassNotFoundException {
        return cabinD.getCabinByID(cabinID);
    }
    
    public LinkedList<Cabin> searchCabinsByName(String name) throws SQLException, ClassNotFoundException {
    return cabinD.searchCabinsByName(name);
}
    public LinkedList<Cabin> searchCabinsByID(int cabinID) throws SQLException, ClassNotFoundException {
    return cabinD.searchCabinsByID(cabinID);
    }
    
    public void  deleteCabin (int cabinID) throws SQLException, ClassNotFoundException {
         cabinD.deleteCabin(cabinID);
    }
}
