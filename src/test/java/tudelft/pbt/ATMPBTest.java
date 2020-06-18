package assignments.pbt;

import assignments.domain.ATM;

import net.jqwik.api.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ATMPBTest {
        
        private final ATM atm = new ATM();

        private void assertAtmThrowsIllegalArgumentException(int amount){
               assertThatThrownBy(() -> {
                       atm.validWithdraw(amount);
               })
               .isInstanceOf(IllegalArgumentException.class);
        }

        @Property
        void tooSmallTest(@ForAll("tooSmallAmounts") int amount){
                assertAtmThrowsIllegalArgumentException(amount);
        }
        @Provide
        Arbitrary<Integer> tooSmallAmounts(){
                return Arbitraries.integers().greaterOrEqual(0)
                        .filter(k -> k < 20);
        }

        @Property
        void negativeTest(@ForAll("negativeAmounts") int amount){
                assertAtmThrowsIllegalArgumentException(amount);
        }
        @Provide
        Arbitrary<Integer> negativeAmounts(){
                return Arbitraries.integers().lessOrEqual(-1);
        }

        @Property
        void tooLargeTest(@ForAll("tooLargeAmounts") int amount){
                assertAtmThrowsIllegalArgumentException(amount);
        }
        @Provide
        Arbitrary<Integer> tooLargeAmounts(){
                return Arbitraries.integers().greaterOrEqual(0)
                        .filter(k -> k > 200);
        }

        @Property
        void validAmountTest(@ForAll("validAmounts") int amount){
                assertThat(atm.validWithdraw(amount));
        }
        @Provide
        Arbitrary<Integer> validAmounts(){
                return Arbitraries.integers().greaterOrEqual(20)
                        .filter(k -> k % 20 == 0 && k <= 200);
        }

        @Property
        void invalidAmountTest(@ForAll("invalidAmounts") int amount){
                assertThat(!atm.validWithdraw(amount));
        }
        @Provide
        Arbitrary<Integer> invalidAmounts(){
                return Arbitraries.integers().greaterOrEqual(20)
                        .filter(k -> k % 20 != 0 && k <= 200);
        }

}
