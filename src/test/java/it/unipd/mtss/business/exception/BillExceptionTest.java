package it.unipd.mtss.business.exception;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BillExceptionTest {
    @Test
    public void testEmptyOrder() {
        //Arrange
        BillException e = BillException.emptyOrder();
        //Act
        String message = e.getMessage().toString();
        //Assert
        assertEquals("Ordine vuoto", message);
    }

    @Test
    public void testNullEItem() {
        //Arrange
        BillException e = BillException.nullEItem();
        //Act
        String message = e.getMessage().toString();
        //Assert
        assertEquals("Lista contente un prodotto nullo", message);
    }

    @Test
    public void testNegativePrice() {
        //Arrange
        BillException e = BillException.negativePrice();
        //Act
        String message = e.getMessage().toString();
        //Assert
        assertEquals("Importo negativo", message);
    }

    @Test
    public void testNullUser() {
        //Arrange
        BillException e = BillException.nullUser();
        //Act
        String message = e.getMessage().toString();
        //Assert
        assertEquals("L'utente è nullo", message);
    }
    
    @Test
    public void testNullTime() {
        //Arrange
        BillException e = BillException.nullTime();
        //Act
        String message = e.getMessage().toString();
        //Assert
        assertEquals("Il tempo è nullo", message);
    }
}
