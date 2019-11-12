package com.company;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
class ProgramGeneratorZapytanSQlite_versja2
    {

    JLabel kolrowid;



    private JComboBox comboNazwaKsiegi;
    private JTextArea obszarTekstowy1;
    private JTextArea obszarTekstowy2;
    private JTextArea obszarTekstowy3;
    private String[] przyciski = new String[]{"Moj_1_Mojzeszowa", "Moj_2_Mojzeszowa", "Moj_3_Mojzeszowa", "Moj_4_Mojzeszowa", "Moj_5_Mojzeszowa", "Ksiega_Jozuego", "Ksiega_Sedziow",
            "Ksiega_Rut", "Ksiega_I_Samuel", "Ksiega_II_Samuel", "I_Ksiega_Krolewska", "II_Ksiega_Krolewska", "I_Ksiega_Kronik", "II Księga Kronik", "Ksiega_Ezdrasza", "Ksiega_Nehemiasza", "Ksiega_Estery", "Ksiega_Hioba", "Psalmy", "Ksiega_Przyslow", "Ksiega_Kaznodziei", "Piesn_Nad_Piesniami", "Ksiega_Izajasza", "Ksiega_Jeremiasza", "Lamentacje", "Ksiega_Ezechiela", "Ksiega_Daniela", "Ksiega_Ozeasza", "Ksiega_Joela", "Ksiega_Amosa", "Ksiega_Abdiasza", "Ksiega_Jonasza", "Ksiega_Micheasza", "Ksiega_Nahuma", "Ksiega_Habakuka", "Ksiega_Sofoniasza", "Ksiega_Aggeusza", "Ksiega_Zachariasza", "Ksiega_Malachiasza", "Ewangelia_Mateusza", "Ewangelia_Marka", "Ewangelia_Lukasza", "Ewangelia_Jana", "Dzieje_Apostolskie", "List_Do_Rzymian", "I_List_Do_Koryntian", "II_List_Do_Koryntian", "List_Do_Galacjan", "List_Do_Efezjan", "List_Do_Filipian", "List_Do_Kolosan", "I_List_Do_Tesaloniczan", "II_List_Do_Tesaloniczan", "I_List_Do_Tymoteusza", "II_List_Do_Tymoteusza", "List_Do_Tytusa", "List_Do_Filemona", "List_Do_Hebrajczykow", "List_Jakuba", "I_List_Piotra", "II_List_Piotra", "I_List_Jana", "II_List_Jana", "III_List_Jana", "List_Judy", "Ksiega_Objawienia"};

    List<String> listaObszar1 = new ArrayList<>();
    List<String> listaObszar2 = new ArrayList<>();
    List<String> listakolumnbazydanych = new ArrayList<>();

    String str_select = "SELECT ";
String str_nazwaKsiegibezko;

    private String str_nazwaKsiegi;
    private JLabel kolroz,koltem,dodajKsiege;


    private JButton buttonprzyciskiKsiag(String Nazwa)
        {
        JButton ksiegi = new JButton(Nazwa);

        ksiegi.addActionListener(e -> {

            String ksiegaDodawana =e.getActionCommand()+"_Komentarze";

        listaObszar1.add("\n|| ifnull("+ksiegaDodawana+"._99_Komentarz, \"\")");

        listaObszar2.add("\n \tLEFT JOIN " +ksiegaDodawana+" ON "+ksiegaDodawana+"._97_Komentarz_rowid="+listakolumnbazydanych.get(2)+" AND "+ksiegaDodawana+"._98_Dotyczy_Ksiegi="+zamienNazweKsieginaNumer(str_nazwaKsiegibezko));

        obszarTekstowy1.append(listaObszar1.get(listaObszar1.size()-1));
        obszarTekstowy2.append(listaObszar2.get(listaObszar2.size()-1));









        });

        return ksiegi;
        }
    private JButton buttonPokazCalosc()
        {
        JButton pokazCalosc = new JButton("Pokaż Całość");

        pokazCalosc.addActionListener((e) -> {

        });
        return pokazCalosc;
        }
    private JButton buttonZaznaczKopiuj()
        {
        JButton dodajzapisz = new JButton("Zaznacz/Kopiuj");
        dodajzapisz.addActionListener((e) -> {


        });
        return dodajzapisz;
        }
    private JLabel etykietaKolumnaRowid()
        {
        kolrowid = new JLabel("Rowid");
        return kolrowid;
        }
    private JLabel etykietaKolumnaRozdzialy()
        {
        kolroz = new JLabel("Rozdzialy");
        return kolroz;
        }
    private JLabel etykietaKolumnaTemat()
        {
        koltem = new JLabel("Temat");
        return koltem;
        }
    private JLabel etykietaKolumnaDodajKsiege()
        {
        dodajKsiege = new JLabel("Dodaj Ksiegę");
        return dodajKsiege;
        }
    private JComboBox comboBoxNazwaKsiegi()
        {

        DefaultComboBoxModel<String> modelBoxNazwaKsiegi = new DefaultComboBoxModel<>(przyciski);
        comboNazwaKsiegi = new JComboBox<>(modelBoxNazwaKsiegi);

            comboNazwaKsiegi.addActionListener(e -> {

                listaObszar1.clear();
                listakolumnbazydanych.clear();

            JComboBox wybor = (JComboBox) e.getSource();
            str_nazwaKsiegibezko= Objects.requireNonNull(wybor.getSelectedItem()).toString();
            str_nazwaKsiegi = str_nazwaKsiegibezko+"_Komentarze";
                try {
                    listakolumnbazydanych.addAll(BazaDanych.polacz(str_nazwaKsiegi));
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                kolrowid.setText(listakolumnbazydanych.get(2));
                kolroz.setText(listakolumnbazydanych.get(1));
                koltem.setText(listakolumnbazydanych.get(0));


                listaObszar1.add(str_select+str_nazwaKsiegi+listakolumnbazydanych.get(1)+",Bible.Verse,Bible.Scripture,\n\t"+str_nazwaKsiegi+"."+listakolumnbazydanych.get(0));
                listaObszar2.add(" AS "+listakolumnbazydanych.get(0)+" FROM Bible INNER JOIN "+str_nazwaKsiegi+" ON "+listakolumnbazydanych.get(0)+"=Bible._rowid_");



                obszarTekstowy1.setText(listaObszar1.get(0));
                obszarTekstowy2.setText(listaObszar2.get(0));

        });


        return comboNazwaKsiegi;
        }
    private JScrollPane textArea1() throws IOException
        {
        obszarTekstowy1 = new JTextArea();

        return new JScrollPane(obszarTekstowy1);
        }
    private JScrollPane textArea2() throws IOException
        {

        obszarTekstowy2 = new JTextArea();

        return new JScrollPane(obszarTekstowy2);
        }
    private JScrollPane textArea3()
        {
        this.obszarTekstowy3 = new JTextArea();
        return new JScrollPane(this.obszarTekstowy3);
        }
    JPanel generatorSQlite_panel_north2()
        {

        JPanel panel_1 = new JPanel();
        panel_1.setLayout(new GridLayout(13, 5));
        for (int x = 0; x < 66; ++x) {
            panel_1.add(this.buttonprzyciskiKsiag(this.przyciski[x]));
        }
        return panel_1;
        }
    JPanel generatorSQlite_panel_center2() throws IOException
        {
        JPanel panel_2 = new JPanel();
        panel_2.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = 2;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0D;
        c.insets = new Insets(0, 0, 0, 2);
        panel_2.add(comboBoxNazwaKsiegi(), c);
        c.fill = 2;
        c.gridx = 1;
        c.gridy = 0;
        panel_2.add(etykietaKolumnaRowid(), c);
        c.fill = 2;
        c.gridx = 2;
        c.gridy = 0;
        panel_2.add(etykietaKolumnaRozdzialy(), c);
        c.fill = 2;
        c.gridx = 3;
        c.gridy = 0;
        panel_2.add(etykietaKolumnaTemat(), c);
        c.fill = 2;
        c.gridx = 4;
        c.gridy = 0;
        panel_2.add(etykietaKolumnaDodajKsiege(), c);
        c.fill = 2;
        c.insets = new Insets(0, 1, 0, 1);
        c.gridx = 0;
        c.gridy = 2;
        c.ipady = 500;
        c.weightx = 0.0D;
        c.gridwidth = 1;
        panel_2.add(this.textArea1(), c);
        c.gridx = 1;
        c.gridy = 2;
        c.ipady = 500;
        c.weightx = 0.0D;
        c.gridwidth = 2;
        panel_2.add(this.textArea2(), c);
        c.gridx = 3;
        c.gridy = 2;
        c.ipady = 500;
        c.weightx = 0.0D;
        c.gridwidth = 2;
        panel_2.add(this.textArea3(), c);
        c.gridx = 2;
        c.gridy = 3;
        c.ipady = 0;
        c.weightx = 0.0D;
        c.gridwidth = 1;
        panel_2.add(this.buttonZaznaczKopiuj(), c);
        c.gridx = 3;
        c.gridy = 3;
        c.ipady = 0;
        c.weightx = 0.0D;
        c.gridwidth = 1;
        panel_2.add(this.buttonPokazCalosc(), c);
            return panel_2;
        }
    private String zamienNazweKsieginaNumer(String NazwaKsiegi)
        {

        switch (NazwaKsiegi) {
            case "Moj_1_Mojzeszowa":
                return "1";
            case "Moj_2_Mojzeszowa":
                return "2";
            case "Moj_3_Mojzeszowa":
                return "3";
            case "Moj_4_Mojzeszowa":
                return "4";
            case  "Moj_5_Mojzeszowa":
                return "5";
            case "Ksiega_Jozuego":
                return "6";
            case "Ksiega_Sedziow":
                return "7";
            case "Ksiega_Rut":
                return "8";
            case "Ksiega_I_Samuel":
                return "9";
            case "Ksiega_II_Samuel":
                return "10";
            case "I_Ksiega_Krolewska":
                return "11";
            case "II Księga Królewska":
                return "12";
            case "I Księga Kronik":
                return "13";
            case "II Księga Kronik":
                return "14";
            case "Księga Ezdrasza":
                return "15";
            case "Księga Nehemiasza":
                return "16";
            case "Księga Estery":
                return "17";
            case "Księga Hioba":
                return "18";
            case "Księga Psalmów":
                return "19";
            case "Księga Przysłów":
                return "20";
            case "Księga Kaznodziei":
                return "21";
            case "Pieśń nad Pieśniami":
                return "22";
            case "Księga Izajasza":
                return "23";
            case "Księga Jeremiasza":
                return "24";
            case "Lamentacje":
                return "25";
            case "Księga Ezechiela":
                return "26";
            case "Księga Daniela":
                return "27";
            case "Księga Ozeasza":
                return "28";
            case "Księga Joela":
                return "29";
            case "Księga Amosa":
                return "30";
            case "Księga Abdiasza":
                return "31";
            case "Księga Jonasza":
                return "32";
            case "Księga Micheasza":
                return "33";
            case "Księga Nahuma":
                return "34";
            case "Księga Habakuka":
                return "35";
            case "Księga Sofoniasza":
                return "36";
            case "Księga Aggeusza":
                return "37";
            case "Księga Zachariasza":
                return "38";
            case "Księga Malachiasza":
                return "39";
            case "Ewangelia Mateusza":
                return "40";
            case "Ewangelia Marka":
                return "41";
            case "Ewangelia Łukasza":
                return "42";
            case "Ewangelia Jana":
                return "43";
            case "Dzieje Apostolskie":
                return "44";
            case "List do Rzymian":
                return "45";
            case "I List do Koryntian":
                return "46";
            case "II List do Koryntian":
                return "47";
            case "List do Galacjan":
                return "48";
            case "List do Efezjan":
                return "49";
            case "List do Filipian":
                return "50";
            case "List do Kolosan":
                return "51";
            case "I List do Tesaloniczan":
                return "52";
            case "II List do Tesaloniczan":
                return "53";
            case "I List do Tymoteusza":
                return "54";
            case "II List do Tymoteusza":
                return "55";
            case "List do Tytusa":
                return "56";
            case "List do Filemona":
                return "57";
            case "List do Hebrajczyków":
                return "58";
            case "List Jakuba":
                return "59";
            case "I List Piotra":
                return "60";
            case "II List Piotra":
                return "61";
            case "1 List Jana":
                return "62";
            case "2 List Jana":
                return "63";
            case "3 List Jana":
                return "64";
            case "List Judy":
                return "65";
            case "Księga Objawienia":
                return "66";
            case "Potrzebujesz Zbawienia":
                return "700";
            case "Nie możesz sam siebie zbawić":
                return "701";
            case "Bóg przygotował już dla Ciebie zbawienie":
                return "702";
            case "Pan Jezus ma moc, aby Cię zbawić i strzec":
                return "703";
            case "Odwróć się od grzechu":
                return "704";
            case "Uwierz w Jezusa":
                return "705";
            case "Wyznaj Bogu swoje grzechy":
                return "706";
            case "Jezus Eucharystyczny VS Jezus Biblijny":
                return "707";
        }
        return "0";
        }

    }
