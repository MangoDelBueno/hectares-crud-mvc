import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class View extends JFrame {
    JTextField txtIdHect, txtCommunity, txtLatitude, txtLongitude;
    JComboBox cmbIsRented;
    JButton btnGet, btnClean, btnSave, btnUpdate, btnDelete, btnShowAll, btnClose;
    JDialog modal;
    DefaultTableModel modelHectares;
    public View(){
        super("CRUD HECTARIAS");
        generateInterface();
    }

    public void generateInterface() {
        setSize(750, 500);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JTextField txtBusqueda = new JTextField();
        JButton btnLimpiar = new JButton("Limpiar");
        Box box = Box.createVerticalBox();
        JPanel panelTxt = new JPanel();
        JPanel panelButtons = new JPanel();

        txtIdHect = new JTextField();
        txtCommunity = new JTextField();
        txtLatitude = new JTextField();
        txtLongitude = new JTextField();
        cmbIsRented = new JComboBox(new String[]{"No", "Si"});
        btnGet = new JButton("Recuperar");
        btnClean = new JButton("Limpiar");
        btnSave = new JButton("Grabar");
        btnUpdate = new JButton("Modificar");
        btnUpdate.setEnabled(false);
        btnDelete = new JButton("Borrar");
        btnDelete.setEnabled(false);
        btnShowAll = new JButton("Consultar todo");

        //btnGet.setBackground(new Color(125, 164, 222));

        JLabel title = new JLabel("CRUD HECTARIAS");
        title.setFont(new Font("Arial", Font.PLAIN, 32));

        Dimension preferredSize = new Dimension(200, 40); // Ancho, Alto
        txtIdHect.setPreferredSize(preferredSize);
        txtCommunity.setPreferredSize(preferredSize);
        txtLatitude.setPreferredSize(preferredSize);
        txtLongitude.setPreferredSize(preferredSize);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.005;
        gbc.insets = new Insets(40, 5, 20, 5);
        gbc.fill = GridBagConstraints.CENTER;
        add(title, gbc);

        panelTxt.setLayout(new GridBagLayout());

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 5, 0, 5);
        panelTxt.add(new JLabel("Ingrese el id de la Hectaria:"), gbc);
        gbc.gridy = 1;
        panelTxt.add(txtIdHect, gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;
        panelTxt.add(new JLabel("Ingrese la comunidad a la que pertenece la hectaria:"), gbc);
        gbc.gridy = 1;
        panelTxt.add(txtCommunity, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        panelTxt.add(new JLabel("Es rentada?"), gbc);
        gbc.gridy = 3;
        panelTxt.add(cmbIsRented, gbc);

        gbc.gridy = 2;
        gbc.gridx = 2;
        panelTxt.add(new JLabel("Ingrese la latitud:"), gbc);
        gbc.gridy = 3;
        panelTxt.add(txtLatitude, gbc);

        gbc.gridx = 4;
        gbc.gridy = 2;
        panelTxt.add(new JLabel("Ingrese la longuitud:"), gbc);
        gbc.gridy = 3;
        panelTxt.add(txtLongitude, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 2;
        gbc.insets = new Insets(10, 50, 10, 50);
        //panelTxt.setBackground(new Color(168, 19, 19));
        add(panelTxt, gbc);

        panelButtons.setLayout(new GridLayout(2, 3, 10, 10));
        panelButtons.add(btnGet);
        panelButtons.add(btnClean);
        panelButtons.add(btnSave);
        panelButtons.add(btnUpdate);
        panelButtons.add(btnDelete);
        panelButtons.add(btnShowAll);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 4;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.1;
        gbc.insets = new Insets(10, 50, 50, 50);
        gbc.fill = GridBagConstraints.BOTH;
        add(panelButtons, gbc);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        modal = new JDialog(this, "Hectarias", true);
        modal.setSize(600, 400);
        modal.setLayout(new BorderLayout());

        btnClose = new JButton("Close");
        modelHectares = new DefaultTableModel();
        modal.add(btnClose, BorderLayout.SOUTH);
    }

    public void modalHectares(ResultSet rs) {
        String[] columnNames = {"Id Hectaria", "Comunidad", "Renta", "Latitud", "Longuitud"};

        modelHectares.setRowCount(0);
        modelHectares.setColumnIdentifiers(columnNames);

        try{
            while (rs.next()){
                modelHectares.addRow(new Object[]{
                        rs.getInt("idHectare"),
                        rs.getString("community"),
                        rs.getBoolean("isRented") ? "Sí" : "No",
                        rs.getDouble("latitude"),
                        rs.getDouble("longitude")
                });
            }
        }catch (SQLException e){
            System.out.println("Aqui"+e.getMessage());
        }
        JTable table = new JTable(modelHectares);
        JScrollPane scrollPane = new JScrollPane(table);
        modal.add(scrollPane, BorderLayout.CENTER);

        modal.setLocationRelativeTo(this);
        modal.setVisible(true);
    }

    public void closeModal(){
        modal.dispose();
    }

    public void setController(Controller controller){
        cmbIsRented.addActionListener(controller);
        btnSave.addActionListener(controller);
        btnDelete.addActionListener(controller);
        btnUpdate.addActionListener(controller);
        btnGet.addActionListener(controller);
        btnClean.addActionListener(controller);
        btnShowAll.addActionListener(controller);
        btnClose.addActionListener(controller);
    }

    public boolean isNumeric(String txt) {
        try {
            Integer.parseInt(txt);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isDecimal(String txt) {
        try {
            Double.parseDouble(txt);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isValidLength(String txt, int maxLength) {
        if (txt == null) {
            return false;
        }
        return txt.length() <= maxLength;
    }

    public boolean isValidRange(String txt, int minNum, int maxNum){
        double numero = Double.parseDouble(txt);
        if (numero >= minNum && numero <= maxNum){
            return true;
        }
        return false;
    }

    public boolean isTxtNotEmpty(){
        if (txtIdHect.getText().isEmpty() || txtCommunity.getText().isEmpty() || txtLatitude.getText().isEmpty() || txtLongitude.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No se aceptan vacios", "Vacios", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        return true;
    }


    public boolean validationBtnSave(){
        if(!isTxtNotEmpty()){
            return false;
        }else if (!isValidLength(txtIdHect.getText().trim(),10)) {
            JOptionPane.showMessageDialog(null, "La cantidad máxima de caracteres en el ID es 10", "Cantidad maxima de caracteres", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }else if (!isNumeric(txtIdHect.getText())) {
            JOptionPane.showMessageDialog(null, "El ID solo acepta datos numéricos", "Datos numéricos", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }else if (!isValidLength(txtCommunity.getText().trim(),255)) {
            JOptionPane.showMessageDialog(null, "La cantidad máxima de caracteres en Comunidad es 255", "Cantidad maxima de caracteres", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }else if(!isDecimal(txtLatitude.getText())) {
            JOptionPane.showMessageDialog(null, "Solo datos numéricos para Latitud", "Datos numéricos", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }else if(!isValidRange(txtLatitude.getText(),-90,90)){
            JOptionPane.showMessageDialog(null, "La latitud está fuera del rango permitido.", "Rango de Latitud", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }else if(!isDecimal(txtLongitude.getText())) {
            JOptionPane.showMessageDialog(null, "Solo datos numéricos para Longitud", "Datos numéricos", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }else if(!isValidRange(txtLongitude.getText(),-180,180)){
            JOptionPane.showMessageDialog(null, "La longitud está fuera del rango permitido.", "Rango de Longitud", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }else{
            return true;
        }
    }

    public Hectare getHectare(){
        if (!validationBtnSave()) {
            return null;
        }
        int idHectare = Integer.parseInt(txtIdHect.getText());
        String community = txtCommunity.getText();
        boolean isRented = cmbIsRented.getSelectedIndex() == 1 ? true : false;
        double latitude = Double.parseDouble(txtLatitude.getText());
        double longitude = Double.parseDouble(txtLongitude.getText());
        return new Hectare(idHectare, community, isRented, latitude, longitude);
    }

    public void setHectare(Hectare hectare){
        if (hectare == null) {
            JOptionPane.showMessageDialog(null, "La hectaria a la que quiere acceder no existe.", "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        setCommunity(hectare.getCommunity());
        setIsRented(hectare.isRented());
        setLatitude(hectare.getLatitude());
        setLongitude(hectare.getLongitude());
        btnSave.setEnabled(false);
        btnUpdate.setEnabled(true);
        btnDelete.setEnabled(true);
    }

    public int confirmDelete(){
        return JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea continuar?", "Confirmación", JOptionPane.YES_NO_OPTION);
    }

    public void showErrorMessage(){
        JOptionPane.showMessageDialog(null, "Ingrese otro ID para grabar.", "ID Existente", JOptionPane.INFORMATION_MESSAGE);
    }

    public void cleanTxt(){
        txtIdHect.setText("");
        txtCommunity.setText("");
        setIsRented(false);
        txtLatitude.setText("");
        txtLongitude.setText("");
        btnSave.setEnabled(true);
        btnDelete.setEnabled(false);
        btnUpdate.setEnabled(false);
    }

    public Integer getIdHect() {
        return txtIdHect.getText().isEmpty() ? -1: Integer.parseInt(txtIdHect.getText());
    }

    public void setCommunity(String community) {
        this.txtCommunity.setText(community);
    }

    public void setLatitude(double latitude) {
        this.txtLatitude.setText(String.valueOf(latitude));
    }

    public void setLongitude(double longitude) {
        this.txtLongitude.setText(String.valueOf(longitude));
    }

    public void setIsRented(boolean isRented) {
        if (isRented){
            cmbIsRented.setSelectedItem("Si");
            return;
        }
        cmbIsRented.setSelectedItem("No");
    }

}
