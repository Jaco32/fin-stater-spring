package edu.jaco.fin_stater.dictionaries.cats;

import edu.jaco.fin_stater.transaction.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
public enum DzieciDictionary {
    SMYK ("smyk"),
    MIKI ("salon dzieciecy miki"),
    PEPCO ("pepco"),
    HEALTHY_KIDS_1 ("livekid.com"),
    HEALTHY_KIDS_2 ("healthy kids"),
    MISIOWE ("misiowe przedszkole"),
    KACIK_MALUSZKA ("kącik maluszka"),
    KACIK_MALUSZKA_2 ("k?cik maluszka"),
    KACIK_MALUSZKA_3 ("music math"),
    KACIK_MALUSZKA_4 ("musicmath.pl"),
    BEBETTO ("aroshop.pl"),
    MAMOLOGIA ("mamologia.pl"),
    SCANMED ("scanmed"),
    ROWER_HANI ("za różową strzałę"),
    VINTED ("vinted"),
    ENERGYLANDIA ("energylandia"),
    ESTROMED ("katarzyna lenart"),
    DIAGNOSTYKA ("diagnostyka"),
    BAJKOWE_PODDASZE ("bajkowepoddasze"),
    GOJUMP ("gojump"),
    PKOLE_LIVEKID_ALA ("wegorkiewicz alic"),
    PKOLE_LIVEKID_ANTOSIA ("wegorkiewicz ant"),
    ZLOBEK_ANIELKI_JEDZENIE ("gotowalnia.pl"),
    CENTRUM_FOTELIKOW ("centrum fotelikow");

    @Getter
    private String sentence;

    public static boolean matchTransactionRow(Transaction transaction) {
        return Arrays.stream(DzieciDictionary.values())
                .anyMatch(dictIt ->
                    transaction.getAdditional_info()
                        .toLowerCase()
                        .contains(dictIt.sentence) ||
                    transaction.getAdditional_info_2()
                        .toLowerCase()
                        .contains(dictIt.sentence) ||
                    transaction.getReceiver()
                        .toLowerCase()
                        .contains(dictIt.sentence) ||
                    transaction.getDescription()
                        .toLowerCase()
                        .contains(dictIt.sentence)
                );
    }
}
