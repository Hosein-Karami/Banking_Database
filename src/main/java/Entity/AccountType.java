package Entity;

public enum AccountType {
    client,employee;

    public static String getType(AccountType accountType){
        if(accountType == AccountType.client)
            return "Client";
        else
            return "Employee";
    }

}
