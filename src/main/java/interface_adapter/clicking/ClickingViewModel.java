package interface_adapter.clicking;

import interface_adapter.ViewModel;

public class ClickingViewModel extends ViewModel<ClickingState>{
    public ClickingViewModel() {
    super("clicking");
    setState(new ClickingState());}
}