import java.util.List;

public interface Loggable {
    void addLog(MoodLog log);
    List<MoodLog> viewLog();
}
