package it.unipd.mtss.model;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.*;

public class EItemTest {
    private static final double delta = 0.001;

    private static List<EItem> prodotti;

    @Test
    public void testGetItemType() {
        prodotti = new ArrayList<EItem>();
        prodotti.add(new EItem(ItemType.Mouse,"Razer", 12.3));
        prodotti.add(new EItem(ItemType.Keyboard,"Logitech", 15.5));
        prodotti.add(new EItem(ItemType.Processor, "Intel", 350.0));
        prodotti.add(new EItem(ItemType.Motherboard, "Asus", 200.0));

        assertEquals(ItemType.Mouse, prodotti.get(0).getItemType());
        assertEquals(ItemType.Keyboard, prodotti.get(1).getItemType());
        assertEquals(ItemType.Processor, prodotti.get(2).getItemType());
        assertEquals(ItemType.Motherboard, prodotti.get(3).getItemType());
    }

    @Test
    public void testGetName() {
        assertEquals("Razer", prodotti.get(0).getName());
        assertEquals("Logitech", prodotti.get(1).getName());
        assertEquals("Intel", prodotti.get(2).getName());
        assertEquals("Asus", prodotti.get(3).getName());
    }

    @Test
    public void testGetPrice() {
        assertEquals(12.3, prodotti.get(0).getPrice(), delta);
        assertEquals(15.5, prodotti.get(1).getPrice(), delta);
        assertEquals(350.0, prodotti.get(2).getPrice(), delta);
        assertEquals(200.0, prodotti.get(3).getPrice(), delta);
    }
}
