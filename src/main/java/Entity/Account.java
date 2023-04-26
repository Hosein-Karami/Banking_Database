package Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Getter
public class Account {

    private String username;
    private String password;
    private long accountNumber;
    private String firstName;
    private String lastName;
    private long nationalId;
    private Date birthdate;
    private AccountType type;
    private double interestRate;

}
