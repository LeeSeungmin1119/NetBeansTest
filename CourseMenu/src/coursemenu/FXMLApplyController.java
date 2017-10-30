/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coursemenu;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author LeeSeungmin
 */
public class FXMLApplyController implements Initializable 
{

    @FXML
    private TableView<CourseDetails> tvApply;
    @FXML
    private TableColumn<CourseDetails, String> applyName;
    @FXML
    private TableColumn<CourseDetails, String> applyDay;
    @FXML
    private TableColumn<CourseDetails, String> applyTime;
    @FXML
    private TableColumn<CourseDetails, String> applyProf;
    @FXML
    private Button btnApply;
    @FXML
    private Button btnMap;
    @FXML
    private Label lblApplyId;
    @FXML
    private Label lblApplyYear;
    @FXML
    private Label lblApplyName;
    @FXML
    private Label lblApplySemester;
    
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
        displayApplyStudentInfo();
        initApplyTableView();
    }    

    @FXML
    private void btnApplyClick(ActionEvent event) 
    {
        courseManager = new XMLCourseManager();
        hm = new HashMap();
        HashMap courseApplyList = new HashMap();
        
        try
        {
            courseApplyList = courseManager.readXML("C:\\Users\\LeeSeungmin\\Documents\\NetBeansProjects\\", "course.xml");
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        
        CourseDetails courseDetails = tvApply.getSelectionModel().getSelectedItem();
        hm.put("id", courseDetails.getId());
        hm.put("name", courseDetails.getName());
        hm.put("day", courseDetails.getDay());
        hm.put("time", courseDetails.getTime());
        hm.put("prof", courseDetails.getProf());
        
        
        if(courseApplyList == null || courseApplyList.get(courseDetails.getId()) == null)
        {
            try
            {
                courseManager.editXML("C:\\Users\\LeeSeungmin\\Documents\\NetBeansProjects\\", "course.xml", hm);
            
                Alert.AlertType AlterType = null;
                Alert alert = new Alert(AlterType.INFORMATION);
                alert.setTitle("안내");
                alert.setHeaderText("수강신청 메시지");
                alert.setContentText("수강 신청 세부사항\n" + "과목명 : " + hm.get("name").toString().trim() 
                        + "\n수강일 : " + hm.get("day").toString().trim() 
                        + "\n수강시간 : " + hm.get("time").toString().trim() 
                        + "\n담당교수 : " + hm.get("prof").toString().trim());
                alert.showAndWait().ifPresent(rs ->
                {
                    if(rs == ButtonType.OK)
                    {
                        alert.close();
                    }
                });
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            Alert.AlertType AlterType = null;
            Alert alert = new Alert(AlterType.ERROR);
            alert.setTitle("안내");
            alert.setHeaderText("수강신청 오류 메시지");
            alert.setContentText("이미 신청한 과목입니다.\n" + "과목명 : " + hm.get("name").toString().trim() 
                    + "\n수강일 : " + hm.get("day").toString().trim() 
                    + "\n수강시간 : " + hm.get("time").toString().trim() 
                    + "\n담당교수 : " + hm.get("prof").toString().trim());
            alert.showAndWait().ifPresent(rs ->
            {
                if(rs == ButtonType.OK)
                {
                    alert.close();
                }
            });
        }
    }

    @FXML
    private void btnMapClick(ActionEvent event) throws IOException 
    {
        Stage stage = new Stage();
        
        Parent root = FXMLLoader.load(getClass().getResource("FXMLWebView.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }
    
    private void displayApplyStudentInfo()
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
        
        lblApplyId.setText(hm.get("id").toString().trim());
        lblApplyYear.setText(hm.get("year").toString().trim());
        lblApplyName.setText(hm.get("name").toString().trim());
        lblApplySemester.setText(hm.get("semester").toString().trim());
        
        courseManager = new XMLCourseManager();
    }
    
    private void initApplyTableView()
    {
        subjectManager = new XMLSubjectManager();
        data = FXCollections.observableArrayList();
        hm = new HashMap();
        
        try
        {
            hm = subjectManager.readXML("C:\\Users\\LeeSeungmin\\Documents\\NetBeansProjects\\", "subject.xml");
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
            
            data.add(new CourseDetails(key, temphm.get("name").toString().trim(), temphm.get("day").toString().trim(), temphm.get("time").toString().trim(), temphm.get("prof").toString().trim()));
        }

        applyName.setCellValueFactory(new PropertyValueFactory<>("name"));
        applyDay.setCellValueFactory(new PropertyValueFactory<>("day"));
        applyTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        applyProf.setCellValueFactory(new PropertyValueFactory<>("prof"));
        
        tvApply.setItems(null);
        tvApply.setItems(data);
    }    
}
