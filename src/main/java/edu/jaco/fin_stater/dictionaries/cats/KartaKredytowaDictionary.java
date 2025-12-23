package edu.jaco.fin_stater.dictionaries.cats;

import edu.jaco.fin_stater.transaction.Transaction;
import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
public enum KartaKredytowaDictionary {

    SPLATA ("spłata ostatniego zestawienia karty kredytowej"),
    AUTO_SPLATA ("autospłata karty kredytowej"),
    MANUAL_SPLATA ("spłata aktualnego zadłużenia karty kredytowej");

    private String sentence;

    public static boolean matchTransactionRow(Transaction transaction) {
        return Arrays.stream(KartaKredytowaDictionary.values())
                .anyMatch(dictIt ->
                     transaction.getDescription()
                        .toLowerCase()
                        .contains(dictIt.sentence) ||
                     transaction.getType()
                        .toLowerCase()
                        .contains(dictIt.sentence)
                );
    }
}
