package Entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Account {

    private String username;
    private String accountId;
    private String password;
    private String firstName;
    private String lastName;
    private String nationalId;
    private LocalDateTime birthdate;
    private AccountType type;
    private double interestRate;

}
