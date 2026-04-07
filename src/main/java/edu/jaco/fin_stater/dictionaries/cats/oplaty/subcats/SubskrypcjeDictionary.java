package edu.jaco.fin_stater.dictionaries.cats.oplaty.subcats;

import edu.jaco.fin_stater.transaction.Transaction;
import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
public enum SubskrypcjeDictionary {
    MICROSOFT ("microsoft");

    private String sentence;

    public static boolean matchTransactionRow(Transaction transaction) {
        return Arrays.stream(SubskrypcjeDictionary.values())
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
