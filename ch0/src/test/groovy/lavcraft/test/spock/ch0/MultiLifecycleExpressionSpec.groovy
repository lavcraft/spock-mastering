package lavcraft.test.spock.ch0

import spock.lang.Specification

import java.util.function.Function

/**
 * @author tolkv
 * @since 8/23/2015
 */
class MultiLifecycleExpressionSpec extends Specification {
    def 'multiple when/then expressions'() {
        given:
        def serviceMock = Mock(Function)
        def result = ''
        when:
        result = serviceMock.apply('Hello')

        then:
        1 * serviceMock.apply(_) >> 'It`s cool'
        result == 'It`s cool'

        when:
        result = serviceMock.apply('Hello my friend')

        then:
        2 * serviceMock.apply(_) >> 'It`s coolest feature, bro!'
        and: 'does not invoke if condition above is false..'
        result == 'It`s coolest feature, bro!2'
    }
}
