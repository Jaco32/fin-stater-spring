package edu.jaco.fin_stater.dictionaries.freqs;

import edu.jaco.fin_stater.transaction.Transaction;

import java.util.Arrays;

public enum MonthlyDictionary {
    MISIOWE ("misiowe przedszkole"),
    KACIK_MALUSZKA ("kącik maluszka"),
    TAURON ("tauron.pl"),
    PROSPERITO_1 ("mieszczanin.pl"),
    PROSPERITO_2 ("prosperito"),
    NETIA ("netia.pl");

    private String sentence;

    MonthlyDictionary(String sentence) { this.sentence = sentence; }

    public static boolean matchTransactionRow(Transaction transaction) {
        return Arrays.stream(MonthlyDictionary.values())
                .anyMatch(dictIt -> {
                    return  transaction.getAdditional_info()
                                    .toLowerCase()
                                    .contains(dictIt.sentence) ||
                            transaction.getAdditional_info_2()
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
                                    .contains(dictIt.sentence);
                });
    }
}
