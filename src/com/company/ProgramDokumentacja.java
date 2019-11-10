
package com.company;

import java.awt.GridLayout;
import java.io.IOException;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

class ProgramDokumentacja {
private JList<String> listaPlikow;
private JList<String> listaKatalogow;
private JTextArea podglad;
private String katalog;
private Dane dane = new Dane();

ProgramDokumentacja() {
}

private JScrollPane Lista_Katalogow(String[] listaKatalogow_filtr) {
this.listaKatalogow = new JList<>(listaKatalogow_filtr);
this.listaKatalogow.setSelectionMode(0);
this.listaKatalogow.setLayoutOrientation(0);
JScrollPane listScroller_kat = new JScrollPane(this.listaKatalogow);
this.listaKatalogow.setSelectedIndex(0);
this.listaKatalogow.addListSelectionListener((e) -> {
    this.listaPlikow.clearSelection();
    if (!e.getValueIsAdjusting()) {
        String selectedValuesList = this.listaKatalogow.getSelectedValue();
        this.katalog = "Dane/" + selectedValuesList + "/";
        this.listaPlikow.setListData(this.niePelnaSciezka_pliki(this.katalog));
        this.listaPlikow.setSelectedIndex(0);
    }

});
return listScroller_kat;
}

private JScrollPane Lista_Pliki(String[] lista) {
this.listaPlikow = new JList<>(lista);
this.listaPlikow.setSelectionMode(0);
this.listaPlikow.setLayoutOrientation(0);
this.listaPlikow.addListSelectionListener((e) -> {
    if (!e.getValueIsAdjusting()) {
        if (this.listaPlikow.getSelectedValue() == null) {
            this.listaPlikow.setSelectedIndex(0);
        }

        try {
            this.podglad.setText(this.dane.odczyt(this.pelnaSciezka_pliki(this.katalog)[this.listaPlikow.getSelectedIndex()]));
        } catch (IOException var3) {
            var3.printStackTrace();
        }
    }

});
return new JScrollPane(this.listaPlikow);
}

private JTextArea podgladPlikow() {
this.podglad = new JTextArea("fassfdg");
return this.podglad;
}

private String[] pelnaSciezka_pliki(String katalog) {
return dane.lista_katalogow_Jar().stream().filter(x -> x.startsWith(katalog)).filter(x -> x.endsWith("txt")).toArray(String[]::new);
}

private String[] niePelnaSciezka_pliki(String katalog) {
return dane.lista_katalogow_Jar().stream().filter(x -> x.startsWith(katalog)).filter(x -> x.endsWith("txt")).map(x -> x.substring(katalog.length())).map(x -> x.replace("/", " ==> ")).map(x -> x.substring(0, x.lastIndexOf(".txt"))).toArray(String[]::new);
}

JPanel Dokumentacja_panel_Center() {
JPanel panel = new JPanel(new GridLayout(1, 3));
panel.add(this.Lista_Katalogow(dane.lista_katalogow_Jar().stream().filter(x -> x.startsWith("Dane/")).filter(x -> x.endsWith("txt")).map(x -> x.substring(5)).map(x -> x.substring(0, x.indexOf("/"))).distinct().sorted().toArray(String[]::new)));
panel.add(this.Lista_Pliki(niePelnaSciezka_pliki("Dane/")));
panel.add(this.podgladPlikow());
return panel;
}
}
