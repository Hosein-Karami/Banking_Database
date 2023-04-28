package Controller.Dashboard;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class GeneralDashboard {

    protected String username;
    public abstract void run();

}
