package edu.jaco.fin_stater.dictionaries.cats.transport.subcats;

import edu.jaco.fin_stater.transaction.Transaction;
import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
public enum ParkingDictionary {

    PARKING_APCOA ("apcoa"),
    PARKING_APCOA_2 ("parking 053"),
    PARKING_GALERIA_KAZIMIERZ ("galeria kazimierz parking");

    private String sentence;

    public static boolean matchTransactionRow(Transaction transaction) {
        return Arrays.stream(ParkingDictionary.values())
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
