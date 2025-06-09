
package cr.ac.una.perezoso.business;

import java.time.LocalDate;

/**
 *
 * @author dayan
 */
public class Logic {
    public boolean validateWord(String word) {
        for (char c : word.toCharArray()) {
            if (!Character.isLetter(c) && !Character.isWhitespace(c)) {
            System.out.println("El nombre del mantenimiento solo puede contener letras y espacios.");
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
        System.out.println("El nombre del mantenimiento no puede estar vacío y solo puede contener letras.");
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
