package Service;

import Dao.EventDao;
import Dao.SnapshotDao;

import java.sql.SQLException;

public class EventService {

    private static EventService fileService = null;
    private final EventDao eventDao = EventDao.getInstance();
    private final SnapshotDao snapshotDao = SnapshotDao.getInstance();

    public static EventService getInstance() {
        if(fileService == null)
            fileService = new EventService();
        return fileService;
    }

    private EventService() {}

    public void saveDepositEvent(long accountNumber,double amount) throws SQLException {
        eventDao.saveDepositEvent(accountNumber,amount);
    }

    public void saveWithdrawEvent(long accountNumber,double amount) throws SQLException {
        eventDao.saveWithdrawEvent(accountNumber,amount);
    }

    public void saveTransferEvent(long from,long to,double amount) throws SQLException {
        eventDao.saveTransferEvent(from,to,amount);
    }

    public void saveInterestEvent(long accountNumber) throws SQLException {
        eventDao.saveInterestEvent(accountNumber);
    }

    public void runEvents() throws SQLException {
        eventDao.runEvents();
        snapshotDao.logSnapshot();
        snapshotDao.createSnapshotTable();
    }

}
