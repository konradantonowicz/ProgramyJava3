package com.company;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
class ProgramGeneratorZapytanSQlite
    {
    private JTextField kolumnaRowid;
    private JTextField kolumnaRozdzialy;
    private JTextField kolumnaTemat;
    private JTextField ksiegadoDodania;
    private JTextArea obszarTekstowy1;
    private JTextArea obszarTekstowy2;
    private JTextArea obszarTekstowy3;
    private String Str_nazwaKsiegi;
    private String Str_kolumnaRozdzialy;
    private String Str_kolumnaRowid;
    private String Str_kolumnaTemat;
    private String Str_LeftJoin = "\n\tLEFT JOIN\n";
    private String Str_numerKsiegi;
    private ArrayList<String> listaZapytanie01 = new ArrayList<>();
    private ArrayList<String> listakolumnbazydanych = new ArrayList<>();

    private List<String> listaZapytanie02 = new ArrayList<>();
    private String[] przyciski = new String[]{"Moj_1_Mojzeszowa", "Moj_2_Mojzeszowa", "Moj_3_Mojzeszowa", "Moj_4_Mojzeszowa", "Moj_5_Mojzeszowa", "Ksiega_Jozuego", "Ksiega_Sedziow", "Ksiega_Rut", "Ksiega_I_Samuel", "Ksiega_II_Samuel", "I_Ksiega_Krolewska", "II_Ksiega_Krolewska", "I_Ksiega_Kronik", "II Księga Kronik", "Ksiega_Ezdrasza", "Ksiega_Nehemiasza", "Ksiega_Estery", "Ksiega_Hioba", "Psalmy", "Ksiega_Przyslow", "Ksiega_Kaznodziei", "Piesn_Nad_Piesniami", "Ksiega_Izajasza", "Ksiega_Jeremiasza", "Lamentacje", "Ksiega_Ezechiela", "Ksiega_Daniela", "Ksiega_Ozeasza", "Ksiega_Joela", "Ksiega_Amosa", "Ksiega_Abdiasza", "Ksiega_Jonasza", "Ksiega_Micheasza", "Ksiega_Nahuma", "Ksiega_Habakuka", "Ksiega_Sofoniasza", "Ksiega_Aggeusza", "Ksiega_Zachariasza", "Ksiega_Malachiasza", "Ewangelia_Mateusza", "Ewangelia_Marka", "Ewangelia_Lukasza", "Ewangelia_Jana", "Dzieje_Apostolskie", "List_Do_Rzymian", "I_List_Do_Koryntian", "II_List_Do_Koryntian", "List_Do_Galacjan", "List_Do_Efezjan", "List_Do_Filipian", "List_Do_Kolosan", "I_List_Do_Tesaloniczan", "II_List_Do_Tesaloniczan", "I_List_Do_Tymoteusza", "II_List_Do_Tymoteusza", "List_Do_Tytusa", "List_Do_Filemona", "List_Do_Hebrajczykow", "List_Jakuba", "I_List_Piotra", "II_List_Piotra", "I_List_Jana", "II_List_Jana", "III_List_Jana", "List_Judy", "Ksiega_Objawienia"};

    ProgramGeneratorZapytanSQlite() throws SQLException
        {
            listakolumnbazydanych.addAll(BazaDanych.polacz(przyciski[0]));
        }

    private JButton buttonprzyciskiKsiag(String Nazwa)
        {
        JButton ksiegi = new JButton(Nazwa);
        ksiegi.addActionListener((e) -> {
            String ksiegaDodac = e.getActionCommand() + "_Komentarze";
            this.ksiegadoDodania.setText(ksiegaDodac);
            String obszar1 = this.obszarTekstowy1.getText();
            String obszar2 = this.obszarTekstowy2.getText();
            obszar1 = obszar1 + "\n|| ifnull(" + ksiegaDodac + "._99_Komentarz, \"\")";
            obszar2 = obszar2 + this.Str_LeftJoin + ksiegaDodac + " ON " + ksiegaDodac + "._97_Komentarz_rowid =" + this.Str_kolumnaRowid + " AND \n" + ksiegaDodac + "._98_Dotyczy_Ksiegi = " + this.Str_numerKsiegi;
            this.obszarTekstowy1.setText(obszar1);
            this.obszarTekstowy2.setText(obszar2);
            this.obszarTekstowy3.setText(obszar1 + " " + obszar2);
        });
        return ksiegi;
        }

    private JButton buttonPokazCalosc()
        {
        JButton pokazCalosc = new JButton("Pokaż Całość");
        pokazCalosc.addActionListener((e) -> {
            JTextArea var10000 = this.obszarTekstowy3;
            String var10001 = this.obszarTekstowy1.getText();
            var10000.setText(var10001 + " " + this.obszarTekstowy2.getText());
        });
        return pokazCalosc;
        }

    private JButton buttonZaznaczKopiuj()
        {
        JButton dodajzapisz = new JButton("Zaznacz/Kopiuj");
        dodajzapisz.addActionListener((e) -> {
            this.obszarTekstowy3.selectAll();
            this.obszarTekstowy3.copy();
            dodajzapisz.transferFocusBackward();
            String[] ko = new String[]{Str_nazwaKsiegi, this.kolumnaRozdzialy.getText(), this.kolumnaRowid.getText(), this.kolumnaTemat.getText(), this.obszarTekstowy1.getText(), this.obszarTekstowy2.getText()};
            StringBuilder str;

            str = new StringBuilder(ko[4]);
            this.czyscDanezPliku(0, 2400);
            try {
                this.writeToPosition(str.toString(), 0);
            } catch (IOException var7) {
                var7.printStackTrace();
            }
            str = new StringBuilder(ko[5]);
            this.czyscDanezPliku(2400, 4400);
            try {
                this.writeToPosition(str.toString(), 2400);
            } catch (IOException var6) {
                var6.printStackTrace();
            }
        });
        return dodajzapisz;
        }

    private JLabel etykieta(String nazwa)
        {
        return new JLabel(nazwa);
        }



        private JComboBox comboBoxNazwaKsiegi()
            {

            DefaultComboBoxModel<String> modelBoxNazwaKsiegi = new DefaultComboBoxModel<>(przyciski);
         JComboBox comboNazwaKsiegi = new JComboBox<>(modelBoxNazwaKsiegi);
         comboNazwaKsiegi.addActionListener(e -> {
             JComboBox wybor = (JComboBox) e.getSource();
            Str_nazwaKsiegi = (String) wybor.getSelectedItem();

           wyswietl();
             listakolumnbazydanych.clear();
             try {
                 listakolumnbazydanych.addAll(BazaDanych.polacz(Str_nazwaKsiegi));
             } catch (SQLException ex) {
                 ex.printStackTrace();
             }
             kolumnaTemat.setText(listakolumnbazydanych.get(0));
             kolumnaRozdzialy.setText(listakolumnbazydanych.get(1));
             kolumnaRowid.setText(listakolumnbazydanych.get(2));
         });


            return comboNazwaKsiegi;
            }



    private JTextField poleTextowe_kolumnaRowid()
        {
        this.kolumnaRowid = new JTextField();
        this.kolumnaRowid.setText(listakolumnbazydanych.get(2));
        return this.kolumnaRowid;
        }

    private JTextField poleTextowe_kolumnaRozdzialy()
        {
        this.kolumnaRozdzialy = new JTextField();
        this.kolumnaRozdzialy.setText(listakolumnbazydanych.get(1));
        return this.kolumnaRozdzialy;
        }

    private JTextField poleTextowe_kolumnaTemat()
        {
        this.kolumnaTemat = new JTextField();
        this.kolumnaTemat.setText(listakolumnbazydanych.get(0));
        this.kolumnaTemat.addKeyListener(new KeyAdapter()
            {
            public void keyReleased(KeyEvent e)
                {
                super.keyReleased(e);

                }
            });
        return this.kolumnaTemat;
        }

    private JTextField poleTextowe_ksiegadoDodania()
        {
        this.ksiegadoDodania = new JTextField();
        return this.ksiegadoDodania;
        }

    private JScrollPane textArea1() throws IOException
        {
        this.obszarTekstowy1 = new JTextArea();
        this.obszarTekstowy1.setText(this.readFromPosition(0));
        return new JScrollPane(this.obszarTekstowy1);
        }

    private JScrollPane textArea2() throws IOException
        {

        this.obszarTekstowy2 = new JTextArea();
        this.obszarTekstowy2.setText(this.readFromPosition(2400));
        this.Str_kolumnaRowid = this.kolumnaRowid.getText();
        this.Str_kolumnaTemat = this.kolumnaTemat.getText() + "\n";
        Str_nazwaKsiegi = "Objects.requireNonNull(comboBoxNazwaKsiegi().getSelectedItem()).toString()";
        this.Str_numerKsiegi = this.ZamienNazweKsiegiNaNumerKsiegi(this.Str_nazwaKsiegi);
        this.Str_kolumnaRozdzialy = this.kolumnaRozdzialy.getText() + ",\n";
        this.ksiegadoDodania.getText();
        return new JScrollPane(this.obszarTekstowy2);
        }

    private JScrollPane textArea3()
        {
        this.obszarTekstowy3 = new JTextArea();
        return new JScrollPane(this.obszarTekstowy3);
        }

    private String readFromPosition(int position) throws IOException
        {
        RandomAccessFile reader = new RandomAccessFile("kolumny.dat", "r");
        reader.seek(position);
        String result = reader.readUTF();
        reader.close();
        return result;
        }

    private void writeToPosition(String data, int pozycja) throws IOException
        {
        RandomAccessFile writer = new RandomAccessFile("kolumny.dat", "rw");
        writer.seek(pozycja);
        writer.writeUTF(data.trim());
        writer.close();
        }

    private void wyswietl()
        {
        this.listaZapytanie01.clear();
        this.listaZapytanie02.clear();
        this.obszarTekstowy1.setText(" ");
        this.obszarTekstowy2.setText(" ");
        this.Str_kolumnaRowid = this.kolumnaRowid.getText();
        this.Str_kolumnaTemat = this.kolumnaTemat.getText() + "\n ";
        this.Str_numerKsiegi = this.ZamienNazweKsiegiNaNumerKsiegi(this.Str_nazwaKsiegi);
        this.Str_kolumnaRozdzialy = this.kolumnaRozdzialy.getText() + ",\n";
        this.ksiegadoDodania.getText();
        String str_select = "SELECT ";
        this.listaZapytanie01.add(str_select);
        this.listaZapytanie01.add(this.Str_nazwaKsiegi + "_Komentarze." + this.Str_kolumnaRozdzialy);
        String str_Verse = "\tBible.Verse,\n";
        this.listaZapytanie01.add(str_Verse);
        String str_scripture = "\tBible.Scripture,\n";
        this.listaZapytanie01.add(str_scripture);
        this.listaZapytanie01.add(this.Str_nazwaKsiegi + "_Komentarze." + this.Str_kolumnaTemat);
        String str_As = " AS ";
        this.listaZapytanie02.add(str_As + this.Str_kolumnaTemat);
        String str_From = "\tFROM Bible\n\t\tINNER JOIN\n";
        String str_Bible = "= Bible._rowid_";
        this.listaZapytanie02.add(str_From + this.Str_nazwaKsiegi + "_Komentarze ON " + this.Str_kolumnaRowid + str_Bible);
        Iterator<String> var1 = this.listaZapytanie01.iterator();
        String b;
        while (var1.hasNext()) {
            b = var1.next();
            this.obszarTekstowy1.append(b);
        }
        var1 = this.listaZapytanie02.iterator();
        while (var1.hasNext()) {
            b = var1.next();
            this.obszarTekstowy2.append(b);
        }
        JTextArea var10000 = this.obszarTekstowy3;
        String var10001 = this.obszarTekstowy1.getText();
        var10000.setText(var10001 + this.obszarTekstowy2.getText());
        }

    private String ZamienNazweKsiegiNaNumerKsiegi(String NazwaKsiegi)
        {
        byte var3 = -1;
        switch (NazwaKsiegi.hashCode()) {
            case -2108543350:
                if (NazwaKsiegi.equals("Ksiega_Aggeusza")) {
                    var3 = 36;
                }
                break;
            case -2073427870:
                if (NazwaKsiegi.equals("Ewangelia_Mateusza")) {
                    var3 = 39;
                }
                break;
            case -1992168849:
                if (NazwaKsiegi.equals("II_List_Piotra")) {
                    var3 = 60;
                }
                break;
            case -1924889212:
                if (NazwaKsiegi.equals("List_Do_Filemona")) {
                    var3 = 56;
                }
                break;
            case -1921111911:
                if (NazwaKsiegi.equals("List_Do_Filipian")) {
                    var3 = 49;
                }
                break;
            case -1895433286:
                if (NazwaKsiegi.equals("Psalmy")) {
                    var3 = 18;
                }
                break;
            case -1883868208:
                if (NazwaKsiegi.equals("Lamentacje")) {
                    var3 = 24;
                }
                break;
            case -1658762558:
                if (NazwaKsiegi.equals("Ewangelia_Jana")) {
                    var3 = 42;
                }
                break;
            case -1570676230:
                if (NazwaKsiegi.equals("Ksiega_Sofoniasza")) {
                    var3 = 35;
                }
                break;
            case -1536014924:
                if (NazwaKsiegi.equals("Ksiega_Izajasza")) {
                    var3 = 22;
                }
                break;
            case -1243826201:
                if (NazwaKsiegi.equals("List_Do_Rzymian")) {
                    var3 = 44;
                }
                break;
            case -1204565336:
                if (NazwaKsiegi.equals("I_Ksiega_Krolewska")) {
                    var3 = 10;
                }
                break;
            case -1149863990:
                if (NazwaKsiegi.equals("I_List_Do_Tesaloniczan")) {
                    var3 = 51;
                }
                break;
            case -1148776516:
                if (NazwaKsiegi.equals("Ksiega_Micheasza")) {
                    var3 = 32;
                }
                break;
            case -830298765:
                if (NazwaKsiegi.equals("Ksiega_Kaznodziei")) {
                    var3 = 20;
                }
                break;
            case -816511254:
                if (NazwaKsiegi.equals("Ksiega_Nehemiasza")) {
                    var3 = 15;
                }
                break;
            case -768820218:
                if (NazwaKsiegi.equals("I_List_Do_Tymoteusza")) {
                    var3 = 53;
                }
                break;
            case -527068682:
                if (NazwaKsiegi.equals("II Księga Kronik")) {
                    var3 = 13;
                }
                break;
            case -487137362:
                if (NazwaKsiegi.equals("List_Do_Efezjan")) {
                    var3 = 48;
                }
                break;
            case -476370093:
                if (NazwaKsiegi.equals("II_List_Do_Tesaloniczan")) {
                    var3 = 52;
                }
                break;
            case -376766169:
                if (NazwaKsiegi.equals("Ksiega_Estery")) {
                    var3 = 16;
                }
                break;
            case -251690685:
                if (NazwaKsiegi.equals("Ewangelia_Lukasza")) {
                    var3 = 41;
                }
                break;
            case -203112511:
                if (NazwaKsiegi.equals("Ksiega_Daniela")) {
                    var3 = 26;
                }
                break;
            case -137571564:
                if (NazwaKsiegi.equals("Ksiega_I_Samuel")) {
                    var3 = 8;
                }
                break;
            case -136069483:
                if (NazwaKsiegi.equals("Ksiega_Nahuma")) {
                    var3 = 33;
                }
                break;
            case -1228541:
                if (NazwaKsiegi.equals("Ksiega_Przyslow")) {
                    var3 = 19;
                }
                break;
            case 120743068:
                if (NazwaKsiegi.equals("Ewangelia_Marka")) {
                    var3 = 40;
                }
                break;
            case 175953949:
                if (NazwaKsiegi.equals("Ksiega_Ezdrasza")) {
                    var3 = 14;
                }
                break;
            case 217776932:
                if (NazwaKsiegi.equals("Ksiega_Jeremiasza")) {
                    var3 = 23;
                }
                break;
            case 324003321:
                if (NazwaKsiegi.equals("Ksiega_Habakuka")) {
                    var3 = 34;
                }
                break;
            case 330332616:
                if (NazwaKsiegi.equals("Ksiega_Sedziow")) {
                    var3 = 6;
                }
                break;
            case 336382622:
                if (NazwaKsiegi.equals("Ksiega_Objawienia")) {
                    var3 = 65;
                }
                break;
            case 431712391:
                if (NazwaKsiegi.equals("List_Do_Tytusa")) {
                    var3 = 55;
                }
                break;
            case 553872408:
                if (NazwaKsiegi.equals("I_List_Piotra")) {
                    var3 = 59;
                }
                break;
            case 740629380:
                if (NazwaKsiegi.equals("Ksiega_Zachariasza")) {
                    var3 = 37;
                }
                break;
            case 778650145:
                if (NazwaKsiegi.equals("List_Judy")) {
                    var3 = 64;
                }
                break;
            case 806725382:
                if (NazwaKsiegi.equals("List_Do_Kolosan")) {
                    var3 = 50;
                }
                break;
            case 940217249:
                if (NazwaKsiegi.equals("List_Jakuba")) {
                    var3 = 58;
                }
                break;
            case 1089852484:
                if (NazwaKsiegi.equals("Moj_1_Mojzeszowa")) {
                    var3 = 0;
                }
                break;
            case 1218935203:
                if (NazwaKsiegi.equals("Moj_2_Mojzeszowa")) {
                    var3 = 1;
                }
                break;
            case 1227525953:
                if (NazwaKsiegi.equals("Ksiega_Jonasza")) {
                    var3 = 31;
                }
                break;
            case 1239189996:
                if (NazwaKsiegi.equals("Ksiega_Jozuego")) {
                    var3 = 5;
                }
                break;
            case 1256254389:
                if (NazwaKsiegi.equals("I_List_Jana")) {
                    var3 = 61;
                }
                break;
            case 1303829078:
                if (NazwaKsiegi.equals("List_Do_Galacjan")) {
                    var3 = 47;
                }
                break;
            case 1348017922:
                if (NazwaKsiegi.equals("Moj_3_Mojzeszowa")) {
                    var3 = 2;
                }
                break;
            case 1369442352:
                if (NazwaKsiegi.equals("Ksiega_Amosa")) {
                    var3 = 29;
                }
                break;
            case 1375787308:
                if (NazwaKsiegi.equals("Ksiega_Hioba")) {
                    var3 = 17;
                }
                break;
            case 1377803796:
                if (NazwaKsiegi.equals("Ksiega_Joela")) {
                    var3 = 28;
                }
                break;
            case 1417917683:
                if (NazwaKsiegi.equals("Ksiega_Malachiasza")) {
                    var3 = 38;
                }
                break;
            case 1477100641:
                if (NazwaKsiegi.equals("Moj_4_Mojzeszowa")) {
                    var3 = 3;
                }
                break;
            case 1538870064:
                if (NazwaKsiegi.equals("Ksiega_Rut")) {
                    var3 = 7;
                }
                break;
            case 1573497521:
                if (NazwaKsiegi.equals("II_Ksiega_Krolewska")) {
                    var3 = 11;
                }
                break;
            case 1606183360:
                if (NazwaKsiegi.equals("Moj_5_Mojzeszowa")) {
                    var3 = 4;
                }
                break;
            case 1623818533:
                if (NazwaKsiegi.equals("Ksiega_Ezechiela")) {
                    var3 = 25;
                }
                break;
            case 1676686034:
                if (NazwaKsiegi.equals("Ksiega_Ozeasza")) {
                    var3 = 27;
                }
                break;
            case 1687615155:
                if (NazwaKsiegi.equals("I_Ksiega_Kronik")) {
                    var3 = 12;
                }
                break;
            case 1693463962:
                if (NazwaKsiegi.equals("List_Do_Hebrajczykow")) {
                    var3 = 57;
                }
                break;
            case 1731774396:
                if (NazwaKsiegi.equals("I_List_Do_Koryntian")) {
                    var3 = 45;
                }
                break;
            case 1774894543:
                if (NazwaKsiegi.equals("II_List_Do_Tymoteusza")) {
                    var3 = 54;
                }
                break;
            case 1952377043:
                if (NazwaKsiegi.equals("II_List_Do_Koryntian")) {
                    var3 = 46;
                }
                break;
            case 1961083648:
                if (NazwaKsiegi.equals("Ksiega_Abdiasza")) {
                    var3 = 30;
                }
                break;
            case 1991123259:
                if (NazwaKsiegi.equals("Ksiega_II_Samuel")) {
                    var3 = 9;
                }
                break;
            case 2021061696:
                if (NazwaKsiegi.equals("Dzieje_Apostolskie")) {
                    var3 = 43;
                }
                break;
            case 2065141429:
                if (NazwaKsiegi.equals("Piesn_Nad_Piesniami")) {
                    var3 = 21;
                }
                break;
            case 2089358284:
                if (NazwaKsiegi.equals("II_List_Jana")) {
                    var3 = 62;
                }
                break;
            case 2145775253:
                if (NazwaKsiegi.equals("III_List_Jana")) {
                    var3 = 63;
                }
        }
        switch (var3) {
            case 0:
                return "1";
            case 1:
                return "2";
            case 2:
                return "3";
            case 3:
                return "4";
            case 4:
                return "5";
            case 5:
                return "6";
            case 6:
                return "7";
            case 7:
                return "8";
            case 8:
                return "9";
            case 9:
                return "10";
            case 10:
                return "11";
            case 11:
                return "12";
            case 12:
                return "13";
            case 13:
                return "14";
            case 14:
                return "15";
            case 15:
                return "16";
            case 16:
                return "17";
            case 17:
                return "18";
            case 18:
                return "19";
            case 19:
                return "20";
            case 20:
                return "21";
            case 21:
                return "22";
            case 22:
                return "23";
            case 23:
                return "24";
            case 24:
                return "25";
            case 25:
                return "26";
            case 26:
                return "27";
            case 27:
                return "28";
            case 28:
                return "29";
            case 29:
                return "30";
            case 30:
                return "31";
            case 31:
                return "32";
            case 32:
                return "33";
            case 33:
                return "34";
            case 34:
                return "35";
            case 35:
                return "36";
            case 36:
                return "37";
            case 37:
                return "38";
            case 38:
                return "39";
            case 39:
                return "40";
            case 40:
                return "41";
            case 41:
                return "42";
            case 42:
                return "43";
            case 43:
                return "44";
            case 44:
                return "45";
            case 45:
                return "46";
            case 46:
                return "47";
            case 47:
                return "48";
            case 48:
                return "49";
            case 49:
                return "50";
            case 50:
                return "51";
            case 51:
                return "52";
            case 52:
                return "53";
            case 53:
                return "54";
            case 54:
                return "55";
            case 55:
                return "56";
            case 56:
                return "57";
            case 57:
                return "58";
            case 58:
                return "59";
            case 59:
                return "60";
            case 60:
                return "61";
            case 61:
                return "62";
            case 62:
                return "63";
            case 63:
                return "64";
            case 64:
                return "65";
            case 65:
                return "66";
            default:
                return "0";
        }
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

    JPanel generatorSQlite_panel_north()
        {
        JPanel panel_1 = new JPanel();
        panel_1.setLayout(new GridLayout(13, 5));
        for (int x = 0; x < 66; ++x) {
            panel_1.add(this.buttonprzyciskiKsiag(this.przyciski[x]));
        }
        return panel_1;
        }

    JPanel generatorSQlite_panel_center() throws IOException
        {
        JPanel panel_2 = new JPanel();
        panel_2.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = 2;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0D;
        c.insets = new Insets(10, 0, 0, 50);
        panel_2.add(this.etykieta("Nazwa Ksiegi"), c);
        c.fill = 2;
        c.gridx = 1;
        c.gridy = 0;

         panel_2.add(this.etykieta("Kolumna Rowid"), c);
        c.fill = 2;
        c.gridx = 2;
        c.gridy = 0;

        panel_2.add(this.etykieta("Kolumna  Rozdzialy"), c);

        c.fill = 2;
        c.gridx = 3;
        c.gridy = 0;
        panel_2.add(this.etykieta("Kolumna Temat"), c);
        c.fill = 2;
        c.gridx = 4;
        c.gridy = 0;
        panel_2.add(this.etykieta("Księga do Dodania"), c);
        c.insets = new Insets(0, 0, 0, 0);
        c.fill = 2;
        c.gridx = 0;
        c.gridy = 1;
        panel_2.add(comboBoxNazwaKsiegi(), c);
        c.fill = 2;
        c.gridx = 1;
        c.gridy = 1;
        panel_2.add(this.poleTextowe_kolumnaRowid(), c);
        c.fill = 2;
        c.gridx = 2;
        c.gridy = 1;
        panel_2.add(this.poleTextowe_kolumnaRozdzialy(), c);
        c.fill = 2;
        c.gridx = 3;
        c.gridy = 1;
        panel_2.add(this.poleTextowe_kolumnaTemat(), c);
        c.fill = 2;
        c.gridx = 4;
        c.gridy = 1;
        panel_2.add(this.poleTextowe_ksiegadoDodania(), c);
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
    }
