/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.perezoso.business;

import java.time.LocalDate;
import org.springframework.stereotype.Service;

/**
 *
 * @author keyna
 */
@Service 
public class ArticleLogic {
     public boolean validateWord(String word) {
        for (char c : word.toCharArray()) {
            if (!Character.isLetter(c) && !Character.isWhitespace(c)) {
            System.out.println("El nombre del producto solo puede contener letras y espacios.");
                return false;
            }
        }
     return true;
    }

    public boolean validateNumbers(int number) {
    if (number < 0) { 
        System.out.println("El campo solo puede contener números positivos.");
        return false;
    }
    return true;
}


    public boolean checkStrings(String word) {

        if (word.isEmpty() || !validateWord(word)) {
        System.out.println("El nombre del producto no puede estar vacío y solo puede contener letras.");
         return false;
        }
        return true;
    }
    
    public boolean validateLocalDate(LocalDate date) {
    if (date == null) {
        System.out.println("La fecha no puede estar vacía.");
        return false;
    }

    if (date.isBefore(LocalDate.now())) {
        System.out.println("La fecha no puede ser anterior a hoy.");
        return false;
    }

    return true;
}

    
  
}
