package Controller.Dashboard;

import Entity.AccountType;

public class DashboardFactory {

    public static GeneralDashboard getProperDashboard(AccountType accountType,String username){
        if(accountType == AccountType.client)
            return new ClientDashboard(username);
        else
            return new EmployeeDashboard(username);
    }

}
