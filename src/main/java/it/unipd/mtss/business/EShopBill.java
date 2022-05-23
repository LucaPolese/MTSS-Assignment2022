////////////////////////////////////////////////////////////////////
// Marco Mamprin 1230233
// Luca Polese 1225425
////////////////////////////////////////////////////////////////////
package it.unipd.mtss.business;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import it.unipd.mtss.business.exception.BillException;
import it.unipd.mtss.model.EItem;
import it.unipd.mtss.model.ItemType;
import it.unipd.mtss.model.User;

public class EShopBill implements Bill {
    private List<User> utentiRegalo = new ArrayList<User>();
    private Random random = new Random();

    public double getOrderPrice(List<EItem> items, User user, LocalTime time) 
    throws BillException {
        //Verifico che il dominio degli parametri sia corretto 
        //altrimenti ritorno un'eccezione
        checkParamsDomain(items,user,time);

        //Attività 1
        double totalPrice = 0;
        //Attività 2
        int numProcessori = 0;
        double minProcessore = -1;
        //Attività 3
        int numMouse = 0;
        double minMouse = -1;
        //Attività 4
        int numTastiere = 0;
        double minArticolo = -1;

        for (EItem item : items) {
            //Attività 1
            totalPrice += item.getPrice();
            //Attività 2
            if (item.getItemType().equals(ItemType.Processor)) {
                numProcessori++;
                if(minProcessore == -1 || item.getPrice() < minProcessore) {
                    minProcessore = item.getPrice();
                }
            }
            //Attività 3
            if (item.getItemType().equals(ItemType.Mouse)) {
                numMouse++;
                if(minMouse == -1 || item.getPrice() < minMouse) {
                    minMouse = item.getPrice();
                }
            }
            //Attività 4
            if (item.getItemType().equals(ItemType.Keyboard)) {
                numTastiere++;
            }
            if(minArticolo == -1 || item.getPrice() < minArticolo) {
                minArticolo = item.getPrice();
            }
        }

        //Attività 2
        if(numProcessori > 5) {
            totalPrice -= minProcessore * 0.5;
        }
        //Attività 3
        if(numMouse > 10) {
            totalPrice -= minMouse;
        }
        //Attività 4
        if(numTastiere == numMouse && numTastiere > 0) {
            totalPrice -= minArticolo;
        }
        //Attività 5
        if(totalPrice > 1000) {
            totalPrice = totalPrice * 0.9;
        }
        //Attività 7
        if(totalPrice < 10) {
            totalPrice += 2;
        }

        //Attività 8
        if(giftAnOrderTo10Users(user,time)) {
            totalPrice = 0;
        }

        return totalPrice;
    }

    public void checkParamsDomain(List<EItem> items, User user, LocalTime time) 
    throws BillException{
        
        if(user == null){
            throw BillException.nullUser(); 
        }

        if(time == null) {
            throw BillException.nullTime();
        }

        if (items == null || items.size() == 0) {
            throw BillException.emptyOrder();
        }

        if (items.size() > 30) {
            throw BillException.over30items();
        }

        for (EItem item : items) {
            if (item == null) {
                throw BillException.nullEItem();
            }
            if (item.getPrice() < 0) {
                throw BillException.negativePrice();
            }
        }

    }

    private boolean giftAnOrderTo10Users(User user, LocalTime time) 
    throws BillException {
        //Controllo i dati in ingresso

        //Se la size di utentiRegalo è minore di 10
        //Se l'utente NON è nella lista 
        //Se l'orario è tra le 18 e le 19
        if(utentiRegalo.size()<10 
        && checkGiveawayTime(time) 
        && !utentiRegalo.contains(user) 
        && randomValueBetweenZeroAndOne() == 1) {
            boolean userIsMinor = user.getEta() < 18;
            //Se l'utente è minorenne
            if (userIsMinor) {
                utentiRegalo.add(user);
                return true;
            }
        }
        return false;
    }

    private boolean checkGiveawayTime(LocalTime ora) {
        return ora.isAfter(LocalTime.of(18, 00)) 
        && ora.isBefore(LocalTime.of(19, 00));
    }

    private int randomValueBetweenZeroAndOne() {
        return random.nextInt(2);
    }

    public void setSeed(long seed) {
        random.setSeed(seed);
    }
}
