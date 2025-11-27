package interface_adapter.RandC_success_submit;

import interface_adapter.ViewModel;

public class RandCSuccessViewModel extends ViewModel<RandCSuccessState> {
    public RandCSuccessViewModel() {
        super("RandC");
        setState(new RandCSuccessState());
    }
}
