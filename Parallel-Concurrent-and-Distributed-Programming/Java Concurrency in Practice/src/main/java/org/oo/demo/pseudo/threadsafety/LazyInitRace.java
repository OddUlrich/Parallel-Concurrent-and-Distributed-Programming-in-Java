package org.oo.demo.pseudo.threadsafety;

import org.oo.demo.annotation.NotThreadSafe;
import org.oo.demo.model.ExpensiveObject;

@NotThreadSafe
public class LazyInitRace {
    private ExpensiveObject instance = null;

    public ExpensiveObject getInstance() {
        if (instance == null) {
            instance = new ExpensiveObject();
        }
        return instance;
    }
}


