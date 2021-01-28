
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Calisanİslemleri {
  private  Connection connection=null;
  private  Statement statement=null;
  
  private PreparedStatement preparedStatement=null;
  public void calisanGuncelle(int id,String yeniad,String yenisoyad,String yenidepartman,String yenimaas){
    String sorgu="Update calisanlar set ad=?,soyad=?,departman=?, maas=? where id=?";
      try {
          preparedStatement=connection.prepareStatement(sorgu);
          preparedStatement.setString(1, yeniad);
          preparedStatement.setString(2, yenisoyad);
          preparedStatement.setString(3, yenidepartman);
          preparedStatement.setString(4,yenimaas);
          preparedStatement.setInt(5, id);
          preparedStatement.executeUpdate();
      } catch (SQLException ex) {
          Logger.getLogger(Calisanİslemleri.class.getName()).log(Level.SEVERE, null, ex);
      }
  
  
  }
  public ArrayList<Calisan> calisanlariGetir(){
    ArrayList<Calisan> cikti=new ArrayList<Calisan>();
      try {
          statement =connection.createStatement();
          String sorgu="Select * From calisanlar";
          ResultSet resultSet=statement.executeQuery(sorgu);
          while(resultSet.next()){
              int id=resultSet.getInt("id");
              String ad=resultSet.getString("ad");
              String soyad=resultSet.getString("soyad");
              String departman=resultSet.getString("departman");
              int maas=resultSet.getInt("maas");
            cikti.add(new Calisan(id, ad, soyad, departman, maas));
          }
          return cikti;
          
      } catch (SQLException ex) {
          Logger.getLogger(Calisanİslemleri.class.getName()).log(Level.SEVERE, null, ex);
          return null;
      }
      
       
  }
  public void calisanSil(int id){
  String sorgu="Delete from calisanlar where id=?";
      try {
          preparedStatement=connection.prepareStatement(sorgu);
          preparedStatement.setInt(1, id);
          preparedStatement.executeUpdate();
      } catch (SQLException ex) {
          Logger.getLogger(Calisanİslemleri.class.getName()).log(Level.SEVERE, null, ex);
      }
  
  
  }
  public void calisanEkle(String ad,String soyad,String departman,String maas){
      String sorgu="Insert Into calisanlar (ad,soyad,departman,maas) values (?,?,?,?)";
      try {
          preparedStatement=connection.prepareStatement(sorgu);
          preparedStatement.setString(1, ad);
          preparedStatement.setString(2, soyad);
          preparedStatement.setString(3, departman);
          preparedStatement.setString(4, maas);
          
          preparedStatement.executeUpdate();
      } catch (SQLException ex) {
          Logger.getLogger(Calisanİslemleri.class.getName()).log(Level.SEVERE, null, ex);
      }
  
  
  
  
  }
   
    public boolean girisYap( String kullaniciAdi,String parola){
        String sorgu="Select * From adminler  where username=? and password=?";
      try {
          preparedStatement=connection.prepareStatement(sorgu);
       preparedStatement.setString(1, kullaniciAdi);
       preparedStatement.setString(2, parola);
          ResultSet resultSet=preparedStatement.executeQuery();
        return resultSet.next();
          
      } catch (SQLException ex) {
          Logger.getLogger(Calisanİslemleri.class.getName()).log(Level.SEVERE, null, ex);
          return false;
      }
        
  
    }
    public Calisanİslemleri() {
       String url="jdbc:mysql://"+Database.host+":"+Database.port+"/"+Database.db_ismi;
            try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver bulunamadi!");
        }
        try {
            connection=DriverManager.getConnection(url, Database.kullaniciAdi, Database.parola);
            System.out.println("Baglanti basarili..");
        } catch (SQLException ex) {
            System.out.println("Baglanti basarisiz");
        }

        
    }
    
          
}
