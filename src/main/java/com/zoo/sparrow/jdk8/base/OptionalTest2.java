package com.zoo.sparrow.jdk8.base;

import java.util.Optional;

/**
 * Created by David.Liu on 17/8/11.
 */
public class OptionalTest2 {

    public static void main(String[] args) {
        Person p = new Person();
        Optional<Person> optPerson = Optional.ofNullable(p);
//        Optional<Car> op = optPerson.flatMap(p1 -> p1.getCar());
//        System.out.println(op);

        System.out.println(p.getCarInsuranceName(optPerson));


    }

    public static class Person {
        Car car;

        public Optional<Car> getCar() {
            return Optional.ofNullable(car);
        }

        public String getCarInsuranceName(Optional<Person> person) {
            return person.flatMap(Person::getCar)
                    .flatMap(Car::getInsurance)
                    .map(Insurance::getName)
                    .orElse("Unknown");
        }

    }

    public static class Car {
        Optional<Insurance> insurance;

        public Optional<Insurance> getInsurance() {
            return insurance;
        }

        public void setInsurance(Optional<Insurance> insurance) {
            this.insurance = insurance;
        }
    }

    public static class Insurance {
        String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


    }
}
