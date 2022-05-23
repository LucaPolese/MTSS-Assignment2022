////////////////////////////////////////////////////////////////////
// Marco Mamprin 1230233
// Luca Polese 1225425
////////////////////////////////////////////////////////////////////
package it.unipd.mtss.business.exception;

public class BillException extends Exception{

    public BillException(String message) {
        super(message);
    }

    public static BillException emptyOrder() {
        return new BillException("Ordine vuoto");
    }

    public static BillException nullEItem() {
        return new BillException("Lista contente un prodotto nullo");
    }

    public static BillException negativePrice() {
        return new BillException("Importo negativo");
    }

    public static BillException nullUser() {
        return new BillException("L'utente è nullo");
    }

    public static BillException nullTime() {
        return new BillException("Il tempo è nullo");
    }
    
}
