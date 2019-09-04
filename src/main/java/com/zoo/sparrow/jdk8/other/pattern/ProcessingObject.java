package com.zoo.sparrow.jdk8.other.pattern;

/**
 *
 * 责任链模式
 *
 * Created by David.Liu on 17/8/14.
 */
public abstract class ProcessingObject<T> {

    protected ProcessingObject<T> successor;

    public ProcessingObject<T> setSuccessor(ProcessingObject<T> successor) {
        this.successor = successor;
        return successor;
    }

    public T handle(T input) {
       T r = handleWork(input);
       if (null != successor) {
           successor.handle(r);
       }
       return r;
    }

    // 由子类实现
    protected abstract T handleWork(T input);
}
