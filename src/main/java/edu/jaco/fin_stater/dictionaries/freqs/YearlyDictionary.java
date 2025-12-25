package edu.jaco.fin_stater.dictionaries.freqs;

import edu.jaco.fin_stater.transaction.Transaction;

import java.util.Arrays;

public enum YearlyDictionary {
    PBKM ("pbkm.pl"),
    TAX ("urząd skarbowy"),
    TAX_2 ("podatek od nieruchomosci"),
    UBEZPIECZENIE_MIESZKANIA ("tuir warta");

    private String sentence;

    YearlyDictionary(String sentence) { this.sentence = sentence; }

    public static boolean matchTransactionRow(Transaction transaction) {
        return Arrays.stream(YearlyDictionary.values())
                .anyMatch(dictIt -> {
                    return transaction.getAdditional_info_2()
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
                                    .contains((dictIt.sentence));
                });
    }
}
