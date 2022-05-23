package it.unipd.mtss.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {    
    @Test
    public void testGetEta() {
        //Arrange
        User user = new User("Mario", "Rossi", 22);
        //Act
        int eta = user.getEta();
        //Assert e Act
        assertEquals(22, eta);
    }

    @Test
    public void testGetId() {
        //Arrange
        User user = new User("Mario", "Rossi", 22);
        int userId = User.getUltimoID();
        //Act
        int getUserId = user.getId();
        //Assert
        assertEquals(userId, getUserId);
    }

    @Test
    public void testGetName() {
        //Arrange
        User user = new User("Mario", "Rossi", 22);
        //Act
        String nome = user.getName();
        //Assert e Act
        assertEquals("Mario", nome);
    }

    @Test
    public void testGetSurname() {
        //Arrange
        User user = new User("Mario", "Rossi", 22);
        //Act
        String cognome = user.getSurname();
        //Assert
        assertEquals("Rossi", cognome);
    }

    @Test()
    public void testUtenteNomeNull(){
        //Arrange
        try{
            User utente = new User(null, "Rossi", 22);
        //Act
            //utente.getName();
        }catch(IllegalArgumentException e){
        //Assert
            assertEquals("Nome non valido", e.getMessage().toString());
        }   
    }

    @Test
    public void testUtenteNomeVuoto(){
        //Arrange
        try{
            User utente = new User("", "Rossi", 22);
        //Act
            //utente.getName();
        }catch(IllegalArgumentException e){
        //Assert
            assertEquals("Nome non valido", e.getMessage().toString());
        }  
    }

    @Test
    public void testUtenteCognomeNull(){
        //Arrange
        try{
            User utente = new User("Mario", null, 22);
        //Act
            //utente.getSurname();
        }catch(IllegalArgumentException e){
        //Assert
            assertEquals("Cognome non valido", e.getMessage().toString());
        }
    }
    
    @Test
    public void testUtenteCognomeVuoto(){
        //Arrange
        try{
            User utente = new User("Mario", "", 22);
        //Act
            //utente.getSurname();
        }catch(IllegalArgumentException e){
        //Assert
            assertEquals("Cognome non valido", e.getMessage().toString());
        }
    }

    @Test
    public void testUtenteEtaZero(){
        //Arrange
        try{
            User utente = new User("Mario", "Rossi", 0);
        //Act
            //utente.getEta();
        }catch(IllegalArgumentException e){
        //Assert
            assertEquals("Eta non valida", e.getMessage().toString());
        }
    }

    @Test
    public void testUtenteEtaNegativa(){
        //Arrange
        try{
            User utente = new User("Mario", "Rossi", -1);
        //Act
            //utente.getEta();
        }catch(IllegalArgumentException e){
        //Assert
            assertEquals("Eta non valida", e.getMessage().toString());
        }
    }
}