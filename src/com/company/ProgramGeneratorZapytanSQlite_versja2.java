package com.company;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
class ProgramGeneratorZapytanSQlite_versja2
    {
    private JLabel kolrowid;
    private JComboBox comboNazwaKsiegi;
    private JTextArea obszarTekstowy1;
    private JTextArea obszarTekstowy2;
    private JTextArea obszarTekstowy3;
    private String[] przyciski = new String[]{"Moj_1_Mojzeszowa", "Moj_2_Mojzeszowa", "Moj_3_Mojzeszowa", "Moj_4_Mojzeszowa", "Moj_5_Mojzeszowa", "Ksiega_Jozuego", "Ksiega_Sedziow",
            "Ksiega_Rut", "Ksiega_I_Samuel", "Ksiega_II_Samuel", "I_Ksiega_Krolewska", "II_Ksiega_Krolewska", "I_Ksiega_Kronik", "II Księga Kronik", "Ksiega_Ezdrasza", "Ksiega_Nehemiasza",
            "Ksiega_Estery", "Ksiega_Hioba", "Psalmy", "Ksiega_Przyslow", "Ksiega_Kaznodziei", "Piesn_Nad_Piesniami", "Ksiega_Izajasza", "Ksiega_Jeremiasza", "Lamentacje", "Ksiega_Ezechiela",
            "Ksiega_Daniela", "Ksiega_Ozeasza", "Ksiega_Joela", "Ksiega_Amosa", "Ksiega_Abdiasza", "Ksiega_Jonasza", "Ksiega_Micheasza", "Ksiega_Nahuma", "Ksiega_Habakuka", "Ksiega_Sofoniasza",
            "Ksiega_Aggeusza", "Ksiega_Zachariasza", "Ksiega_Malachiasza", "Ewangelia_Mateusza", "Ewangelia_Marka", "Ewangelia_Lukasza", "Ewangelia_Jana", "Dzieje_Apostolskie", "List_Do_Rzymian",
            "I_List_Do_Koryntian", "II_List_Do_Koryntian", "List_Do_Galacjan", "List_Do_Efezjan", "List_Do_Filipian", "List_Do_Kolosan", "I_List_Do_Tesaloniczan", "II_List_Do_Tesaloniczan",
            "I_List_Do_Tymoteusza", "II_List_Do_Tymoteusza", "List_Do_Tytusa", "List_Do_Filemona", "List_Do_Hebrajczykow", "List_Jakuba", "I_List_Piotra", "II_List_Piotra", "I_List_Jana", "II_List_Jana",
            "III_List_Jana", "List_Judy", "Ksiega_Objawienia"};
    private List<String> listaObszar1 = new ArrayList<>();
    private List<String> listaObszar2 = new ArrayList<>();
    private List<String> listakolumnbazydanych = new ArrayList<>();
    private String str_select = "SELECT ";
    private String str_nazwaKsiegibezko;
    private String str_nazwaKsiegi;
    private JLabel kolroz;
    private JLabel koltem;

    private JButton buttonprzyciskiKsiag(String Nazwa)
        {
        JButton ksiegi = new JButton(Nazwa);
        ksiegi.addActionListener(e -> {
            String ksiegaDodawana = e.getActionCommand() + "_Komentarze";
            listaObszar1.add("\n|| ifnull(" + ksiegaDodawana + "._99_Komentarz, \"\")");
            listaObszar2.add("\n \tLEFT JOIN " + ksiegaDodawana + " ON " + ksiegaDodawana + "._97_Komentarz_rowid=" + listakolumnbazydanych.get(2) + " AND " + ksiegaDodawana + "._98_Dotyczy_Ksiegi=" + zamienNazweKsieginaNumer(str_nazwaKsiegibezko));
            obszarTekstowy1.append(listaObszar1.get(listaObszar1.size() - 1));
            obszarTekstowy2.append(listaObszar2.get(listaObszar2.size() - 1));
            obszarTekstowy3.setText(obszarTekstowy1.getText() + "\n" + obszarTekstowy2.getText());
            String[] ko = new String[]{str_nazwaKsiegibezko, obszarTekstowy1.getText(), obszarTekstowy2.getText()};
            watek_zapis(ko);
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
        JButton dodajzapisz = new JButton("Zaznacz/Kopiuj/Zapisz");
        dodajzapisz.addActionListener((e) -> {
            obszarTekstowy3.selectAll();
            obszarTekstowy3.copy();
            dodajzapisz.transferFocusBackward();
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
        return new JLabel("Dodaj Ksiegę");
        }
    private JComboBox comboBoxNazwaKsiegi()
        {
        DefaultComboBoxModel<String> modelBoxNazwaKsiegi = new DefaultComboBoxModel<>(przyciski);
        comboNazwaKsiegi = new JComboBox<>(modelBoxNazwaKsiegi);
        comboNazwaKsiegi.addActionListener(e -> {
            listaObszar1.clear();
            listakolumnbazydanych.clear();
            JComboBox wybor = (JComboBox) e.getSource();
            str_nazwaKsiegibezko = Objects.requireNonNull(wybor.getSelectedItem()).toString();
            str_nazwaKsiegi = str_nazwaKsiegibezko + "_Komentarze";
            try {
                listakolumnbazydanych.addAll(BazaDanych.polacz(str_nazwaKsiegi));
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            kolrowid.setText(listakolumnbazydanych.get(2));
            kolroz.setText(listakolumnbazydanych.get(1));
            koltem.setText(listakolumnbazydanych.get(0));
            listaObszar1.add(str_select + str_nazwaKsiegi + "." + listakolumnbazydanych.get(1) + ",Bible.Verse,Bible.Scripture,\n\t" + str_nazwaKsiegi + "." + listakolumnbazydanych.get(0));
            listaObszar2.add(" AS " + listakolumnbazydanych.get(0) + " FROM Bible INNER JOIN " + str_nazwaKsiegi + " ON " + listakolumnbazydanych.get(2) + "=Bible._rowid_");
            obszarTekstowy1.setText(listaObszar1.get(0));
            obszarTekstowy2.setText(listaObszar2.get(0));
            obszarTekstowy3.setText(listaObszar1.get(0) + "\n" + listaObszar2.get(0));
            String[] ko = new String[]{str_nazwaKsiegibezko, obszarTekstowy1.getText(), obszarTekstowy2.getText()};
            watek_zapis(ko);
        });
        return comboNazwaKsiegi;
        }
    private JScrollPane textArea1()
        {
        obszarTekstowy1 = new JTextArea();
        return new JScrollPane(obszarTekstowy1);
        }
    private JScrollPane textArea2()
        {
        obszarTekstowy2 = new JTextArea();
        return new JScrollPane(obszarTekstowy2);
        }
    private JScrollPane textArea3()
        {
        this.obszarTekstowy3 = new JTextArea();
        watek_odczyt();
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
    JPanel generatorSQlite_panel_center2()
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
            case "Moj_5_Mojzeszowa":
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
            case "II_Ksiega_Krolewska":
                return "12";
            case "I_Ksiega_Kronik":
                return "13";
            case "II Księga Kronik":
                return "14";
            case "Ksiega_Ezdrasza":
                return "15";
            case "Ksiega_Nehemiasza":
                return "16";
            case "Ksiega_Estery":
                return "17";
            case "Ksiega_Hioba":
                return "18";
            case "Psalmy":
                return "19";
            case "Ksiega_Przyslow":
                return "20";
            case "Ksiega_Kaznodziei":
                return "21";
            case "Piesn_Nad_Piesniami":
                return "22";
            case "Ksiega_Izajasza":
                return "23";
            case "Ksiega_Jeremiasza":
                return "24";
            case "Lamentacje":
                return "25";
            case "Ksiega_Ezechiela":
                return "26";
            case "Ksiega_Daniela":
                return "27";
            case "Ksiega_Ozeasza":
                return "28";
            case "Ksiega_Joela":
                return "29";
            case "Ksiega_Amosa":
                return "30";
            case "Ksiega_Abdiasza":
                return "31";
            case "Ksiega_Jonasza":
                return "32";
            case "Ksiega_Micheasza":
                return "33";
            case "Ksiega_Nahuma":
                return "34";
            case "Ksiega_Habakuka":
                return "35";
            case "Ksiega_Sofoniasza":
                return "36";
            case "Ksiega_Aggeusza":
                return "37";
            case "Ksiega_Zachariasza":
                return "38";
            case "Ksiega_Malachiasza":
                return "39";
            case "Ewangelia_Mateusza":
                return "40";
            case "Ewangelia_Marka":
                return "41";
            case "Ewangelia_Lukasza":
                return "42";
            case "Ewangelia_Jana":
                return "43";
            case "Dzieje_Apostolskie":
                return "44";
            case "List_Do_Rzymian":
                return "45";
            case "I_List_Do_Koryntian":
                return "46";
            case "II_List_Do_Koryntian":
                return "47";
            case "List_Do_Galacjan":
                return "48";
            case "List_Do_Efezjan":
                return "49";
            case "List_Do_Filipian":
                return "50";
            case "List_Do_Kolosan":
                return "51";
            case "I_List_Do_Tesaloniczan":
                return "52";
            case "II_List_Do_Tesaloniczan":
                return "53";
            case "I_List_Do_Tymoteusza":
                return "54";
            case "II_List_Do_Tymoteusza":
                return "55";
            case "List_Do_Tytusa":
                return "56";
            case "List_Do_Filemona":
                return "57";
            case "List_Do_Hebrajczykow":
                return "58";
            case "List_Jakuba":
                return "59";
            case "I_List_Piotra":
                return "60";
            case "II_List_Piotra":
                return "61";
            case "I_List_Jana":
                return "62";
            case "II_List_Jana":
                return "63";
            case "III_List_Jana":
                return "64";
            case "List_Judy":
                return "65";
            case "Ksiega_Objawienia":
                return "66";
        }
        return "0";
        }
    private void watek_zapis(String[] str)
        {
        class runnableZapisPliku implements Runnable
            {
            private String[] str;

            private runnableZapisPliku(String[] s)
                {
                str = s;
                }

            private void czyscDanezPliku(int start, int koniec)
                {
                for (int x = start; x < koniec; ++x) {
                    try {
                        this.writeToPosition(" ", x);
                    } catch (IOException var5) {
                        var5.printStackTrace();
                    }
                }
                }

            private void writeToPosition(String data, int pozycja) throws IOException
                {
                RandomAccessFile writer = new RandomAccessFile("kolumny.dat", "rw");
                writer.seek(pozycja);
                writer.writeUTF(data.trim());
                writer.close();
                }

            @Override
            public void run()
                {
                czyscDanezPliku(0, 50);
                try {
                    this.writeToPosition(str[0], 0);
                } catch (IOException var7) {
                    var7.printStackTrace();
                }
                czyscDanezPliku(50, 1050);
                try {
                    this.writeToPosition(str[1], 50);
                } catch (IOException var7) {
                    var7.printStackTrace();
                }
                czyscDanezPliku(1050, 2050);
                try {
                    this.writeToPosition(str[2], 1050);
                } catch (IOException var7) {
                    var7.printStackTrace();
                }
                }
            }
        Thread t = new Thread(new runnableZapisPliku(str));
        t.start();
        }
    private void watek_odczyt()
        {
        class runnableOdczytPliku implements Runnable
            {
            @Override
            public void run()
                {
                RandomAccessFile reader;
                try {
                    reader = new RandomAccessFile("kolumny.dat", "r");
                    reader.seek(0);
                    String result1 = reader.readUTF();
                    comboNazwaKsiegi.setSelectedItem(result1);
                    System.out.println(result1);
                    reader.seek(50);
                    String result2 = reader.readUTF();
                    obszarTekstowy1.setText(result2);
                    reader.seek(1050);
                    String result3 = reader.readUTF();
                    obszarTekstowy2.setText(result3);
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                }
            }
        Thread tt = new Thread(new runnableOdczytPliku());
        tt.start();
        }
    }