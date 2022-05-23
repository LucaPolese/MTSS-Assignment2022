////////////////////////////////////////////////////////////////////
// Marco Mamprin 1230233
// Luca Polese 1225425
////////////////////////////////////////////////////////////////////
package it.unipd.mtss.model;

public class User {
    private static int ultimoID=0;

    private String name,surname;
    private int id, eta;
    
    public User(String name, String surname, int eta){
        checkDatiUtente(name,surname,eta);
        this.name=name;
        this.surname=surname;
        this.eta=eta;
        this.id= ++ultimoID;
    }

    private void checkDatiUtente(String name2, String surname2, int eta2) 
    throws IllegalArgumentException{
        if(name2==null || name2.isEmpty()){
            throw new IllegalArgumentException("Nome non valido");
        }
        if(surname2==null || surname2.isEmpty()){
            throw new IllegalArgumentException("Cognome non valido");
        }
        if(eta2<=0){
            throw new IllegalArgumentException("Eta non valida");
        }
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getId() {
        return id;
    }

    public int getEta() {
        return eta;
    }

    public static int getUltimoID() {
        return ultimoID;
    }
}
