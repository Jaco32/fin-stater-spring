package edu.jaco.fin_stater.dictionaries.cats.body;

import edu.jaco.fin_stater.transaction.Transaction;
import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
public enum CialoDictionary {

    // Higiena
    ANGELA ("salon fryzjerski angel"),
    ROSSMANN ("rossmann"),
    PONGO ("pongo"),

    // Zdrowie
    HIENA ("apteka hygieia"),
    ZIKO ("ziko"),
    DOZ ("doz apteka"),
    DR_MAX ("apteka dr. max"),
    DENTIMA ("dentima"),
    APTEKA_GEMINI ("apteka gemini"),
    APTEKA_CHMIELNO ("apteka kartuska chmielno"),
    APTEKA_ZIELONKI ("apteka pod zlotym tygr"),
    APTEKA_SLONECZNA ("apteka sloneczna"),
    STUDIO_FI ("studio fi"),
    LUXMED ("luxmed"),
    LUXMED_2 ("lux med"),
    KOALA ("centrum rehabilitacji koa"),
    PODIATRICA ("podiatrica"),
    CENTRUM_MEDYCZNE_EVITA ("centrum medyczne evita"),
    CENTRUM_MEDYCZNE_DIAGNOZA ("cm diagnoza"),
    CENTRUM_MEDYCZNE_UNIMED ("centrum medyczne unimed"),
    MED_FILE_PL ("app.medfile.pl"),
    DAFI ("dafi.pl"),
    AROMAMA ("aromama.pl"),

    // Beauty
    SO_BEAUTY ("so beauty"),
    FIU_FIU ("fiu fiu");

    private String sentence;

    public static boolean matchTransactionRow(Transaction transaction) {
        return Arrays.stream(CialoDictionary.values())
                .anyMatch(dictIt ->
                        transaction.getDescription()
                            .toLowerCase()
                            .contains(dictIt.sentence) ||
                        transaction.getReceiver()
                            .toLowerCase()
                            .contains(dictIt.sentence) ||
                        transaction.getAdditional_info()
                            .toLowerCase()
                            .contains(dictIt.sentence) ||
                        transaction.getAdditional_info_2()
                            .toLowerCase()
                            .contains(dictIt.sentence)
                );
    }
}
