package edu.eseiaat.upc.pma.borreguero.daniel.shoppinglist;

/**
 * Created by PortatilDani on 20/10/2017.
 */

public class ShoppingItem {
    private String texto;
    private boolean check;

    public ShoppingItem(String texto) {
        this.texto = texto;
    }

    public ShoppingItem(String texto, boolean check) {
        this.texto = texto;
        this.check = check;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public void changecheck() {
        this.check=!this.check;
    }
}
