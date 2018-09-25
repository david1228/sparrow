package com.zoo.sparrow.thrift;

import com.zoo.sparrow.thrift.generated.DataException;
import com.zoo.sparrow.thrift.generated.Person;
import com.zoo.sparrow.thrift.generated.PersonService;
import org.apache.thrift.TException;

/**
 * Created by David.Liu on 17/6/25.
 */
public class PersonServiceImpl implements PersonService.Iface {
    @Override public Person getPersonByName(String name) throws DataException, TException {
        System.out.println("--------getPersonByName method invoked...name arg:" + name);
        Person person = new Person();
        person.setName("张三");
        person.setAge(20);
        person.setGender("男");

        return person;
    }

    @Override public void savePerson(Person person) throws DataException, TException {
        System.out.println("--------savePerson method invoked...Person:" + person);
    }
}
