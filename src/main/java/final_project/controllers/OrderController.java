package final_project.controllers;

import final_project.services.OrderService;
import final_project.utils.Session;
import final_project.utils.exceptions.BadRequestException;

public class OrderController {
    private OrderService orderService = new OrderService();

    public void bookRoom(long roomId, long userId, long hotelId) throws Exception {
        if(userId != Session.getUser().getId())
            throw new BadRequestException("User can't book room - not logged in system! User ID: " + userId);

        orderService.bookRoom(roomId, userId);
    }

    public void cancelReservation(long roomId, long userId, long hotelId) throws Exception {
        if(userId != Session.getUser().getId())
            throw new BadRequestException("User can't cancel reservation - not logged in system! User ID: " + userId);
        orderService.cancelReservation(roomId, userId);

    }
}
