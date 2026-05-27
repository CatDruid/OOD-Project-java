package org.ood.presentation;

import org.ood.presentation.Helpers.InputHandler;
import org.ood.presentation.Helpers.OutputFormatter;

public abstract class UICRUDAbstract<T> implements UIInterface {
    private InputHandler inputHandler;
    private OutputFormatter outputFormatter ;
    protected abstract void Create();
    protected abstract void RetrieveAll();
    protected abstract void RetrieveByID();
    protected abstract void Update();
    protected abstract void Delete();
    public abstract void MenuLoop();
}
