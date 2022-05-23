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

        for (EItem item : items) {
            //Attività 1
            totalPrice += item.getPrice();
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

        for (EItem item : items) {
            if (item == null) {
                throw BillException.nullEItem();
            }
            if (item.getPrice() < 0) {
                throw BillException.negativePrice();
            }
        }

    }
}
