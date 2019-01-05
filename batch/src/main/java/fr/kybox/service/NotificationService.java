package fr.kybox.service;

public interface NotificationService {

    boolean setReservationAvailable(String token, String isbn, String email);
}
