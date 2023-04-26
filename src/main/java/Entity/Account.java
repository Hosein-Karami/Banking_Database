package Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Account {

    private String username;
    private String password;
    private long accountNumber;
    private String firstName;
    private String lastName;
    private long nationalId;
    private String birthdate;
    private AccountType type;
    private double interestRate;

}
