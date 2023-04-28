package Entity;

public enum AccountType {
    client,employee;

    public static String getType(AccountType accountType){
        if(accountType == AccountType.client)
            return "Client";
        else
            return "Employee";
    }

    public static AccountType getAccountType(String accountType){
        if(accountType == null)
            return null;
        accountType = accountType.toLowerCase();
        if(accountType.equals("client"))
            return AccountType.client;
        else if(accountType.equals("employee"))
            return AccountType.employee;
        return null;
    }

}
