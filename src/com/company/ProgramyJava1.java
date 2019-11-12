package com.company;
import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class ProgramyJava1
    {
    private JFrame f;

    private ProgramyJava1()
        {
    this.OknoGlowne();
    }

    public static void main(String[] args)
        {
        new ProgramyJava1();

        }

    private void OknoGlowne()
        {
    this.f = new JFrame();
    ProgramOdnosniki programOdnosniki = new ProgramOdnosniki();



    this.f.setLayout(new BorderLayout());
    this.f.setSize(800, 800);
    this.f.setTitle("Wszystkie Programy");
    this.f.add(programOdnosniki.Odnosniki_panel_01(), "North");
    this.f.add(programOdnosniki.Odnosniki_panel_02(), "Center");
    this.f.add(this.Panel_South(), "South");
    this.f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.f.setVisible(true);
    }






    private JButton ProgramDokumentacja() {
    JButton programDokumenacja = new JButton("Program Dokumentacja");
    programDokumenacja.addActionListener((e) -> {
        this.usun_komponenty();
        ProgramDokumentacja programDokumentacja = new ProgramDokumentacja();
        this.f.add(programDokumentacja.Dokumentacja_panel_Center(), "Center");
        this.f.add(this.Panel_South(), "South");
        this.f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.f.setVisible(true);
    });
    return programDokumenacja;
    }

    private JButton ProgramYoutube() {
    JButton programYoutube = new JButton("Youtube-dl");
    programYoutube.addActionListener((e) -> {
        this.usun_komponenty();
        ProgramYouTubeDownload programYouTubeDownload = new ProgramYouTubeDownload();
        this.f.add(programYouTubeDownload.youtube_dl_Panel_NORTH(), "North");
        this.f.add(programYouTubeDownload.youtube_dl_Panel_Center(), "Center");
        this.f.add(this.Panel_South(), "South");
        this.f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.f.setVisible(true);
    });
    return programYoutube;
    }

    private JButton ProgramOdnosniki() {
    JButton programOdnosniki = new JButton("Odnośniki");
    programOdnosniki.addActionListener((e) -> {
        this.usun_komponenty();
        ProgramOdnosniki programOdnosniki1 = new ProgramOdnosniki();
        this.f.add(programOdnosniki1.Odnosniki_panel_01(), "North");
        this.f.add(programOdnosniki1.Odnosniki_panel_02(), "Center");
        this.f.add(this.Panel_South(), "South");
        this.f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.f.setVisible(true);
    });
    return programOdnosniki;
    }

    private JButton ProgramGeneratorSqlite() {
    JButton programGenerator = new JButton("GeneratorSQlite");
    programGenerator.addActionListener((e) -> {
        this.usun_komponenty();
        ProgramGeneratorZapytanSQlite_versja2 programGeneratorZapytanSQlite;
        programGeneratorZapytanSQlite = new ProgramGeneratorZapytanSQlite_versja2();
        this.f.add(Objects.requireNonNull(programGeneratorZapytanSQlite).generatorSQlite_panel_north2(), "North");
        this.f.add(programGeneratorZapytanSQlite.generatorSQlite_panel_center2(), "Center");
        this.f.add(this.Panel_South(), "South");
        this.f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.f.setVisible(true);
    });
    return programGenerator;
    }

    private JPanel Panel_South() {
    JPanel panel3 = new JPanel();
    panel3.add(this.ProgramDokumentacja());
    panel3.add(this.ProgramYoutube());
    panel3.add(this.ProgramOdnosniki());
    panel3.add(this.ProgramGeneratorSqlite());
    return panel3;
    }

    private void usun_komponenty() {
    this.f.getContentPane().removeAll();
    this.f.getContentPane().repaint();
    }
    }