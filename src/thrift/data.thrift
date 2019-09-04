namespace java thrift.generated

typedef i16 short
typedef i32 int
typedef i64 long
typedef bool boolean
typedef string String

struct Person{
    1: optional String name;
    2: optional int age;
    3: optional String gender;
}

exception DataException {
    1: optional String message;
    2: optional String callStack,
    3: optional String date
}

service PersonService{
    Person getPersonByName(1:required String name) throws (1:DataException dataException),

    void savePerson(1:required Person person) throws (1:DataException dataException)

}