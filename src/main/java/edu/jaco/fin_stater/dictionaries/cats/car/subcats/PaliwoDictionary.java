package edu.jaco.fin_stater.dictionaries.cats.car.subcats;

import edu.jaco.fin_stater.transaction.Transaction;
import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
public enum PaliwoDictionary {

    BP ("bp-"),
    LOTOS ("lotos"),
    ORLEN ("orlen"),
    ORLEN_2 ("stacja paliw nr 7578"),
    CIRCLE_K ("circle k"),
    STACJA_LIBIAZ ("stacja paliw nr 7011"),
    A4_BRAMKI ("balice manual");

    private String sentence;

    public static boolean matchTransactionRow(Transaction transaction) {
        return Arrays.stream(PaliwoDictionary.values())
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
