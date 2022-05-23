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

    /* Attività 3: Se l'ordine ha più di 10 Mouse il meno caro viene regalato*/
    // Controllo che la somma totale aspettata, con regalo del mouse meno caro, corrisponda a quanto calcolato dalla funzione, in seguito all'ordine di 11 o più mouse 
    //
    @Test
    public void testCalcoloTotale_RegaloPerOrdiniConPiuDi10Mouse() throws BillException{
        //Arrange
        EShopBill conto = new EShopBill();
        User utente = new User("Mario", "Rossi", 22);
        List<EItem> ordini = new ArrayList<EItem>();
        EItem mouse = new EItem(ItemType.Mouse, "Logitech",50.0);
        for(int i=0; i<11; i++){
            ordini.add(mouse);
        }
        LocalTime ora = LocalTime.of(00, 00);
        //Act
        double totale = conto.getOrderPrice(ordini,utente,ora);
        //Assert
        assertEquals(500 , totale, delta);
    }

    @Test
    public void testCalcoloTotale_NoRegaloPerOrdiniDiEsattamente10Mouse() throws BillException{
        //Arrange
        EShopBill conto = new EShopBill();
        User utente = new User("Mario", "Rossi", 22);
        List<EItem> ordini = new ArrayList<EItem>();
        EItem mouse = new EItem(ItemType.Mouse, "Logitech",50.0);
        for(int i=0; i<10; i++){
            ordini.add(mouse);
        }
        LocalTime ora = LocalTime.of(00, 00);
        //Act
        double totale = conto.getOrderPrice(ordini,utente,ora);
        //Assert
        assertEquals(500 , totale, delta);
    }

    /* Attività 4: Se vengono ordinati lo stesso numero di Mouse e Tastiere viene regalato l’articolo meno caro*/
    // Controllo che la somma totale aspettata, con regalo del prodotto meno caro, corrisponda a quanto calcolato dalla funzione, in seguito all'ordine dello stesso numero di mouse e tastiere (>0 && <=10)
    @Test
    public void testCalcoloTotale_RegaloPerOrdiniStessoNumeroMouseETastiereMinoreUguale10() throws BillException{
        //Arrange
        EShopBill conto = new EShopBill();
        User utente = new User("Mario", "Rossi", 22);
        List<EItem> ordini = new ArrayList<EItem>();
        EItem mouse = new EItem(ItemType.Mouse, "Logitech",50.0);
        for(int i=0; i<5; i++){
            ordini.add(mouse);
        }
        EItem tastiere = new EItem(ItemType.Keyboard, "Logitech",100.0);
        for(int i=0; i<5; i++){
            ordini.add(tastiere);
        }
        LocalTime ora = LocalTime.of(00, 00);
        //Act
        double totale = conto.getOrderPrice(ordini,utente,ora);
        //Assert
        assertEquals(700 , totale, delta);
    }

    // Controllo che la somma totale aspettata, sia uguale alla somma del prezzo delle singole componenti, e che non venga sottratto il prezzo del mouse/tastiera meno caro, nel caso di 0 mouse e 0 tastiere
    @Test
    public void testCalcoloTotale_PerOrdini0Mouse0Tastiere() throws BillException{
        //Arrange
        EShopBill conto = new EShopBill();
        User utente = new User("Mario", "Rossi", 22);
        List<EItem> ordini = new ArrayList<EItem>();
        EItem processore = new EItem(ItemType.Processor, "Logitech",50.0);
        ordini.add(processore);
        LocalTime ora = LocalTime.of(00, 00);
        //Act
        double totale = conto.getOrderPrice(ordini,utente,ora);
        //Assert
        assertEquals(50 , totale, delta);
    }

    // Controllo che la somma totale aspettata, sia uguale alla somma del prezzo delle singole componenti, e che non venga sottratto il prezzo del mouse/tastiera meno caro, nel caso di quantità diverse di mouse e tastiere (>0 && <=10)
    @Test
    public void testCalcoloTotale_PerOrdiniDiversoNumeroMouseTastiereMinoreUgualeA10() 
    throws BillException{
        //Arrange
        EShopBill conto = new EShopBill();
        User utente = new User("Mario", "Rossi", 22);
        List<EItem> ordini = new ArrayList<EItem>();
        EItem mouse = new EItem(ItemType.Mouse, "Logitech",50.0);
        for(int i=0; i<5; i++){
            ordini.add(mouse);
        }
        EItem tastiere = new EItem(ItemType.Keyboard, "Logitech",100.0);
        for(int i=0; i<6; i++){
            ordini.add(tastiere);
        }
        LocalTime ora = LocalTime.of(00, 00);
        //Act
        double totale = conto.getOrderPrice(ordini,utente,ora);
        //Assert
        assertEquals(850 , totale, delta);
    }

    /* Attività 5: Se l’importo totale degli articoli supera i 1000 euro viene fatto uno sconto del 10% sul totale*/
    // Controllo che la somma totale aspettata, con sconto del 10%, corrisponda a quanto calcolato dalla funzione, in seguito al totale maggiore di 1000
    @Test
    public void testCalcoloTotale_OrdiniConTotaleMaggioreDi1000() throws BillException{
        //Arrange
        EShopBill conto = new EShopBill();
        User utente = new User("Mario", "Rossi", 22);
        List<EItem> ordini = new ArrayList<EItem>();
        EItem tastiera = new EItem(ItemType.Keyboard, "Logitech",100.0);
        for(int i=0; i<11; i++){
            ordini.add(tastiera);
        }
        LocalTime ora = LocalTime.of(00, 00);
        //Act
        double totale = conto.getOrderPrice(ordini,utente,ora);
        //Assert
        assertEquals(990 , totale, delta);
    }

    // Controllo che la somma totale aspettata, senza sconto del 10%, corrisponda a quanto calcolato dalla funzione, in seguito al totale uguale a 1000
    @Test
    public void testCalcoloTotale_OrdiniConTotaleUgualeA1000() throws BillException{
        //Arrange
        EShopBill conto = new EShopBill();
        User utente = new User("Mario", "Rossi", 22);
        List<EItem> ordini = new ArrayList<EItem>();
        EItem tastiera = new EItem(ItemType.Keyboard, "Logitech",100.0);
        for(int i=0; i<10; i++){
            ordini.add(tastiera);
        }
        LocalTime ora = LocalTime.of(00, 00);
        //Act
        double totale = conto.getOrderPrice(ordini,utente,ora);
        //Assert
        assertEquals(1000 , totale, delta);
    }

    /* Attività 6: Non è possibile avere un’ordinazione con più di 30 elementi (se accade prevedere un messaggio d’errore)*/
    // Controllo che la funzione ritorni un messaggio di errore se il numero di prodotti nell'ordine supera i 30
    @Test
    public void testNumeroProdottiMaggioreDi30() throws BillException{
        //Arrange
        EShopBill conto = new EShopBill();
        User utente = new User("Mario", "Rossi", 22);
        List<EItem> ordini = new ArrayList<EItem>();
        EItem tastiera = new EItem(ItemType.Keyboard, "Logitech",100.0);
        for(int i=0; i<31; i++){
            ordini.add(tastiera);
        }
        LocalTime ora = LocalTime.of(00, 00);
        //Act
        try{
           double totale = conto.getOrderPrice(ordini,utente,ora); 
        }catch(BillException e){
        //Assert
            assertEquals("Ordine con più di 30 articoli", e.getMessage());
        }
    }

    @Test
    public void testNumeroProdottiEsattamenteUgualeA30_EccezioneNonLanciata() throws BillException{
        //Arrange
        EShopBill conto = new EShopBill();
        User utente = new User("Mario", "Rossi", 22);
        List<EItem> ordini = new ArrayList<EItem>();
        EItem tastiera = new EItem(ItemType.Keyboard, "Logitech",10.0);
        for(int i=0; i<30; i++){
            ordini.add(tastiera);
        }
        LocalTime ora = LocalTime.of(00, 00);
        //Act
        double totale = conto.getOrderPrice(ordini,utente,ora);
        //Assert
        assertEquals(300, totale, delta);
    }

    /*Attività 7: Se l’importo totale è inferiore a 10 € viene aggiunta una commissione di 2€ */
    @Test
    public void testCalcoloTotale_PrezzoTotaleProdottiMinoreDi10() throws BillException{
        //Arrange
        EShopBill conto = new EShopBill();
        User utente = new User("Mario", "Rossi", 22);
        List<EItem> ordini = new ArrayList<EItem>();
        EItem mouse = new EItem(ItemType.Mouse, "Logitech", 9.99);
        ordini.add(mouse);
        LocalTime ora = LocalTime.of(00, 00);
        //Act
        double totale = conto.getOrderPrice(ordini,utente,ora);
        //Assert
        assertEquals(11.99 , totale, delta);
    }

    @Test
    public void testCalcoloTotale_PrezzoTotaleProdottiEsattamenteUgualeA10() throws BillException{
        //Arrange
        EShopBill conto = new EShopBill();
        User utente = new User("Mario", "Rossi", 22);
        List<EItem> ordini = new ArrayList<EItem>();
        EItem mouse = new EItem(ItemType.Mouse, "Logitech", 10);
        ordini.add(mouse);
        LocalTime ora = LocalTime.of(00, 00);
        //Act
        double totale = conto.getOrderPrice(ordini,utente,ora);
        //Assert
        assertEquals(10 , totale, delta);
    }

    /* Attività 8: Prevedere la possibilità di regalare, in modo casuale, 10 ordini effettuati dalle 18:00 alle 19:00 da utenti minorenni differenti.*/
    //Test che controlla che la funzione ritorni spesa 0, se l'utente passato alla funzione viene scelto in modo casuale, per ricevere in regalo l'ordine (se l'utente è minorenne e l'ordine è effettuato dalle 18:00 alle 19:00)
    @Test
    public void testRegaloOrdine_UtenteMinorenneOrarioOrdineFra18E19() throws BillException {
        //Arrange
        EShopBill conto = new EShopBill();
        User utente = new User("Mario", "Rossi", 17);
        List<EItem> ordini = new ArrayList<EItem>();
        EItem tastiera = new EItem(ItemType.Keyboard, "Logitech", 29.99);
        ordini.add(tastiera);
        LocalTime ora = LocalTime.of(18, 30);
        // Act
        conto.setSeed(1);
        double totale = conto.getOrderPrice(ordini,utente,ora);
        // Assert
        assertEquals(0, totale, delta);
    }

    @Test
    public void testRegaloOrdine_UndicesimoUtenteMinorenneGiaRegalato10Ordini() throws BillException{
        //Arrange
        EShopBill conto = new EShopBill();
        List<EItem> ordini1 = new ArrayList<EItem>();
        List<EItem> ordini2 = new ArrayList<EItem>();
        List<EItem> ordini3 = new ArrayList<EItem>();
        List<EItem> ordini4 = new ArrayList<EItem>();
        List<EItem> ordini5 = new ArrayList<EItem>();
        List<EItem> ordini6 = new ArrayList<EItem>();
        List<EItem> ordini7 = new ArrayList<EItem>();
        List<EItem> ordini8 = new ArrayList<EItem>();
        List<EItem> ordini9 = new ArrayList<EItem>();
        List<EItem> ordini10 = new ArrayList<EItem>();
        List<EItem> ordini11 = new ArrayList<EItem>();
        User utente1 = new User("Mario", "Rossi", 17);
        User utente2 = new User("Mario", "Rossi", 17);
        User utente3 = new User("Mario", "Rossi", 17);
        User utente4 = new User("Mario", "Rossi", 17);
        User utente5 = new User("Mario", "Rossi", 17);
        User utente6 = new User("Mario", "Rossi", 17);
        User utente7 = new User("Mario", "Rossi", 17);
        User utente8 = new User("Mario", "Rossi", 17);
        User utente9 = new User("Mario", "Rossi", 17);
        User utente10 = new User("Mario", "Rossi", 17);
        User utente11 = new User("Mario", "Rossi", 17);
        
        EItem tastiera = new EItem(ItemType.Keyboard, "Logitech", 30);
        LocalTime ora = LocalTime.of(18, 30);
        double totale = 0;
        ordini1.add(tastiera);
        ordini2.add(tastiera);
        ordini3.add(tastiera);
        ordini4.add(tastiera);
        ordini5.add(tastiera);
        ordini6.add(tastiera);
        ordini7.add(tastiera);
        ordini8.add(tastiera);
        ordini9.add(tastiera);
        ordini10.add(tastiera);
        ordini11.add(tastiera);
        //Act
        conto.setSeed(1);
        totale += conto.getOrderPrice(ordini1,utente1,ora); 
        conto.setSeed(1);
        totale += conto.getOrderPrice(ordini2,utente2,ora);
        conto.setSeed(1);
        totale += conto.getOrderPrice(ordini3,utente3,ora);
        conto.setSeed(1);
        totale += conto.getOrderPrice(ordini4,utente4,ora);
        conto.setSeed(1);
        totale += conto.getOrderPrice(ordini5,utente5,ora);
        conto.setSeed(1);
        totale += conto.getOrderPrice(ordini6,utente6,ora);
        conto.setSeed(1);
        totale += conto.getOrderPrice(ordini7,utente7,ora);
        conto.setSeed(1);
        totale += conto.getOrderPrice(ordini8,utente8,ora);
        conto.setSeed(1);
        totale += conto.getOrderPrice(ordini9,utente9,ora);
        conto.setSeed(1);
        totale += conto.getOrderPrice(ordini10,utente10,ora);
        conto.setSeed(1);
        totale += conto.getOrderPrice(ordini11,utente11,ora);
        // Assert
        assertEquals(30, totale, delta);
    }

    @Test
    public void testRegaloOrdine_UtenteMaggiorenneOrarioOrdineFra18E19() throws BillException {
        //Arrange
        EShopBill conto = new EShopBill();
        User utente = new User("Mario", "Rossi", 19);
        List<EItem> ordini = new ArrayList<EItem>();
        EItem tastiera = new EItem(ItemType.Keyboard, "Logitech", 29.99);
        ordini.add(tastiera);
        LocalTime ora = LocalTime.of(18, 30);
        // Act
        conto.setSeed(1);
        double totale = conto.getOrderPrice(ordini,utente,ora);
        // Assert
        assertEquals(29.99, totale, delta);
    }

    @Test
    public void testRegaloOrdine_DueOrdiniStessoUtenteMinorenneOrarioFra18E19() throws BillException {
        //Arrange
        EShopBill conto = new EShopBill();
        User utente = new User("Mario", "Rossi", 17);
        List<EItem> ordini1 = new ArrayList<EItem>();
        List<EItem> ordini2 = new ArrayList<EItem>();
        EItem tastiera = new EItem(ItemType.Keyboard, "Logitech", 29.99);
        ordini1.add(tastiera);
        ordini2.add(tastiera);
        LocalTime ora = LocalTime.of(18, 30);
        // Act
        conto.setSeed(1);
        double totale = conto.getOrderPrice(ordini1,utente,ora);
        conto.setSeed(1);
        totale += conto.getOrderPrice(ordini2,utente,ora);
        // Assert
        assertEquals(29.99, totale, delta);
    }

    @Test
    public void testRegaloOrdine_UtenteMinorenneOrarioOrdinePrimaDelle18() throws BillException {
        //Arrange
        EShopBill conto = new EShopBill();
        User utente = new User("Mario", "Rossi", 17);
        List<EItem> ordini = new ArrayList<EItem>();
        EItem tastiera = new EItem(ItemType.Keyboard, "Logitech", 29.99);
        ordini.add(tastiera);
        LocalTime ora = LocalTime.of(15, 30);
        // Act
        conto.setSeed(1);
        double totale = conto.getOrderPrice(ordini,utente,ora);
        // Assert
        assertEquals(29.99, totale, delta);
    }

    @Test
    public void testRegaloOrdine_UtenteMinorenneOrarioOrdineDopoLe19() throws BillException {
        //Arrange
        EShopBill conto = new EShopBill();
        User utente = new User("Mario", "Rossi", 17);
        List<EItem> ordini = new ArrayList<EItem>();
        EItem tastiera = new EItem(ItemType.Keyboard, "Logitech", 29.99);
        ordini.add(tastiera);
        LocalTime ora = LocalTime.of(20, 30);
        // Act
        conto.setSeed(1);
        double totale = conto.getOrderPrice(ordini,utente,ora);
        // Assert
        assertEquals(29.99, totale, delta);
    }

    /* Test finali */
    // Controllo che la somma totale aspettata, con regalo del prodotto meno caro, corrisponda a quanto calcolato dalla funzione, in seguito all'ordine di 11 o più mouse e tastiere
    @Test
    public void testCalcoloTotale_OrdiniStessoNumeroMouseTastierePiuDi10ConSconto() throws BillException{
        //Arrange
        EShopBill conto = new EShopBill();
        User utente = new User("Mario", "Rossi", 22);
        List<EItem> ordini = new ArrayList<EItem>();
        EItem mouse = new EItem(ItemType.Mouse, "Logitech",50.0);
        for(int i=0; i<11; i++){
            ordini.add(mouse);
        }

        EItem tastiere = new EItem(ItemType.Keyboard, "Logitech",100.0);
        for(int i=0; i<11; i++){
            ordini.add(tastiere);
        }
        LocalTime ora = LocalTime.of(00, 00);
        //Act
        double totale = conto.getOrderPrice(ordini,utente,ora);
        //Assert -> [11*50 + 11*100 - 50 (stesso # mouse e tastiere) - 50 (>10 mouse)]*0.9 (sconto del 10% costo > 1000)) 
        assertEquals(1395 , totale, delta);
    }

    @Test
    public void testCalcoloTotale_OrdiniConTotaleMaggioreDi1000ConSconto() throws BillException{
        //Arrange
        EShopBill conto = new EShopBill();
        User utente = new User("Mario", "Rossi", 22);
        List<EItem> ordini = new ArrayList<EItem>();
        EItem tastiera = new EItem(ItemType.Keyboard, "Logitech",100.0);
        for(int i=0; i<7; i++){
            ordini.add(tastiera);
        }

        EItem mouse = new EItem(ItemType.Processor, "Intel",200);
        for(int i=0; i<6; i++){
            ordini.add(mouse);
        }
        LocalTime ora = LocalTime.of(00, 00);
        //Act
        double totale = conto.getOrderPrice(ordini,utente,ora);
        //Assert
        assertEquals(1620, totale, delta);
    }
}