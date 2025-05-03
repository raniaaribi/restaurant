package restaurantmanagement;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import static oracle.net.aso.C00.x;
import static oracle.net.aso.C00.y;
import static restaurantmanagement.order_col_quantity.setCellValueFactory;

public class dashboardController implements Initializable {
     @FXML
    private AnchorPane main_form;
    @FXML
    private Button availablefd_updatebtn;
    @FXML
    private Button availablefd_addbtn;

    @FXML
    private Button availablefd_btn;

    @FXML
    private Button availablefd_clearbtn;

    @FXML
    private TableColumn<Categories, String> availablefd_col_productid;

    @FXML
    private TableColumn<Categories, String> availablefd_col_productname;

    @FXML
    private TableColumn<Categories, Double>availablefd_col_productprice;

    @FXML
    private TableColumn<Categories, String> availablefd_col_productstatus;

    @FXML
    private TableColumn<Categories, String> availablefd_col_producttype;

    @FXML
    private Button availablefd_deletebtn;

    @FXML
    private AnchorPane availablefd_form;

    @FXML
    private TextField availablefd_productid;

    @FXML
    private TextField availablefd_productname;

    @FXML
    private TextField availablefd_productprice;

    @FXML
    private ComboBox<String> availablefd_productstauts;

    @FXML
    private ComboBox<String> availablefd_producttype;

    @FXML
    private TextField availablefd_search;

    

    @FXML
    private Button close;

    @FXML
    private Button dashboard_btn;

    @FXML
    private AnchorPane dashboard_form;

   @FXML
    private AreaChart<?, ?> dashboard_icchart;

    @FXML
    private Label dashboard_nc;

    @FXML
    private BarChart<?, ?> dashboard_ncchart;

    @FXML
    private Label dashboard_ti;

    @FXML
    private Label dashboard_tincome;

    @FXML
    private Button logout;

    @FXML
    private Button minimize;

    @FXML
    private Button order_addbtn;

    @FXML
    private TextField order_amount;

    @FXML
    private Label order_balance;
    @FXML
    private Button order_btn;

    @FXML
    private TableColumn<?, ?> order_col_productid;

    @FXML
    private TableColumn<?, ?> order_col_productname;

    @FXML
    private TableColumn<?, ?> order_col_productprice;

    @FXML
    private TableColumn<?, ?> order_col_productquantity;

    @FXML
    private TableColumn<?, ?> order_col_producttype;

    @FXML
    private AnchorPane order_form;

    @FXML
    private Button order_paybtn;

    @FXML
    private ComboBox<?> order_productID;

    @FXML
    private ComboBox<?> order_productName;

    @FXML
    private Spinner<Integer> order_quantity;

    @FXML
    private Button order_receiptbtn;

    @FXML
    private Button order_removebtn;
    @FXML
    private TableView<Product> order_tableview;

    @FXML
    private Label order_total;
    @FXML
    private TableView<Categories> table_view;
    @FXML
    private Label username;
    
