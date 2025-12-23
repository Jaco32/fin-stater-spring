package edu.jaco.fin_stater.dictionaries.cats;

import edu.jaco.fin_stater.transaction.Transaction;
import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
public enum RowerDictionary {

    TOMAR ("tomar sport");

    private String sentence;

    public static boolean matchTransactionRow(Transaction transaction) {
        return Arrays.stream(RowerDictionary.values())
                .anyMatch(dictIt ->
                     transaction.getAdditional_info()
                        .toLowerCase()
                        .contains(dictIt.sentence) ||
                     transaction.getDescription()
                        .toLowerCase()
                        .contains(dictIt.sentence)
                );
    }
}
