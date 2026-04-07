package edu.jaco.fin_stater.dictionaries.cats.transport.subcats;

import edu.jaco.fin_stater.transaction.Transaction;
import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
public enum BiletyDictionary {

    JAK_DOJADE ("jakdojade.pl"),
    MPK_KRAKOW ("mpk kraków"),
    KASOWNIK_ZTP ("kasownik ztp krakow"),
    INTERCITY ("intercity.pl");

    private String sentence;

    public static boolean matchTransactionRow(Transaction transaction) {
        return Arrays.stream(BiletyDictionary.values())
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
