package com.zoo.sparrow.thrift;

import com.zoo.sparrow.thrift.generated.Person;
import com.zoo.sparrow.thrift.generated.PersonService;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 * Created by David.Liu on 17/6/25.
 */
public class ThriftClient {

    public static void main(String[] args) {
        TTransport transport = new TFramedTransport(new TSocket("localhost", 8899));
        TProtocol protocol = new TCompactProtocol(transport);
        PersonService.Client client = new PersonService.Client(protocol);

        try {

            transport.open();

            Person person = client.getPersonByName("张三");
            System.out.println(person);

            System.out.println("-----------save person...");
            Person person1 = new Person();
            person1.setName("李四");
            person1.setAge(30);
            person1.setGender("女");

            client.savePerson(person1);

        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        } finally {
            transport.close();
        }
    }
}
