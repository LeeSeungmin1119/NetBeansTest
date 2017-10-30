/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coursemenu;

import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author LeeSeungmin
 */
public class FXMLCancleController implements Initializable 
{

    @FXML
    private TableView<CourseDetails> tvCancle;
    @FXML
    private TableColumn<CourseDetails, String> cancleName;
    @FXML
    private TableColumn<CourseDetails, String> cancleDay;
    @FXML
    private TableColumn<CourseDetails, String> cancleTime;
    @FXML
    private TableColumn<CourseDetails, String> cancleProf;
    @FXML
    private Button btnCancle;
    @FXML
    private Label lblCancleId;
    @FXML
    private Label lblCancleYear;
    @FXML
    private Label lblCancleName;
    @FXML
    private Label lblCancleSemester;

    private ObservableList<CourseDetails> data;
    private XMLStudentManager studentManager;
    private XMLSubjectManager subjectManager;
    private XMLCourseManager courseManager;
    private HashMap hm;
    private HashMap temphm;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        displayCancleStudentInfo();
        initCancleTableView();
    }    

    @FXML
    private void btnCancleClick(ActionEvent event) 
    {
        subjectManager = new XMLSubjectManager();
        CourseDetails courseDetails = tvCancle.getSelectionModel().getSelectedItem();
        try
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("안내");
            alert.setHeaderText("수강철회 메시지");
            alert.setContentText("수강 철회 세부사항\n" + "과목명 : " + courseDetails.getName() + 
                    "\n수강일 : " + courseDetails.getDay() + 
                    "\n수강시간 : " + courseDetails.getTime() + 
                    "\n담당교수 : " + courseDetails.getProf() + 
                    "\n해당 과목 수강을 철회하시겠습니까?\n(수강을 철회하면 되돌릴 수 없습니다.)");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK)
            {
                try
                {
                    subjectManager.editXML("C:\\Users\\LeeSeungmin\\Documents\\NetBeansProjects\\", "course.xml", courseDetails.getId());
                    Alert subAlert = new Alert(Alert.AlertType.INFORMATION);
                    subAlert.setTitle("안내");
                    subAlert.setHeaderText("수강철회 메시지");
                    subAlert.setContentText("수강 철회 세부사항\n" + "과목명 : " + courseDetails.getName() + 
                            "\n수강일 : " + courseDetails.getDay() + 
                            "\n수강시간 : " + courseDetails.getTime() + 
                            "\n담당교수 : " + courseDetails.getProf() + 
                            "\n수강 철회가 완료되었습니다.");
                    Optional<ButtonType> rs = subAlert.showAndWait();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
            else if(result.get() == ButtonType.CANCEL)
            {
                Alert subAlert = new Alert(Alert.AlertType.INFORMATION);
                subAlert.setTitle("안내");
                subAlert.setHeaderText("수강철회 메시지");
                subAlert.setContentText("수강 철회가 취소되었습니다.");
                Optional<ButtonType> rs = subAlert.showAndWait();
            }
        }
        
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        initCancleTableView();
    }
    
    private void displayCancleStudentInfo()
    {
        studentManager = new XMLStudentManager();
        hm = new HashMap();
        
        try
        {
            hm = studentManager.readXML("C:\\Users\\LeeSeungmin\\Documents\\NetBeansProjects\\", "student.xml");
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        
        lblCancleId.setText(hm.get("id").toString().trim());
        lblCancleYear.setText(hm.get("year").toString().trim());
        lblCancleName.setText(hm.get("name").toString().trim());
        lblCancleSemester.setText(hm.get("semester").toString().trim());
    }
    
    private void initCancleTableView()
    {
        courseManager = new XMLCourseManager();
        data = FXCollections.observableArrayList();
        hm = new HashMap();
        
        try
        {
            hm = courseManager.readXML("C:\\Users\\LeeSeungmin\\Documents\\NetBeansProjects\\", "course.xml");
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        
        Iterator<String> iterator = hm.keySet().iterator();
        while(iterator.hasNext())
        {
            String key = iterator.next();
            temphm = (HashMap) hm.get(key);
            
            data.add(new CourseDetails(key, temphm.get("name").toString().trim(), 
                    temphm.get("day").toString().trim(), 
                    temphm.get("time").toString().trim(), 
                    temphm.get("prof").toString().trim()));
        }

        cancleName.setCellValueFactory(new PropertyValueFactory<>("name"));
        cancleDay.setCellValueFactory(new PropertyValueFactory<>("day"));
        cancleTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        cancleProf.setCellValueFactory(new PropertyValueFactory<>("prof"));
        
        tvCancle.setItems(null);
        tvCancle.setItems(data);
    }
    
}