     private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
    private ObservableList<Categories> availableFDList;
     public void dashboardNC() {

        String sql = "SELECT COUNT(id) FROM product_info";

        int nc = 0;

        connect = Database.connectDb();

        try {

            statement = connect.createStatement();
            result = statement.executeQuery(sql);

            if (result.next()) {
                nc = result.getInt("COUNT(id)");
            }

           dashboard_nc.setText(String.valueOf(nc));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dashboardTI() {

        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        String sql = "SELECT SUM(total) FROM product_info WHERE date = '" + sqlDate + "'";

        connect = Database.connectDb();

        double ti = 0;

        try {
            statement = connect.createStatement();
            result = statement.executeQuery(sql);

            if (result.next()) {
                ti = result.getDouble("SUM(total)");
            }

            dashboard_ti.setText("$" + String.valueOf(ti));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dashboardTIncome() {

        String sql = "SELECT SUM(total) FROM product_info";

        connect = Database.connectDb();

        double ti = 0;

        try {

            statement = connect.createStatement();
            result = statement.executeQuery(sql);

            if (result.next()) {
                ti = result.getDouble("SUM(total)");
            }
            dashboard_tincome.setText("$" + String.valueOf(ti));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void dashboardNOCCChart() {

        try {

            dashboard_ncchart.getData().clear();

            String sql = "SELECT date, COUNT(id) FROM product_info GROUP BY date ORDER BY TIMESTAMP(date) ASC LIMIT 5";

            connect = Database.connectDb();

            XYChart.Series chart = new XYChart.Series();

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                chart.getData().add(new XYChart.Data(result.getString(1), result.getInt(2)));
            }

           dashboard_ncchart.getData().add(chart);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void dashboardICC() {

       dashboard_icchart.getData().clear();

        String sql = "SELECT date, SUM(total) AS total_sum \n" +
"FROM product_info \n" +
"GROUP BY date \n" +
"ORDER BY date ASC \n" +
"LIMIT 7;";

        connect = Database.connectDb();

        try {

            XYChart.Series chart = new XYChart.Series();

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {

                chart.getData().add(new XYChart.Data(result.getString(1), result.getDouble(2)));

            }

           dashboard_icchart.getData().add(chart);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    
    public ObservableList<Categories> availableFDListData() {

        ObservableList<Categories> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM category";

        connect = Database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            Categories cat;

            while (result.next()) {
                cat = new Categories(result.getString("product_id"),
                        result.getString("product_name"), result.getString("type"),
                        result.getDouble("price"), result.getString("status"));

                listData.add(cat);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
        
    }
   public void availableFDShowData() {
    // Charger les données dans la liste Observable
    availableFDList = availableFDListData();
    
    // Configurer les colonnes du TableView
    availablefd_col_productid.setCellValueFactory(new PropertyValueFactory<>("productId"));
    availablefd_col_productname.setCellValueFactory(new PropertyValueFactory<>("name"));
    availablefd_col_producttype.setCellValueFactory(new PropertyValueFactory<>("type"));
    availablefd_col_productprice.setCellValueFactory(new PropertyValueFactory<>("price"));
    availablefd_col_productstatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    
    // Lier les données au TableView
    table_view.setItems(availableFDList);
}

      public void availableFDAdd() {

        String sql = "INSERT INTO category (product_id, product_name, type, price, status) "
                + "VALUES(?,?,?,?,?)";

        connect = Database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, availablefd_productid.getText());
            prepare.setString(2, availablefd_productname.getText());
            prepare.setString(3, (String) availablefd_producttype.getSelectionModel().getSelectedItem());
            prepare.setString(4, availablefd_productprice.getText());
            prepare.setString(5, (String) availablefd_productstauts.getSelectionModel().getSelectedItem());

            Alert alert;

            if (availablefd_productid.getText().isEmpty()
                    || availablefd_productname.getText().isEmpty()
                    || availablefd_producttype.getSelectionModel() == null
                    | availablefd_productprice.getText().isEmpty()
                    ||  availablefd_productstauts.getSelectionModel() == null) {

                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();

            } else {

                String checkData = "SELECT product_id FROM category WHERE product_id = '"
                        + availablefd_productid.getText() + "'";

                connect = Database.connectDb();

                statement = connect.createStatement();
                result = statement.executeQuery(checkData);

                if (result.next()) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Product ID: " + availablefd_productid.getText() + " is already exist!");
                    alert.showAndWait();
                } else {
                      prepare.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();

                    // TO SHOW THE DATA
                      availableFDShowData();
                    // TO CLEAR THE FIELDS
                      availableFDClear();

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void availableFDUpdate() {

        String sql = "UPDATE category SET product_name = '"
                + availablefd_productname.getText() + "', type = '"
                + availablefd_producttype.getSelectionModel().getSelectedItem() + "', price = '"
                + availablefd_productprice.getText() + "', status = '"
                + availablefd_productstauts.getSelectionModel().getSelectedItem()
                + "' WHERE product_id = '" + availablefd_productid.getText() + "'";

        connect = Database.connectDb();

        try {

            Alert alert;

            if (availablefd_productid.getText().isEmpty()
                    || availablefd_productname.getText().isEmpty()
                    || availablefd_producttype.getSelectionModel().getSelectedItem() == null
                    || availablefd_productprice.getText().isEmpty()
                    || availablefd_productstauts.getSelectionModel().getSelectedItem() == null) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {

                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE Product ID: "
                        + availablefd_productid.getText() + "?");

                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();

                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    // TO SHOW THE DATA
                    availableFDShowData();
                    // TO CLEAR THE FIELDS
                     availableFDClear();

                } else {
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Cancelled.");
                    alert.showAndWait();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    } 
    public void availableFDDelete() {

        String sql = "DELETE FROM category WHERE product_id = '"
                + availablefd_productid.getText() + "'";

        connect = Database.connectDb();

        try {

            Alert alert;

            if (availablefd_productid.getText().isEmpty()
                    || availablefd_productname.getText().isEmpty()
                    || availablefd_producttype.getSelectionModel().getSelectedItem() == null
                    || availablefd_productprice.getText().isEmpty()
                    ||availablefd_productstauts.getSelectionModel().getSelectedItem() == null) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {

                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to DELETE Product ID: "
                        + availablefd_productid.getText() + "?");

                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();

                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    // TO SHOW THE DATA
                    availableFDShowData();
                    // TO CLEAR THE FIELDS
                    availableFDClear();

                } else {
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Cancelled.");
                    alert.showAndWait();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
      public void availableFDSearch() {

        FilteredList<Categories> filter = new FilteredList<>(availableFDList, e -> true);

        availablefd_search.textProperty().addListener((observabl, newValue, oldValue) -> {

            filter.setPredicate(predicateCategories -> {

                if (newValue.isEmpty() || newValue == null) {
                    return true;
                }

                String searchKey = newValue.toLowerCase();

                if (predicateCategories.getProductId().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateCategories.getName().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateCategories.getType().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateCategories.getPrice().toString().contains(searchKey)) {
                    return true;
                } else if (predicateCategories.getStatus().toLowerCase().contains(searchKey)) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<Categories> sortList = new SortedList<>(filter);

        sortList.comparatorProperty().bind(table_view.comparatorProperty());
        table_view.setItems(sortList);
    }

 
    
    
    private String[] categories = {"Meals", "Drinks"};

    public void availableFDType() {
        List<String> listCat = new ArrayList<>();

        for (String data : categories) {
            listCat.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listCat);
       availablefd_producttype.setItems(listData);

    }
    private String[] status = {"Available", "unavailable"};

    public void availableFDStatus() {
        List<String> listStatus = new ArrayList<>();

        for (String data : status) {
            listStatus.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listStatus);
        availablefd_productstauts.setItems(listData);

    }
     
 public void switchForm(ActionEvent event) {

        if (event.getSource() == dashboard_btn) {
            dashboard_form.setVisible(true);
            availablefd_form.setVisible(false);
            order_form.setVisible(false);

            dashboard_btn.setStyle("-fx-background-color: #3796a7; -fx-text-fill: #fff; -fx-border-width: 0px;");
            availablefd_btn.setStyle("-fx-background-color: transparent; -fx-border-width: 1px; -fx-text-fill: #000;");
            order_btn.setStyle("-fx-background-color: transparent; -fx-border-width: 1px; -fx-text-fill: #000;");

            /*dashboardNC();
            dashboardTI();
            dashboardTIncome();
            dashboardNOCCChart();
            dashboardICC();*/

        } else if (event.getSource() == availablefd_btn) {
            dashboard_form.setVisible(false);
            availablefd_form.setVisible(true);
            order_form.setVisible(false);

           availablefd_btn.setStyle("-fx-background-color: #3796a7; -fx-text-fill: #fff; -fx-border-width: 0px;");
            dashboard_btn.setStyle("-fx-background-color: transparent; -fx-border-width: 1px; -fx-text-fill: #000;");
            order_btn.setStyle("-fx-background-color: transparent; -fx-border-width: 1px; -fx-text-fill: #000;");

            /*availableFDShowData();
            availableFDSearch();*/

        } else if (event.getSource() == order_btn) {
            dashboard_form.setVisible(false);
            availablefd_form.setVisible(false);
            order_form.setVisible(true);

            order_btn.setStyle("-fx-background-color: #3796a7; -fx-text-fill: #fff; -fx-border-width: 0px;");
            availablefd_form.setStyle("-fx-background-color: transparent; -fx-border-width: 1px; -fx-text-fill: #000;");
            dashboard_btn.setStyle("-fx-background-color: transparent; -fx-border-width: 1px; -fx-text-fill: #000;");

            orderProductId();
            orderProductName();
            orderSpinner();
            orderListData();
            orderDisplayTotal();
        }

    }
 public void availableFDSelect() {
    Categories catData = table_view.getSelectionModel().getSelectedItem();

    int num = table_view.getSelectionModel().getSelectedIndex();

    // If no row is selected, return
    if ((num - 1) < -1) {
        return;
    }

    // Set the text fields
    availablefd_productid.setText(catData.getProductId());
    availablefd_productname.setText(catData.getName());
    availablefd_productprice.setText(String.valueOf(catData.getPrice()));

    // Set the combo boxes (assuming you have combo boxes for status and type)
    availablefd_productstauts.setValue(catData.getStatus());  // Status from the Categories object
    availablefd_producttype.setValue(catData.getType());  // Type from the Categories object
}


 
    public void availableFDClear() {

        availablefd_productid.setText("");
        availablefd_productname.setText("");
        availablefd_producttype.getSelectionModel().clearSelection();
        availablefd_productprice.setText("");
        availablefd_productstauts.getSelectionModel().clearSelection();

    }
    
 public void displayUsername() {
        String user = data.username;
        user = user.substring(0, 1).toUpperCase() + user.substring(1);
        username.setText(user);
    }
    public void close() {
        System.exit(0);
    }
    public void minimize() {
        Stage stage = (Stage)main_form.getScene().getWindow();
        stage.setIconified(true);
    }
    private double x = 0;
      private double y = 0;
    public void logout() {

        try {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get().equals(ButtonType.OK)) {

                logout.getScene().getWindow().hide();

                // LINK YOUR LOGIN FORM
                Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);

                root.setOnMousePressed((MouseEvent event) -> {
                    x = event.getSceneX();
                    y = event.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent event) -> {
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);

                    stage.setOpacity(.8f);
                });

                root.setOnMouseReleased((MouseEvent event) -> {
                    stage.setOpacity(1);
                });

                stage.initStyle(StageStyle.TRANSPARENT);

                stage.setScene(scene);
                stage.show();

            }

        } catch (Exception e) {
            e.printStackTrace();}}
      public void orderProductName() {

        String sql = "SELECT product_name FROM category WHERE product_id = '"
                + order_productID.getSelectionModel().getSelectedItem() + "'";

        connect = Database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            ObservableList listData = FXCollections.observableArrayList();

            while (result.next()) {
                listData.add(result.getString("product_name"));
            }

            order_productName.setItems(listData);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void orderProductId() {

        String sql = "SELECT product_id FROM category WHERE status = 'Available'";

        connect = Database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            ObservableList listData = FXCollections.observableArrayList();

            while (result.next()) {
                listData.add(result.getString("product_id"));
            }

            order_productID.setItems(listData);

            orderProductName();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
private SpinnerValueFactory<Integer> spinner;

    public void orderSpinner() {
        spinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50, 0);

        order_quantity.setValueFactory(spinner);
    }

    private int qty;

    public void orderQuantity() {
        qty = order_quantity.getValue();
    }
     
   
        private int customerId;
          public void orderCustomerId() {

        String sql = "SELECT customer_id FROM product";

        connect = Database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                customerId = result.getInt("customer_id");
            }
            String checkData = "SELECT customer_id FROM product_info";

            statement = connect.createStatement();
            result = statement.executeQuery(checkData);

            int customerInfoId = 0;

            while (result.next()) {
                customerInfoId = result.getInt("customer_id");
            }

            if (customerId == 0) {
                customerId += 1;
            } else if (customerId == customerInfoId) {
                customerId += 1;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
   private double totalP = 0;

    public void orderTotal() {
        orderCustomerId();

        String sql = "SELECT SUM(price) FROM product WHERE customer_id = " + customerId;

        connect = Database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                totalP = result.getDouble("SUM(price)");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
        public void orderDisplayTotal() {
        orderTotal();
        order_total.setText("$" + String.valueOf(totalP));
     
        }
    
    
    
    public ObservableList<Product> orderListData() {

        orderCustomerId();

        ObservableList<Product> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM product WHERE customer_id = " + customerId;

        connect = Database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            Product prod;

            while (result.next()) {
                prod = new Product(result.getInt("id"),
                         result.getString("product_id"),
                         result.getString("product_name"),
                         result.getString("type"),
                         result.getDouble("price"),
                         result.getInt("quantity"));

                listData.add(prod);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }
       public void orderAdd() {

        orderCustomerId();
        orderTotal();

        String sql = "INSERT INTO product "
                + "(customer_id, product_id, product_name, type, price, quantity, date) "
                + "VALUES(?,?,?,?,?,?,?)";

        connect = Database.connectDb();

        try {
            String orderType = "";
            double orderPrice = 0;

            String checkData = "SELECT * FROM category WHERE product_id = '"
                    + order_productID.getSelectionModel().getSelectedItem() + "'";

            statement = connect.createStatement();
            result = statement.executeQuery(checkData);

            if (result.next()) {
                orderType = result.getString("type");
                orderPrice = result.getDouble("price");
            }

            prepare = connect.prepareStatement(sql);
            prepare.setString(1, String.valueOf(customerId));
            prepare.setString(2, (String) order_productID.getSelectionModel().getSelectedItem());
            prepare.setString(3, (String) order_productName.getSelectionModel().getSelectedItem());
            prepare.setString(4, orderType);

            double totalPrice = orderPrice * qty;

            prepare.setString(5, String.valueOf(totalPrice));

            prepare.setString(6, String.valueOf(qty));

            Date date=new Date();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());

            prepare.setString(7, String.valueOf(sqlDate));

            prepare.executeUpdate();
            orderDisplayTotal();
            orderDisplayData();
            

        } catch (Exception e) {
            e.printStackTrace();
        }
      

    } 
        public void orderRemove() {

        String sql = "DELETE FROM product WHERE id = " + item;

        connect = Database.connectDb();

        try {
            Alert alert;

            if (item == 0 || String.valueOf(item) == null || String.valueOf(item) == "") {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please select the item first");
                alert.showAndWait();
            } else {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to Remove Item: " + item + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Removed!");
                    alert.showAndWait();

                    orderDisplayData();
                    orderDisplayTotal();

                    order_amount.setText("");
                    order_balance.setText("$0.0");

                } else {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Cancelled!");
                    alert.showAndWait();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
       private int item;

    public void orderSelectData() {

        Product prod = order_tableview.getSelectionModel().getSelectedItem();
        int num = order_tableview.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        item = prod.getId();
    }


    public void orderPay() {
        orderCustomerId();
        orderTotal();
        
        String sql = "INSERT INTO product_info (customer_id, total, date) VALUES(?,?,?)";

        connect =Database.connectDb();
                try {

            Alert alert;

            if (balance == 0 || String.valueOf(balance) == "$0.0" || String.valueOf(balance) == null
                    || totalP == 0 || String.valueOf(totalP) == "$0.0" || String.valueOf(totalP) == null) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Invalid :3");
                alert.showAndWait();
            } else {

                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure?");
                Optional<ButtonType> option = alert.showAndWait();
           if (option.get().equals(ButtonType.OK)) {
                 
                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, String.valueOf(customerId));
                    prepare.setString(2, String.valueOf(totalP));

                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                    prepare.setString(3, String.valueOf(sqlDate));
                    prepare.executeUpdate();
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successful!");
                    alert.showAndWait();
                     order_total.setText("$0.0");
                    order_balance.setText("$0.0");
                    order_amount.setText("");

      
                } else {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Cancelled!");
                    alert.showAndWait();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }}


       private ObservableList<Product> orderData;

       public void orderDisplayData() {
        orderData = orderListData();

        order_col_productid.setCellValueFactory(new PropertyValueFactory<>("productId"));
        order_col_productname.setCellValueFactory(new PropertyValueFactory<>("name"));
        order_col_producttype.setCellValueFactory(new PropertyValueFactory<>("type"));
        order_col_productprice.setCellValueFactory(new PropertyValueFactory<>("price"));
        order_col_productquantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

      order_tableview .setItems(orderData);

    }  
       private double amount;
       private double balance;
        public void orderAmount() {
        orderTotal();

        Alert alert;

        if (order_amount.getText().isEmpty() || order_amount.getText() == null
                || order_amount.getText() == "") {
            alert = new Alert(AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please type the amount!");
            alert.showAndWait();
        } else {
            amount = Double.parseDouble(order_amount.getText());

            if (amount < totalP) {
                order_amount.setText("");
            } else {
                balance = (amount - totalP);
                order_balance.setText("$" + String.valueOf(balance));
            }
        }
           }
        

    public void genererRecu() {
        // Message du reçu
        String messageRecu = "Reçu généré avec succès !\nMontant: 50 DZD\nDate: 01/12/2024";
        
        // Création d'une alerte d'information
        Alert alerte = new Alert(AlertType.INFORMATION);
        alerte.setTitle("Reçu");
        alerte.setHeaderText("Voici votre reçu");
        alerte.setContentText(messageRecu);
        
        // Affichage de l'alerte
        alerte.showAndWait();
    }

  


    @Override
    public void initialize(URL location, ResourceBundle resources) {  
       dashboardNC();
        dashboardTI();
        dashboardTIncome();
        dashboardNOCCChart();
        dashboardICC();
          displayUsername();
          availableFDStatus();
          availableFDType();
         availableFDShowData();
            orderProductId();
            orderProductName();
            orderSpinner();
            orderDisplayData();
             orderDisplayTotal();
         
    }
    

    
    
}
