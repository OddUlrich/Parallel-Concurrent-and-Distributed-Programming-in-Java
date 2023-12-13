package org.oo.demo.pseudo.stretch;

import org.oo.demo.annotation.GuardedBy;
import org.oo.demo.annotation.ThreadSafe;

import java.util.HashSet;
import java.util.Set;

@ThreadSafe
public class ServerStatus {

    @GuardedBy("users")
    public final Set<String> users;
    @GuardedBy("queries")
    public final Set<String> queries;

    public ServerStatus() {
        this.users = new HashSet<>();
        this.queries = new HashSet<>();
    }

    public void addUser(String u) {
        synchronized (users) {
            users.add(u);
        }
    }

    public void addQuery(String q) {
        synchronized (queries) {
            queries.add(q);
        }
    }

    public void removeUser(String u) {
        synchronized (users) {
            users.remove(u);
        }
    }

    public void removeQuery(String q) {
        synchronized (queries) {
            queries.remove(q);
        }
    }


}
