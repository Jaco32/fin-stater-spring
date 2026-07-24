package edu.jaco.fin_stater.dictionaries.cats.transport;

import edu.jaco.fin_stater.transaction.Transaction;
import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
public enum TransportDictionary {

    // Serwis
    DYNAMICA ("dynamica sp. z o.o."),
    PGD ("grupa pgd sp z o.o."),
    UBEZPIECZENIE_LEONA ("generali t.u. s.a."),
    UBEZPIECZENIE_TOURANA ("ubezpieczenia.vwsu.pl"),
    GREEN_SHINE ("green shine"),
    VW ("vw bank polska"),
    VW_2 ("volkswagen financial services"),
    VW_3 ("cichy - zasada"),
    VW_4 ("vw gcz"),
    OTOMOTO ("www.otomoto.pl"),

    // Paliwo
    BP ("bp-"),
    LOTOS ("lotos"),
    ORLEN ("orlen"),
    ORLEN_2 ("stacja paliw nr 7578"),
    CIRCLE_K ("circle k"),
    STACJA_LIBIAZ ("stacja paliw nr 7011"),

    // Parkingi
    PARKING_APCOA ("apcoa"),
    PARKING_APCOA_2 ("parking 053"),
    PARKING_GALERIA_KAZIMIERZ ("galeria kazimierz parking"),

    // Bilety
    JAK_DOJADE ("jakdojade.pl"),
    MPK_KRAKOW_1("mpk kraków"),
    MPK_KRAKOW_2("mpk krakow"),
    KASOWNIK_ZTP ("kasownik ztp krakow"),
    INTERCITY ("intercity.pl"),
    AUTOSTRADA_A4 ("opł. za przejazd a4"),
    A4_BRAMKI ("balice manual");

    private String sentence;

    public static boolean matchTransactionRow(Transaction transaction) {
        return Arrays.stream(TransportDictionary.values())
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
