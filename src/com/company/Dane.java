//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

class Dane {
Dane() {
}

public String[] filtrujSciezkePlikow(String sciezkaKatalogu) {
return lista_katalogow_Jar().stream().filter((x) -> x.startsWith(sciezkaKatalogu)).filter((x) -> x.endsWith("txt")).map((x) -> x.substring(x.indexOf("/") + 1)).map((x) -> x.substring(x.indexOf("/") + 1)).toArray(String[]::new);
}

List<String> lista_katalogow_Jar() {
ArrayList<String> listaKatalogow = new ArrayList<>();

try {
    JarFile jarFile = new JarFile("ProgramyJava1.jar");
    Enumeration<JarEntry> enumOfJar = jarFile.entries();

    while(enumOfJar.hasMoreElements()) {
        listaKatalogow.add((enumOfJar.nextElement()).toString());
    }
} catch (IOException var4) {
    System.out.println("IOException: " + var4);
}

return listaKatalogow;
}

String odczyt(String katalog) throws IOException {
JarFile jarFile = new JarFile("ProgramyJava1.jar");
JarEntry entry = jarFile.getJarEntry(katalog);
InputStream input = jarFile.getInputStream(entry);
StringBuilder linie = new StringBuilder();
InputStreamReader isr = new InputStreamReader(input);
BufferedReader reader = new BufferedReader(isr);

String line;
while((line = reader.readLine()) != null) {
    linie.append(line).append("\n");
}

reader.close();
jarFile.close();
return linie.toString();
}
}
