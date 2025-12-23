package edu.jaco.fin_stater.dictionaries.cats;

import edu.jaco.fin_stater.transaction.Transaction;
import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
public enum BankomatDictionary {

    BP_KRK_OPOLSKA_60 ("ul.opolska 60"),
    BY_TYPE_1 ("wypłata w bankomacie"),
    BY_TYPE_2 ("wypłata z bankomatu");

    private String sentence;

    public static boolean matchTransactionRow(Transaction transaction) {
        return Arrays.stream(BankomatDictionary.values())
                .anyMatch(dictIt ->
                    transaction.getAdditional_info()
                        .toLowerCase()
                        .contains(dictIt.sentence) ||
                    transaction.getAdditional_info_2()
                        .toLowerCase()
                        .contains(dictIt.sentence) ||
                    transaction.getType()
                        .toLowerCase()
                        .contains(dictIt.sentence)
                );
    }
}
