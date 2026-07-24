package edu.jaco.fin_stater.dictionaries.cats.food.subcats;

import edu.jaco.fin_stater.transaction.Transaction;
import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
public enum SlodkosciDictionary {

    LODOVE_TUTKI ("lodziarnie firmowe"),
    GOOD_LOOD ("good lood"),
    GOOD_LOOD_2 ("goodlood.com"),
    CZULY_LODZIARZ ("czuly lodziarz"),
    LODY_U_MISKOW ("lody u miskow"),
    LODOWA_CHATKA ("lodowa chatka zielonki"),
    NAKIELNY ("nakielny"),
    CUKIERNIA_ROZA ("cukiernia roza"),
    GRYCAN("grycan");

    private String sentence;

    public static boolean matchTransactionRow(Transaction transaction) {
        return Arrays.stream(SlodkosciDictionary.values())
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
