package org.ood;

import java.lang.reflect.GenericArrayType;

public abstract class RegistryAbstract<T> implements RegistryInterface<T>{
    private T[] items;
}
