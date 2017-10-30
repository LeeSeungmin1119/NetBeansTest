/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coursemenu;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

/**
 *
 * @author LeeSeungmin
 */
public class FXMLDocumentController implements Initializable 
{
    
    @FXML
    private MenuItem menuApply;
    @FXML
    private MenuItem menuCancle;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        // TODO
    }    

    @FXML
    private void menuApplyClick(ActionEvent event) throws IOException 
    {
        Stage stage = new Stage();
        
        Parent root = FXMLLoader.load(getClass().getResource("FXMLApply.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void menuCancleClick(ActionEvent event) throws IOException 
    {
        Stage stage = new Stage();
        
        Parent root = FXMLLoader.load(getClass().getResource("FXMLCancle.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }
    
}
