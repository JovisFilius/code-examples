package tudelft.pbt;

import tudelft.domain.ATM;

import net.jqwik.api.*;
import net.jqwik.api.constraints.Negative;
import net.jqwik.api.constraints.IntRange;

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
        void tooSmallTest(@ForAll @IntRange(min = 0, max = 19) int amount){
                assertAtmThrowsIllegalArgumentException(amount);
        }

        @Property
        void negativeTest(@ForAll @Negative int amount){
                assertAtmThrowsIllegalArgumentException(amount);
        }
        @Provide

        @Property
        void tooLargeTest(@ForAll @IntRange(min = 201) int amount){
                assertAtmThrowsIllegalArgumentException(amount);
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
