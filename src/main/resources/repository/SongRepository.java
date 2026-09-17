import javax.swing.tree.RowMapper;

public class SongRepository {
    private final JdbcTemplate jdbc;

    public SongRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private final RowMapper<Song> songMapper = (rs, rowNum) -> {
        Song song = new Song();
        song.setId(rs.getInt("id"));
        song.setTitle(rs.getString("title"));
        song.setArtist(rs.getString("artist"));
        song.setInstrument(rs.getString("instrument"));
        song.setLevel(rs.getInt("level"));
        song.setCreatedDay(rs.getTimestamp("created_day").toLocalDateTime());
        return song;
    }

    // 조회
    public List<Song> findAll() {
        return jdbc.query("select * from song order by created_day desc", songMapper);
    }

    // 등록
    public void insert(Song song) {
        jdbc.update("insert into song (title, artist, instrument) values (?,?,?)", song.getTitle(), song.getArtist(), song.getInstrument());
    }

    // 수정
    public void update(Song song) {
        jdbc.update("update song set title=?, artist=?, instrument=?, where id=?", song.getTitle(), song.getArtist(), song.getInstrument(), song.getId());
    }

    // 삭제
    public void delete(int id) {
        jdbc.update("delete from song where id=?", id);
    }
}