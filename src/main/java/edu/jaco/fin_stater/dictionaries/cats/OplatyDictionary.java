package edu.jaco.fin_stater.dictionaries.cats;

import edu.jaco.fin_stater.transaction.Transaction;
import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
public enum OplatyDictionary {
    PLAY ("24.play.pl"),
    TAURON ("tauron.pl"),
    NETIA ("netia.pl"),
    NETIA_2 ("netiaonline.pl"),
    DOM_KRAK ("mmsoft.com.pl"),
    PROSPERITO_1 ("mieszczanin.pl"),
    PROSPERITO_2 ("prosperito"),
    ISTS ("ists sp. z o.o."),
    UBEZPIECZENIE_MIESZKANIA ("tuir warta"),
    TAX ("urząd skarbowy"),
    TAX_2 ("podatek od nieruchomosci"),
    TAX_3 ("podatek od nieruchomości"),
    KREDYT_M2 ("spłata kredytu"),
    PBKM ("pbkm.pl"),
    PARKING_1 ("zdmk krakow"),
    PARKING_2 ("property center parking"),
    PARKING_3 ("parkomat 3078"),
    MICROSOFT ("microsoft");

    private String sentence;

    public static boolean matchTransactionRow(Transaction transaction) {
        return Arrays.stream(OplatyDictionary.values())
                .anyMatch(dictIt ->
                     transaction.getAdditional_info_2()
                        .toLowerCase()
                        .contains(dictIt.sentence) ||
                     transaction.getAdditional_info()
                        .toLowerCase()
                        .contains(dictIt.sentence) ||
                     transaction.getReceiver()
                        .toLowerCase()
                        .contains(dictIt.sentence) ||
                     transaction.getType()
                        .toLowerCase()
                        .contains(dictIt.sentence) ||
                     transaction.getDescription()
                        .toLowerCase()
                        .contains((dictIt.sentence))
                );
    }
}
