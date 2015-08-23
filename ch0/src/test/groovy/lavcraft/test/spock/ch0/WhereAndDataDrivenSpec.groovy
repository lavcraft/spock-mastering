package lavcraft.test.spock.ch0

import spock.lang.See
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import java.util.function.Function

/**
 * @author tolkv
 * @since 8/23/2015
 */
class WhereAndDataDrivenSpec extends Specification {

    public static final String NUMBER_IS_ONE_OF_LIST = 'number is one of 11,12,13'

    def 'should use data and explain how work block `where`'() {
        setup:
        def myService = Mock(Function) {
            apply(_) >> { args ->
                def result = ''
                switch (args[0]) {
                    case '0': result = 'zero'; break;
                    case '1': result = 'one'; break;
                    case '2': result = 'two'; break;
                    case [11, 12, 13]: result = NUMBER_IS_ONE_OF_LIST; break
                    case { it instanceof Integer && it > 10 }: result = 'number > 10'; break
                }
                return result
            }
        }

        expect:
        myService.apply(arg) == result

        where:
        arg | result
        '0' | 'zero'
        '1' | 'one'
        '2' | 'two'
        100 | 'number > 10'
        11  | NUMBER_IS_ONE_OF_LIST
        12  | NUMBER_IS_ONE_OF_LIST
        13  | NUMBER_IS_ONE_OF_LIST
    }

    @Shared
    String myBeautifulValue = "BeautifulVal"
    @Shared
    String myOtherBeautifulValue = "OtherBeautifulVal"
    static final String rnd = new Random().nextFloat() as String

    @Unroll
    @See("https://github.com/spockframework/spock/blob/5225ba64643f81a0c3bab0953e5ca9461a9d0f2b/spock-core/src/main/java/org/spockframework/runtime/ParameterizedSpecRunner.java")
    def '''where logic generate by AST transformation, and invoke before setup() method
           myBeautifulValue actual value is #actualVal but set in where is #myBeautifulValue
           myOtherBeautifulValue actual value is #actualOtherVal but set in where #myOtherBeautifulValue
        '''() { // IntelliJ Idea not found this test. Don't use multiline function name :)
        setup:
        myBeautifulValue = "NotBeautiful"

        expect:
        myBeautifulValue == actualVal
        myOtherBeautifulValue == actualOtherVal

        where:
        myBeautifulValue | myOtherBeautifulValue | actualVal       | actualOtherVal
        "NEWWAL"         | 'NEW_OTHER_VAL'       | 'NotBeautiful'  | 'NEW_OTHER_VAL'
        "NEWWAAAA"       | 'NEW_OTHER_VAL2'      | 'NotBeautiful'  | 'NEW_OTHER_VAL2'
        rnd              | rnd                   | 'NotBeautiful1' | rnd
    }
}
