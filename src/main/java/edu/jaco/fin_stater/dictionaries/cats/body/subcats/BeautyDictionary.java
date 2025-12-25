package edu.jaco.fin_stater.dictionaries.cats.body.subcats;

import edu.jaco.fin_stater.transaction.Transaction;
import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
public enum BeautyDictionary {

    SO_BEAUTY ("so beauty"),
    FIU_FIU ("fiu fiu");

    private String sentence;

    public static boolean matchTransactionRow(Transaction transaction) {
        return Arrays.stream(BeautyDictionary.values())
                .anyMatch(dictIt ->
                        transaction.getAdditional_info()
                                .toLowerCase()
                                .contains(dictIt.sentence)
                );
    }
}
