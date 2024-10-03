import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener {
    private Model model;
    private View view;

    public Controller(Model model, View view){
        this.model = model;
        this.view = view;
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == view.btnGet){
            view.setHectare(model.getHectareById(view.getIdHect()));
        }
        if (actionEvent.getSource() == view.btnSave){
            if(!model.save(view.getHectare())){
                view.showErrorMessage();
            }
            view.cleanTxt();
        }
        if (actionEvent.getSource() == view.btnUpdate){
            model.upadatehectare(view.getHectare());
            view.cleanTxt();
        }
        if (actionEvent.getSource() == view.btnDelete){
            if (view.confirmDelete() == JOptionPane.YES_OPTION) {
                model.deleteHectare(view.getHectare());
                view.cleanTxt();
            }
        }
        if (actionEvent.getSource() == view.btnClean){
            view.cleanTxt();
        }
        if (actionEvent.getSource() == view.btnShowAll){
            view.modalHectares(model.getAllHectares());
        }
        if (actionEvent.getSource() == view.btnClose){
            view.closeModal();
        }
    }
}
