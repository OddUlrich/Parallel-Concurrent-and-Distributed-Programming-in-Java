package org.oo.demo.pseudo.combination;

import org.oo.demo.annotation.GuardedBy;
import org.oo.demo.annotation.ThreadSafe;
import org.oo.demo.model.Person;

import java.util.HashSet;
import java.util.Set;

@ThreadSafe
public class PersonSet {
    @GuardedBy("this")
    private final Set<Person> mySet = new HashSet<>();

    public synchronized void addPerson(Person p) {
        mySet.add(p);
    }

    public synchronized boolean containsPerson(Person p) {
        return mySet.contains(p);
    }
}
