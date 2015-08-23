package lavcraft.test.spock.ch0

import groovy.transform.ToString
import spock.lang.Specification

/**
 * @author tolkv
 * @since 8/23/2015
 */
class PuzzlerGroovyWithExpressionSpec extends Specification {
    def 'should return super puzzler'() {
        given:
        def person = new Person(name: 'Test Testovich Testovskoi', data: 'Test bug')
        def data = ''

        when:
        person.with {
            data = 'Test'
        }

        then:
        person.data != 'Test'
        person.data != ''
        data == 'Test'
    }

    def 'how to fix'() {
        given:
        def person = new Person(name: 'Test Testovich Testovskoi', data: 'Test bug')
        def data = ''

        when:
        person.with {
            it.data = 'Test'
        }

        then:
        person.data == 'Test'
        person.data != ''
        data == ''
    }
}

@ToString
public class Person {
    String name
    String data
}
