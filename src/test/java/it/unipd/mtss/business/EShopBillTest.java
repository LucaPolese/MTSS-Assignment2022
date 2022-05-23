package it.unipd.mtss.business;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


import org.junit.Test;

import it.unipd.mtss.business.exception.BillException;
import it.unipd.mtss.model.EItem;
import it.unipd.mtss.model.ItemType;
import it.unipd.mtss.model.User;

import static org.junit.Assert.*;

public class EShopBillTest {
    private static final double delta = 0.001;

    /* Attività 1:  Dato un ordine di prodotti calcolarne il totale */
    // Test nel caso di array = null
    @Test
    public void testCalcoloTotale_ListaOrdiniNulla(){
        //Arrange
        EShopBill conto = new EShopBill();
        User utente = new User("Mario", "Rossi", 22);
        List<EItem> ordini = new ArrayList<EItem>();
        ordini = null;
        LocalTime ora = LocalTime.of(00, 00);
        //Act
        try{
           double totale = conto.getOrderPrice(ordini, utente, ora); 
        }catch(BillException e){
        //Assert
            assertEquals("Ordine vuoto", e.getMessage());
        }
    }

    // Test nel caso di array vuoto
    @Test
    public void testCalcoloTotale_ListaOrdiniVuota(){
        //Arrange
        EShopBill conto = new EShopBill();
        User utente = new User("Mario", "Rossi", 22);
        List<EItem> ordini = new ArrayList<EItem>();
        LocalTime ora = LocalTime.of(00, 00);
        //Act
        try{
           double totale = conto.getOrderPrice(ordini, utente, ora); 
        }catch(BillException e){
        //Assert
            assertEquals("Ordine vuoto", e.getMessage());
        }
    }

    //Test nel caso di inserimento di EItem nullo
    @Test
    public void testCalcoloTotale_EItemNullo(){
        //Arrange
        EShopBill conto = new EShopBill();
        User utente = new User("Mario", "Rossi", 22);
        List<EItem> ordini = new ArrayList<EItem>();
        ordini.add(new EItem(ItemType.Mouse,"Razer", 12.3));
        ordini.add(null);
        LocalTime ora = LocalTime.of(00, 00);
        //Act
        try{
           double totale = conto.getOrderPrice(ordini, utente, ora); 
        }catch(BillException e){
        //Assert
            assertEquals("Lista contente un prodotto nullo", e.getMessage());
        }
    }

    // Test nel caso di array con un elemento con prezzo negativo
    @Test
    public void testCalcoloTotale_EItemConPrezzoNegativo(){
        //Arrange
        EShopBill conto = new EShopBill();
        User utente = new User("Mario", "Rossi", 22);
        List<EItem> ordini = new ArrayList<EItem>();
        ordini.add(new EItem(ItemType.Mouse,"Razer", -12.3));
        LocalTime ora = LocalTime.of(00, 00);
        //Act
        try{
           double totale = conto.getOrderPrice(ordini, utente, ora); 
        }catch(BillException e){
        //Assert
            assertEquals("Importo negativo", e.getMessage());
        }
    }
    
    // Controllo che la somma totale aspettata corrisponda a quanto calcolato dalla funzione
    @Test
    public void testCalcoloTotale_ListaOrdiniOk() throws BillException{
        //Arrange
        EShopBill conto = new EShopBill();
        User utente = new User("Mario", "Rossi", 22);List<EItem> ordini = new ArrayList<EItem>();
        ordini.add(new EItem(ItemType.Mouse,"Razer", 12.3));
        ordini.add(new EItem(ItemType.Processor, "Intel", 350.0));
        ordini.add(new EItem(ItemType.Motherboard, "Asus", 200.0));
        LocalTime ora = LocalTime.of(00, 00);
        //Act
        double totale = conto.getOrderPrice(ordini,utente,ora);
        //Assert
        assertEquals(562.3, totale, delta);
    }

    //Test che l'orario associato all'ordine non sia nullo
    @Test
    public void testOrarioOrdineNullo(){
        //Arrange
        EShopBill conto = new EShopBill();
        User utente = new User("Mario", "Rossi", 17);
        List<EItem> ordini = new ArrayList<EItem>();
        EItem tastiera = new EItem(ItemType.Keyboard, "Logitech", 29.99);
        ordini.add(tastiera);
        LocalTime ora = null;
        //Act
        try{
            double totale = conto.getOrderPrice(ordini,utente,ora);
        }catch(BillException e){
        //Assert
            assertEquals("Il tempo è nullo", e.getMessage());
        }
    }

    //Test che l'utente associato all'ordine non sia nullo 
    @Test
    public void testUtenteOrdineNullo(){
        //Arrange
        EShopBill conto = new EShopBill();
        User utente = null;
        List<EItem> ordini = new ArrayList<EItem>();
        EItem tastiera = new EItem(ItemType.Keyboard, "Logitech",100.0);
        try {
        //Act
            conto.getOrderPrice(ordini, utente, LocalTime.of(00,00));
        }catch(BillException e){
        //Assert
            assertEquals("L'utente è nullo", e.getMessage());
        }
    }

    /* Attività 2: Se l'ordine ha più di 5 Processori il meno caro viene scontato del 50% */
    // Controllo che la somma totale aspettata, con sconto del 50% sul processore meno caro, corrisponda a quanto calcolato dalla funzione, in seguito all'ordine di 6 o più processori
    @Test
    public void testCalcoloTotale_OrdiniConPiuDi5Processori() throws BillException{
        //Arrange
        EShopBill conto = new EShopBill();
        User utente = new User("Mario", "Rossi", 22);
        List<EItem> ordini = new ArrayList<EItem>();
        EItem processore = new EItem(ItemType.Processor, "Intel",100.0);
        for(int i=0; i<6; i++){
            ordini.add(processore);
        }
        LocalTime ora = LocalTime.of(00, 00);
        //Act
        double totale = conto.getOrderPrice(ordini,utente,ora);
        //Assert
        assertEquals(550, totale, delta);
    }

    // Controllo che la somma totale aspettata, corrisponda a quanto calcolato dalla funzione, in seguito all'ordine di esattamente 5 (quindi non si applica lo sconto)
    @Test
    public void testCalcoloTotale_OrdiniConEsattamente5Processori() 
    throws BillException{
        //Arrange
        EShopBill conto = new EShopBill();
        User utente = new User("Mario", "Rossi", 22);
        List<EItem> ordini = new ArrayList<EItem>();
        EItem processore = new EItem(ItemType.Processor, "Intel",100.0);
        for(int i=0; i<5; i++){
            ordini.add(processore);
        }
        LocalTime ora = LocalTime.of(00, 00);
        //Act
        double totale = conto.getOrderPrice(ordini,utente,ora);
        //Assert
        assertEquals(500, totale, delta);
    }
}