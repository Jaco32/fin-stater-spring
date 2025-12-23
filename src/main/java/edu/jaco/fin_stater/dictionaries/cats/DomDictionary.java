package edu.jaco.fin_stater.dictionaries.cats;

import edu.jaco.fin_stater.transaction.Transaction;
import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
public enum DomDictionary {

    IKEA ("ikea"),
    CASTORAMA ("castorama"),
    MEBLE_MAGNAT ("sklep503774.shoparena.pl"),
    LEROY ("www.leroymerlin.pl"),
    OBI ("market obi"),
    RESERVED ("lpp reserved"),
    MEDIA_MARKT ("media markt"),
    KLIMA ("michał kościński"),
    EMPIK_FOTO ("empikfoto"),
    POSTER_STORE ("poster store"),
    FOTOJOKER ("fotojoker");

    private String sentence;

    public static boolean matchTransactionRow(Transaction transaction) {
        return Arrays.stream(DomDictionary.values())
                .anyMatch(dictIt ->
                          transaction.getAdditional_info()
                                .toLowerCase()
                                .contains(dictIt.sentence) ||
                          transaction.getAdditional_info_2()
                                .toLowerCase()
                                .contains(dictIt.sentence) ||
                          transaction.getDescription()
                                .toLowerCase()
                                .contains(dictIt.sentence) ||
                          transaction.getReceiver()
                                .toLowerCase()
                                .contains(dictIt.sentence)
                );
    }
}
