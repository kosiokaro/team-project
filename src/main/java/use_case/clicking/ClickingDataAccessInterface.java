package use_case.clicking;
import entity.MediaDetailsResponse;

public interface ClickingDataAccessInterface {
    MediaDetailsResponse fetchDetailsById(int id);
}
