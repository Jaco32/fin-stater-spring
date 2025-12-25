package edu.jaco.fin_stater.dictionaries.cats.food.subcats;

import edu.jaco.fin_stater.transaction.Transaction;
import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
public enum MarketyDictionary {

    BIEDRONKA ("biedronka"),
    LEWIATAN ("koral sp.j."),
    LEWIATAN_WIARUSA ("lokietek"),
    LEWIATAN_WIARUSA_2 ("wiarusa 15"),
    ZABKA ("zabka"),
    AUCHAN ("auchan"),
    LIDL ("lidl"),
    CARREFOUR ("carrefour"),
    GLOVO ("glovoapp.com");

    private String sentence;

    public static boolean matchTransactionRow(Transaction transaction) {
        return Arrays.stream(MarketyDictionary.values())
                .anyMatch(dictIt ->
                    transaction.getAdditional_info()
                            .toLowerCase()
                            .contains(dictIt.sentence) ||
                    transaction.getAdditional_info_2()
                            .toLowerCase()
                            .contains(dictIt.sentence) ||
                    transaction.getDescription()
                            .toLowerCase()
                            .contains((dictIt.sentence))
                );
    }
}
