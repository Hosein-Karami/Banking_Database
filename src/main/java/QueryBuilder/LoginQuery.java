package QueryBuilder;

import java.sql.Timestamp;

public class LoginQuery {

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
