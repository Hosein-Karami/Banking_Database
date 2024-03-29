package QueryBuilder;

import Entity.Account;
import Entity.AccountType;

import java.sql.Timestamp;

public class AccountQuery {

    public static String register(Account account){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CALL Register('");
        stringBuilder.append(account.getUsername());
        stringBuilder.append("',");
        stringBuilder.append(account.getAccountNumber());
        stringBuilder.append(",'");
        stringBuilder.append(account.getPassword());
        stringBuilder.append("','");
        stringBuilder.append(account.getFirstName());
        stringBuilder.append("','");
        stringBuilder.append(account.getLastName());
        stringBuilder.append("',");
        stringBuilder.append(account.getNationalId());
        stringBuilder.append(",'");
        stringBuilder.append(account.getBirthdate());
        stringBuilder.append("','");
        stringBuilder.append(AccountType.getType(account.getType()));
        stringBuilder.append("',");
        stringBuilder.append(account.getInterestRate());
        stringBuilder.append(");");
        return stringBuilder.toString();
    }

    public static String loginLog(String username){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CALL LoginLog('");
        stringBuilder.append(username);
        stringBuilder.append("','");
        stringBuilder.append(timestamp);
        stringBuilder.append("');");
        return stringBuilder.toString();
    }

}
