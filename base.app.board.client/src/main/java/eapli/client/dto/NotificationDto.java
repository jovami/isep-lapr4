package eapli.client.dto;


public class NotificationDto {

    public String board;
    public String permissions;

    public NotificationDto(String[] str){
        if (str.length!=2){

        }
        board = str[0];
        permissions = str[1];
    }

    @Override
    public String toString() {
        return "Permissions to "+permissions+" on " + board;
    }
}
