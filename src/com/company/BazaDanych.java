package com.company;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
class BazaDanych
    {



    static List<String> polacz(String nazwaKsiegi,String sciezka) throws SQLException
        {
        Connection polaczenie = null;
        Statement stat;
        try {

            Class.forName("org.sqlite.JDBC");
            polaczenie = DriverManager.getConnection(sciezka);
            System.out.println("Połączyłem się z bazą ");
        } catch (Exception e) {
            System.err.println("Błąd w połączeniu z bazą: \n" + e.getMessage());

        }
        assert polaczenie != null;
        stat = polaczenie.createStatement();

        String szukajSQL = "SELECT * FROM "+nazwaKsiegi;

        ResultSet wynik = stat.executeQuery(szukajSQL);
        ResultSetMetaData rsmd = wynik.getMetaData();
        int columnCount = rsmd.getColumnCount();
        java.util.List<String> lista = new ArrayList<>();
        for (int i = 1; i <= columnCount; i++ ) {
            lista.add(rsmd.getColumnName(i));

        }


        Iterator<String> reversedStream = new LinkedList<>(lista)
                .descendingIterator();

        List<String> listReversedOrder = StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(reversedStream,
                        Spliterator.ORDERED), false).limit(3).collect(
                Collectors.toList());

       // System.out.println(listReversedOrder);


        wynik.close();
        stat.close();
        polaczenie.close();
        return listReversedOrder;
        }


    }
