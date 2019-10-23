import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JTextAreaUppgift extends JFrame implements ActionListener {
    JButton openFile = new JButton("Open");
    JButton saveFile = new JButton("Save");
    JButton printFile = new JButton("Print");
    JButton closeApplication = new JButton("Exit");
    List<String> list=new ArrayList<>();
    JLabel FilePath = new JLabel("File namn");


    JTextArea textArea = new JTextArea(30,45);
    JScrollPane scrollPane = new JScrollPane(textArea);

    JPanel menuPanel = new JPanel();
    JPanel textfield = new JPanel();

    JComboBox<String> box = new JComboBox<>();


    JTextAreaUppgift(){
        FillTheBox();
        setLayout(new BorderLayout());
        add(menuPanel,BorderLayout.NORTH);
        add(textfield,BorderLayout.CENTER);

        box.setEditable(true);
        box.setSelectedIndex(-1);
        menuPanel.add(FilePath);
        menuPanel.add(box);

        menuPanel.add(openFile);
        menuPanel.add(saveFile);
        menuPanel.add(printFile);
        menuPanel.add(closeApplication);
        closeApplication.addActionListener(this);
        saveFile.addActionListener(this);
        openFile.addActionListener(this);
        printFile.addActionListener(this);
        textfield.add(scrollPane);
        textArea.setLineWrap(true);


        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==saveFile) {
            try (PrintWriter save = new PrintWriter(new BufferedWriter(new FileWriter(box.getSelectedItem()+".txt",true)))) {
                    textArea.write(save);
                    box.addItem(box.getSelectedItem().toString());
                System.out.println(box.getSelectedItem().toString());

            } catch (IOException i) {
                i.printStackTrace();
            }
        } else if(e.getSource() == printFile) {
            try {
                textArea.print();
            } catch (PrinterException i) {
                i.printStackTrace();
            }
        }else if(e.getSource() == openFile){
            try(BufferedReader reader = new BufferedReader(new FileReader(box.getSelectedItem().toString()))){
                textArea.read(reader,null);


            }catch (IOException i){
               JOptionPane.showMessageDialog(null,"filen finns inte");
            }
        } else if (e.getSource() == closeApplication){
            System.exit(0);

        }

    }

    public void FillTheBox(){
        Path p = Paths.get("C:\\Users\\atefs\\OneDrive\\Skrivbord\\Java lektioner\\Objektorientering kurs2\\Lektion 15");
        try(DirectoryStream<Path> atef = Files.newDirectoryStream(p)) {
            for(Path t:atef){
                String a=t.getFileName().toString();
                System.out.println(a);
                if(a.contains(".txt")){

                    box.addItem(a);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
