package edu.jaco.fin_stater.dictionaries.cats.body.subcats;

import edu.jaco.fin_stater.transaction.Transaction;
import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
public enum HigienaDictionary {

    ANGELA ("salon fryzjerski angel"),
    ROSSMANN ("rossmann"),
    PONGO ("pongo");

    private String sentence;

    public static boolean matchTransactionRow(Transaction transaction) {
        return Arrays.stream(HigienaDictionary.values())
                .anyMatch(dictIt ->
                     transaction.getDescription()
                        .toLowerCase()
                        .contains(dictIt.sentence) ||
                     transaction.getReceiver()
                        .toLowerCase()
                        .contains(dictIt.sentence) ||
                     transaction.getAdditional_info()
                        .toLowerCase()
                        .contains(dictIt.sentence)
                );
    }
}
