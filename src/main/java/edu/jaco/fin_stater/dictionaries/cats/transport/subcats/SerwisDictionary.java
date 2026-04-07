package edu.jaco.fin_stater.dictionaries.cats.transport.subcats;

import edu.jaco.fin_stater.transaction.Transaction;
import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
public enum SerwisDictionary {

    DYNAMICA ("dynamica sp. z o.o."),
    PGD ("grupa pgd sp z o.o."),
    UBEZPIECZENIE_LEONA ("generali t.u. s.a."),
    UBEZPIECZENIE_TOURANA ("ubezpieczenia.vwsu.pl"),
    GREEN_SHINE ("green shine"),
    VW ("vw bank polska"),
    VW_2 ("volkswagen financial services"),
    VW_3 ("cichy - zasada"),
    VW_4 ("vw gcz"),
    OTOMOTO ("www.otomoto.pl");

    private String sentence;

    public static boolean matchTransactionRow(Transaction transaction) {
        return Arrays.stream(SerwisDictionary.values())
                .anyMatch(dictIt ->
                        transaction.getAdditional_info()
                                .toLowerCase()
                                .contains(dictIt.sentence) ||
                        transaction.getAdditional_info_2()
                                .toLowerCase()
                                .contains(dictIt.sentence) ||
                        transaction.getReceiver()
                                .toLowerCase()
                                .contains(dictIt.sentence) ||
                        transaction.getDescription()
                                .toLowerCase()
                                .contains(dictIt.sentence)
                );
    }
}
