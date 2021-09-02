/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ravc.simpleimport.views;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.intellijthemes.FlatOneDarkIJTheme;
import static com.ravc.simpleimport.SimpleImport.SIProp;
import com.ravc.simpleimport.controllers.ControllerData;
import com.ravc.simpleimport.models.ModelCidade;
import com.ravc.simpleimport.models.ModelClassFiscal;
import com.ravc.simpleimport.models.ModelEmpresa;
import com.ravc.simpleimport.models.ModelSitTrib;
import com.ravc.simpleimport.models.ModelUnMed;
import com.ravc.simpleimport.utils.Database;
import com.ravc.simpleimport.utils.FileTypeFilter;
import com.ravc.simpleimport.utils.SIConst;
import com.ravc.simpleimport.utils.TextAreaAppender;
import com.ravc.simpleimport.utils.UnMedEnum;
import com.ravc.simpleimport.utils.Util;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.PlainDocument;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author Rodrigo
 */
public class Main extends javax.swing.JFrame {

    final static Logger logger = Logger.getLogger("");
    List<String> excelCols = null;
    List<ModelCidade> cidades = new ArrayList<>();
    List<ModelUnMed> unidades = new ArrayList<>();
    List<ModelSitTrib> sitTribs = new ArrayList<>();
    List<ModelClassFiscal> classfiscals = new ArrayList<>();

    /**
     * Creates new form Main
     */
    public Main() {
        initComponents();
        logger.setLevel(Level.INFO);

        DefaultCaret caret = (DefaultCaret) jtaLogs.getCaret(); // ←
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        TextAreaAppender appender = new TextAreaAppender(jtaLogs);
        Logger.getRootLogger().addAppender(appender);

        setIconImage(SIConst.ICON_SYSTEM);

        String theme = SIProp.prop().getString("tema", "Dark");
        if (theme.equals("Dark")) {
            jrbmDark.setSelected(true);
            jrbmLight.setSelected(false);
        } else if (theme.equals("Light")) {
            jrbmDark.setSelected(false);
            jrbmLight.setSelected(true);
        }

        jtfClientesNome.setVisible(false);
        jtfClientesFantasia.setVisible(false);
        jtfClientesTelefone.setVisible(false);
        jtfClientesCidade.setVisible(false);
        jlInfoCidade.setVisible(false);
        jtfClientesBairro.setVisible(false);
        jtfClientesCPFCNPJ.setVisible(false);
        jtfClientesIEIM.setVisible(false);
        jtfClientesEndereco.setVisible(false);
        jtfClientesNumero.setVisible(false);
        jtfClientesComplemento.setVisible(false);
        jtfClientesCelular.setVisible(false);
        jtfClientesDtNasc.setVisible(false);
        jtfFormatDateNasc.setVisible(false);
        jlFormat.setVisible(false);
        jbInfo.setVisible(false);
        jtfClientesContato.setVisible(false);
        jtfClientesEmail.setVisible(false);
        jtfClientesCEP.setVisible(false);

        jtfClientesNome.setDocument(new UpperCase());
        jtfClientesFantasia.setDocument(new UpperCase());
        jtfClientesTelefone.setDocument(new UpperCase());
        jtfClientesCidade.setDocument(new UpperCase());
        jtfClientesBairro.setDocument(new UpperCase());
        jtfClientesCPFCNPJ.setDocument(new UpperCase());
        jtfClientesIEIM.setDocument(new UpperCase());
        jtfClientesEndereco.setDocument(new UpperCase());
        jtfClientesNumero.setDocument(new UpperCase());
        jtfClientesComplemento.setDocument(new UpperCase());
        jtfClientesCelular.setDocument(new UpperCase());
        jtfClientesDtNasc.setDocument(new UpperCase());
        jtfClientesContato.setDocument(new UpperCase());
        jtfClientesEmail.setDocument(new UpperCase());
        jtfClientesCEP.setDocument(new UpperCase());

        jtfProdutosCodBarras.setVisible(false);
        jtfProdutosDescricao.setVisible(false);
        jtfProdutosUnMed.setVisible(false);
        jtfProdutosMarca.setVisible(false);
        jtfProdutosGrupo.setVisible(false);
        jtfProdutosEstMin.setVisible(false);
        jtfProdutosEstAtual.setVisible(false);
        jtfProdutosVlrCusto.setVisible(false);
        jtfProdutosVlrVenda.setVisible(false);
        jtfProdutosRef.setVisible(false);
        jtfProdutosClassFiscal.setVisible(false);
        jtfProdutosSitTrib.setVisible(false);
        jtfProdutosCFOP.setVisible(false);

        jtfProdutosCodBarras.setDocument(new UpperCase());
        jtfProdutosDescricao.setDocument(new UpperCase());
        jtfProdutosUnMed.setDocument(new UpperCase());
        jtfProdutosMarca.setDocument(new UpperCase());
        jtfProdutosGrupo.setDocument(new UpperCase());
        jtfProdutosEstMin.setDocument(new UpperCase());
        jtfProdutosEstAtual.setDocument(new UpperCase());
        jtfProdutosVlrCusto.setDocument(new UpperCase());
        jtfProdutosVlrVenda.setDocument(new UpperCase());
        jtfProdutosRef.setDocument(new UpperCase());
        jtfProdutosClassFiscal.setDocument(new UpperCase());
        jtfProdutosSitTrib.setDocument(new UpperCase());
        jtfProdutosCFOP.setDocument(new UpperCase());

        jbInfo.putClientProperty("JButton.buttonType", "roundRect");

        excelCols = new ArrayList<>();
        excelCols.add("A");
        excelCols.add("B");
        excelCols.add("C");
        excelCols.add("D");
        excelCols.add("E");
        excelCols.add("F");
        excelCols.add("G");
        excelCols.add("H");
        excelCols.add("I");
        excelCols.add("J");
        excelCols.add("K");
        excelCols.add("L");
        excelCols.add("M");
        excelCols.add("N");
        excelCols.add("O");
        excelCols.add("P");
        excelCols.add("Q");
        excelCols.add("R");
        excelCols.add("S");
        excelCols.add("T");
        excelCols.add("U");
        excelCols.add("V");
        excelCols.add("W");
        excelCols.add("X");
        excelCols.add("Y");
        excelCols.add("Z");

        excelCols.add("AA");
        excelCols.add("AB");
        excelCols.add("AC");
        excelCols.add("AD");
        excelCols.add("AE");
        excelCols.add("AF");
        excelCols.add("AG");
        excelCols.add("AH");
        excelCols.add("AI");
        excelCols.add("AJ");
        excelCols.add("AK");
        excelCols.add("AL");
        excelCols.add("AM");
        excelCols.add("AN");
        excelCols.add("AO");
        excelCols.add("AP");
        excelCols.add("AQ");
        excelCols.add("AR");
        excelCols.add("AS");
        excelCols.add("AT");
        excelCols.add("AU");
        excelCols.add("AV");
        excelCols.add("AW");
        excelCols.add("AX");
        excelCols.add("AY");
        excelCols.add("AZ");

        excelCols.add("BA");
        excelCols.add("BB");
        excelCols.add("BC");
        excelCols.add("BD");
        excelCols.add("BE");
        excelCols.add("BF");
        excelCols.add("BG");
        excelCols.add("BH");
        excelCols.add("BI");
        excelCols.add("BJ");
        excelCols.add("BK");
        excelCols.add("BL");
        excelCols.add("BM");
        excelCols.add("BN");
        excelCols.add("BO");
        excelCols.add("BP");
        excelCols.add("BQ");
        excelCols.add("BR");
        excelCols.add("BS");
        excelCols.add("BT");
        excelCols.add("BU");
        excelCols.add("BV");
        excelCols.add("BW");
        excelCols.add("BX");
        excelCols.add("BY");
        excelCols.add("BZ");

        excelCols.add("CA");
        excelCols.add("CB");
        excelCols.add("CC");
        excelCols.add("CD");
        excelCols.add("CE");
        excelCols.add("CF");
        excelCols.add("CG");
        excelCols.add("CH");
        excelCols.add("CI");
        excelCols.add("CJ");
        excelCols.add("CK");
        excelCols.add("CL");
        excelCols.add("CM");
        excelCols.add("CN");
        excelCols.add("CO");
        excelCols.add("CP");
        excelCols.add("CQ");
        excelCols.add("CR");
        excelCols.add("CS");
        excelCols.add("CT");
        excelCols.add("CU");
        excelCols.add("CV");
        excelCols.add("CW");
        excelCols.add("CX");
        excelCols.add("CY");
        excelCols.add("CZ");

        logger.info("########################");
        logger.info("### Aplicação inciada! ###");

        getEmpresas();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jpLog = new javax.swing.JPanel();
        jpbProgress = new javax.swing.JProgressBar();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtaLogs = new javax.swing.JTextArea();
        jpFields = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jcbEmpresa = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jtfPathClientsFile = new javax.swing.JTextField();
        jbSearchFileClients = new javax.swing.JButton();
        jbImportClients = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jtfClientesIEIM = new javax.swing.JTextField();
        jtfClientesCPFCNPJ = new javax.swing.JTextField();
        jtfClientesCelular = new javax.swing.JTextField();
        jtfClientesContato = new javax.swing.JTextField();
        jtfClientesDtNasc = new javax.swing.JTextField();
        jcbClientesDtNasc = new javax.swing.JCheckBox();
        jcbClientesEmail = new javax.swing.JCheckBox();
        jtfClientesEmail = new javax.swing.JTextField();
        jtfClientesFantasia = new javax.swing.JTextField();
        jcbClientesCPFCNPJ = new javax.swing.JCheckBox();
        jcbClientesFantasia = new javax.swing.JCheckBox();
        jcbClientesIEIM = new javax.swing.JCheckBox();
        jcbClientesCelular = new javax.swing.JCheckBox();
        jcbClientesTelefone = new javax.swing.JCheckBox();
        jtfClientesTelefone = new javax.swing.JTextField();
        jcbClientesNome = new javax.swing.JCheckBox();
        jtfClientesNome = new javax.swing.JTextField();
        jcbClientesContato = new javax.swing.JCheckBox();
        jtfClientesEndereco = new javax.swing.JTextField();
        jtfClientesNumero = new javax.swing.JTextField();
        jcbClientesBairro = new javax.swing.JCheckBox();
        jtfClientesBairro = new javax.swing.JTextField();
        jcbClientesCidade = new javax.swing.JCheckBox();
        jtfClientesCidade = new javax.swing.JTextField();
        jcbClientesCEP = new javax.swing.JCheckBox();
        jtfClientesCEP = new javax.swing.JTextField();
        jcbClientesComplemento = new javax.swing.JCheckBox();
        jcbClientesEndereco = new javax.swing.JCheckBox();
        jtfClientesComplemento = new javax.swing.JTextField();
        jcbClientesNumero = new javax.swing.JCheckBox();
        jcbHeaderExistsClient = new javax.swing.JCheckBox();
        jtfFormatDateNasc = new javax.swing.JTextField();
        jlFormat = new javax.swing.JLabel();
        jbInfo = new javax.swing.JButton();
        jlInfoCidade = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jtfPathProductsFile = new javax.swing.JTextField();
        jbSeachFileProducts = new javax.swing.JButton();
        jbImportProducts = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jcbProdutosCodBarras = new javax.swing.JCheckBox();
        jtfProdutosCodBarras = new javax.swing.JTextField();
        jcbProdutosDescricao = new javax.swing.JCheckBox();
        jtfProdutosDescricao = new javax.swing.JTextField();
        jcbProdutosRef = new javax.swing.JCheckBox();
        jtfProdutosRef = new javax.swing.JTextField();
        jcbProdutosUnMed = new javax.swing.JCheckBox();
        jtfProdutosUnMed = new javax.swing.JTextField();
        jcbProdutosEstMin = new javax.swing.JCheckBox();
        jtfProdutosEstMin = new javax.swing.JTextField();
        jtfProdutosEstAtual = new javax.swing.JTextField();
        jcbProdutosEstAtual = new javax.swing.JCheckBox();
        jtfProdutosVlrVenda = new javax.swing.JTextField();
        jtfProdutosVlrCusto = new javax.swing.JTextField();
        jcbProdutosVlrCusto = new javax.swing.JCheckBox();
        jcbProdutosVlrVenda = new javax.swing.JCheckBox();
        jtfProdutosMarca = new javax.swing.JTextField();
        jcbProdutosMarca = new javax.swing.JCheckBox();
        jcbProdutosGrupo = new javax.swing.JCheckBox();
        jtfProdutosGrupo = new javax.swing.JTextField();
        jcbProdutosSitTrib = new javax.swing.JCheckBox();
        jtfProdutosSitTrib = new javax.swing.JTextField();
        jcbProdutosCFOP = new javax.swing.JCheckBox();
        jtfProdutosCFOP = new javax.swing.JTextField();
        jtfProdutosClassFiscal = new javax.swing.JTextField();
        jcbProdutosClassFiscal = new javax.swing.JCheckBox();
        jcbHeaderExistsProducts = new javax.swing.JCheckBox();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jrbmDark = new javax.swing.JRadioButtonMenuItem();
        jrbmLight = new javax.swing.JRadioButtonMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jmiExit = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Simple Import");
        setMaximumSize(null);
        setMinimumSize(new java.awt.Dimension(1124, 524));
        setPreferredSize(new java.awt.Dimension(1124, 524));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 1, 1, 1));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jpLog.setLayout(new java.awt.BorderLayout());

        jpbProgress.setPreferredSize(new java.awt.Dimension(146, 15));
        jpbProgress.setString("");
        jpbProgress.setStringPainted(true);
        jpLog.add(jpbProgress, java.awt.BorderLayout.SOUTH);

        jScrollPane2.setBorder(null);

        jtaLogs.setEditable(false);
        jtaLogs.setColumns(20);
        jtaLogs.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jtaLogs.setRows(5);
        jtaLogs.setBorder(null);
        jtaLogs.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jtaLogs.setFocusable(false);
        jtaLogs.setRequestFocusEnabled(false);
        jScrollPane2.setViewportView(jtaLogs);

        jpLog.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel1.add(jpLog, java.awt.BorderLayout.CENTER);

        jpFields.setPreferredSize(new java.awt.Dimension(580, 389));
        jpFields.setLayout(new java.awt.BorderLayout());

        jPanel5.setPreferredSize(new java.awt.Dimension(580, 60));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Empresa:");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jcbEmpresa, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 505, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jcbEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jpFields.add(jPanel5, java.awt.BorderLayout.NORTH);

        jLabel1.setText("Caminho do arquivo (csv/txt separado por ';'):");

        jbSearchFileClients.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8_search_20px.png"))); // NOI18N
        jbSearchFileClients.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbSearchFileClients.setMaximumSize(new java.awt.Dimension(45, 30));
        jbSearchFileClients.setMinimumSize(new java.awt.Dimension(45, 30));
        jbSearchFileClients.setPreferredSize(new java.awt.Dimension(45, 30));
        jbSearchFileClients.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSearchFileClientsActionPerformed(evt);
            }
        });

        jbImportClients.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8_ssd_20px.png"))); // NOI18N
        jbImportClients.setText("Importar");
        jbImportClients.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbImportClients.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbImportClientsActionPerformed(evt);
            }
        });

        jPanel4.setLayout(null);

        jtfClientesIEIM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfClientesIEIMKeyTyped(evt);
            }
        });
        jPanel4.add(jtfClientesIEIM);
        jtfClientesIEIM.setBounds(300, 80, 40, 23);

        jtfClientesCPFCNPJ.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfClientesCPFCNPJKeyTyped(evt);
            }
        });
        jPanel4.add(jtfClientesCPFCNPJ);
        jtfClientesCPFCNPJ.setBounds(120, 80, 40, 23);

        jtfClientesCelular.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfClientesCelularKeyTyped(evt);
            }
        });
        jPanel4.add(jtfClientesCelular);
        jtfClientesCelular.setBounds(480, 110, 40, 23);

        jtfClientesContato.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfClientesContatoKeyTyped(evt);
            }
        });
        jPanel4.add(jtfClientesContato);
        jtfClientesContato.setBounds(480, 50, 40, 23);

        jtfClientesDtNasc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfClientesDtNascKeyTyped(evt);
            }
        });
        jPanel4.add(jtfClientesDtNasc);
        jtfClientesDtNasc.setBounds(120, 110, 40, 23);

        jcbClientesDtNasc.setText("Data Nasc.");
        jcbClientesDtNasc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbClientesDtNascActionPerformed(evt);
            }
        });
        jPanel4.add(jcbClientesDtNasc);
        jcbClientesDtNasc.setBounds(10, 110, 100, 23);

        jcbClientesEmail.setText("E-mail");
        jcbClientesEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbClientesEmailActionPerformed(evt);
            }
        });
        jPanel4.add(jcbClientesEmail);
        jcbClientesEmail.setBounds(190, 110, 103, 23);

        jtfClientesEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfClientesEmailKeyTyped(evt);
            }
        });
        jPanel4.add(jtfClientesEmail);
        jtfClientesEmail.setBounds(300, 110, 40, 23);

        jtfClientesFantasia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfClientesFantasiaKeyTyped(evt);
            }
        });
        jPanel4.add(jtfClientesFantasia);
        jtfClientesFantasia.setBounds(300, 50, 40, 23);

        jcbClientesCPFCNPJ.setText("CPF/CNPJ");
        jcbClientesCPFCNPJ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbClientesCPFCNPJActionPerformed(evt);
            }
        });
        jPanel4.add(jcbClientesCPFCNPJ);
        jcbClientesCPFCNPJ.setBounds(10, 80, 110, 23);

        jcbClientesFantasia.setText("Fantasia");
        jcbClientesFantasia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbClientesFantasiaActionPerformed(evt);
            }
        });
        jPanel4.add(jcbClientesFantasia);
        jcbClientesFantasia.setBounds(190, 50, 100, 23);

        jcbClientesIEIM.setText("Inscrição Est.");
        jcbClientesIEIM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbClientesIEIMActionPerformed(evt);
            }
        });
        jPanel4.add(jcbClientesIEIM);
        jcbClientesIEIM.setBounds(190, 80, 100, 23);

        jcbClientesCelular.setText("Telefone 2");
        jcbClientesCelular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbClientesCelularActionPerformed(evt);
            }
        });
        jPanel4.add(jcbClientesCelular);
        jcbClientesCelular.setBounds(370, 110, 100, 23);

        jcbClientesTelefone.setText("Telefone 1");
        jcbClientesTelefone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbClientesTelefoneActionPerformed(evt);
            }
        });
        jPanel4.add(jcbClientesTelefone);
        jcbClientesTelefone.setBounds(370, 80, 100, 23);

        jtfClientesTelefone.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfClientesTelefoneKeyTyped(evt);
            }
        });
        jPanel4.add(jtfClientesTelefone);
        jtfClientesTelefone.setBounds(480, 80, 40, 23);

        jcbClientesNome.setText("Nome");
        jcbClientesNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbClientesNomeActionPerformed(evt);
            }
        });
        jPanel4.add(jcbClientesNome);
        jcbClientesNome.setBounds(10, 50, 100, 23);

        jtfClientesNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtfClientesNomeKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfClientesNomeKeyTyped(evt);
            }
        });
        jPanel4.add(jtfClientesNome);
        jtfClientesNome.setBounds(120, 50, 40, 23);

        jcbClientesContato.setText("Contato");
        jcbClientesContato.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbClientesContatoActionPerformed(evt);
            }
        });
        jPanel4.add(jcbClientesContato);
        jcbClientesContato.setBounds(370, 50, 100, 23);

        jtfClientesEndereco.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfClientesEnderecoKeyTyped(evt);
            }
        });
        jPanel4.add(jtfClientesEndereco);
        jtfClientesEndereco.setBounds(120, 180, 40, 23);

        jtfClientesNumero.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfClientesNumeroKeyTyped(evt);
            }
        });
        jPanel4.add(jtfClientesNumero);
        jtfClientesNumero.setBounds(300, 180, 40, 23);

        jcbClientesBairro.setText("Bairro");
        jcbClientesBairro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbClientesBairroActionPerformed(evt);
            }
        });
        jPanel4.add(jcbClientesBairro);
        jcbClientesBairro.setBounds(370, 180, 100, 23);

        jtfClientesBairro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfClientesBairroActionPerformed(evt);
            }
        });
        jtfClientesBairro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfClientesBairroKeyTyped(evt);
            }
        });
        jPanel4.add(jtfClientesBairro);
        jtfClientesBairro.setBounds(480, 180, 40, 22);

        jcbClientesCidade.setText("Cidade");
        jcbClientesCidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbClientesCidadeActionPerformed(evt);
            }
        });
        jPanel4.add(jcbClientesCidade);
        jcbClientesCidade.setBounds(370, 210, 103, 23);

        jtfClientesCidade.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfClientesCidadeKeyTyped(evt);
            }
        });
        jPanel4.add(jtfClientesCidade);
        jtfClientesCidade.setBounds(480, 210, 40, 23);

        jcbClientesCEP.setText("CEP");
        jcbClientesCEP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbClientesCEPActionPerformed(evt);
            }
        });
        jPanel4.add(jcbClientesCEP);
        jcbClientesCEP.setBounds(190, 210, 103, 23);

        jtfClientesCEP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfClientesCEPKeyTyped(evt);
            }
        });
        jPanel4.add(jtfClientesCEP);
        jtfClientesCEP.setBounds(300, 210, 40, 23);

        jcbClientesComplemento.setText("Complemento");
        jcbClientesComplemento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbClientesComplementoActionPerformed(evt);
            }
        });
        jPanel4.add(jcbClientesComplemento);
        jcbClientesComplemento.setBounds(10, 210, 100, 23);

        jcbClientesEndereco.setText("Endereço");
        jcbClientesEndereco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbClientesEnderecoActionPerformed(evt);
            }
        });
        jPanel4.add(jcbClientesEndereco);
        jcbClientesEndereco.setBounds(10, 180, 90, 23);

        jtfClientesComplemento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfClientesComplementoKeyTyped(evt);
            }
        });
        jPanel4.add(jtfClientesComplemento);
        jtfClientesComplemento.setBounds(120, 210, 40, 23);

        jcbClientesNumero.setText("Numero");
        jcbClientesNumero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbClientesNumeroActionPerformed(evt);
            }
        });
        jPanel4.add(jcbClientesNumero);
        jcbClientesNumero.setBounds(190, 180, 100, 23);

        jcbHeaderExistsClient.setText("Meu arquivo contém cabeçalho.");
        jPanel4.add(jcbHeaderExistsClient);
        jcbHeaderExistsClient.setBounds(10, 10, 270, 23);

        jtfFormatDateNasc.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfFormatDateNasc.setText("dd/MM/yyyy");
        jPanel4.add(jtfFormatDateNasc);
        jtfFormatDateNasc.setBounds(70, 140, 110, 30);

        jlFormat.setText("Formato:");
        jPanel4.add(jlFormat);
        jlFormat.setBounds(20, 140, 50, 30);

        jbInfo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8_info_15px.png"))); // NOI18N
        jbInfo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbInfoActionPerformed(evt);
            }
        });
        jPanel4.add(jbInfo);
        jbInfo.setBounds(180, 140, 30, 30);

        jlInfoCidade.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlInfoCidade.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8_info_15px.png"))); // NOI18N
        jlInfoCidade.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jlInfoCidade.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlInfoCidadeMouseClicked(evt);
            }
        });
        jPanel4.add(jlInfoCidade);
        jlInfoCidade.setBounds(520, 210, 20, 20);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jbImportClients))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 333, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jtfPathClientsFile)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbSearchFileClients, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbSearchFileClients, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfPathClientsFile, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbImportClients)
                .addContainerGap(47, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Clientes", jPanel2);

        jLabel2.setText("Caminho do arquivo (csv/txt separado por ';'):");

        jbSeachFileProducts.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8_search_20px.png"))); // NOI18N
        jbSeachFileProducts.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbSeachFileProducts.setMaximumSize(new java.awt.Dimension(45, 30));
        jbSeachFileProducts.setMinimumSize(new java.awt.Dimension(45, 30));
        jbSeachFileProducts.setPreferredSize(new java.awt.Dimension(45, 30));
        jbSeachFileProducts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSeachFileProductsActionPerformed(evt);
            }
        });

        jbImportProducts.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8_ssd_20px.png"))); // NOI18N
        jbImportProducts.setText("Importar");
        jbImportProducts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbImportProductsActionPerformed(evt);
            }
        });

        jPanel8.setPreferredSize(new java.awt.Dimension(100, 125));
        jPanel8.setLayout(null);

        jcbProdutosCodBarras.setText("Barras");
        jcbProdutosCodBarras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbProdutosCodBarrasActionPerformed(evt);
            }
        });
        jPanel8.add(jcbProdutosCodBarras);
        jcbProdutosCodBarras.setBounds(10, 80, 57, 22);

        jtfProdutosCodBarras.setMaximumSize(new java.awt.Dimension(35, 22));
        jtfProdutosCodBarras.setMinimumSize(new java.awt.Dimension(35, 22));
        jtfProdutosCodBarras.setPreferredSize(new java.awt.Dimension(35, 22));
        jtfProdutosCodBarras.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfProdutosCodBarrasKeyTyped(evt);
            }
        });
        jPanel8.add(jtfProdutosCodBarras);
        jtfProdutosCodBarras.setBounds(120, 80, 40, 23);

        jcbProdutosDescricao.setText("Descrição");
        jcbProdutosDescricao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbProdutosDescricaoActionPerformed(evt);
            }
        });
        jPanel8.add(jcbProdutosDescricao);
        jcbProdutosDescricao.setBounds(10, 50, 107, 22);

        jtfProdutosDescricao.setMaximumSize(new java.awt.Dimension(35, 22));
        jtfProdutosDescricao.setMinimumSize(new java.awt.Dimension(35, 22));
        jtfProdutosDescricao.setPreferredSize(new java.awt.Dimension(35, 22));
        jtfProdutosDescricao.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfProdutosDescricaoKeyTyped(evt);
            }
        });
        jPanel8.add(jtfProdutosDescricao);
        jtfProdutosDescricao.setBounds(120, 50, 40, 23);

        jcbProdutosRef.setText("Referência");
        jcbProdutosRef.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbProdutosRefActionPerformed(evt);
            }
        });
        jPanel8.add(jcbProdutosRef);
        jcbProdutosRef.setBounds(190, 50, 110, 22);

        jtfProdutosRef.setMaximumSize(new java.awt.Dimension(35, 22));
        jtfProdutosRef.setMinimumSize(new java.awt.Dimension(35, 22));
        jtfProdutosRef.setPreferredSize(new java.awt.Dimension(35, 22));
        jtfProdutosRef.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfProdutosRefKeyTyped(evt);
            }
        });
        jPanel8.add(jtfProdutosRef);
        jtfProdutosRef.setBounds(300, 50, 40, 23);

        jcbProdutosUnMed.setText("Un Medida");
        jcbProdutosUnMed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbProdutosUnMedActionPerformed(evt);
            }
        });
        jPanel8.add(jcbProdutosUnMed);
        jcbProdutosUnMed.setBounds(190, 80, 107, 22);

        jtfProdutosUnMed.setMaximumSize(new java.awt.Dimension(35, 22));
        jtfProdutosUnMed.setMinimumSize(new java.awt.Dimension(35, 22));
        jtfProdutosUnMed.setPreferredSize(new java.awt.Dimension(35, 22));
        jtfProdutosUnMed.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfProdutosUnMedKeyTyped(evt);
            }
        });
        jPanel8.add(jtfProdutosUnMed);
        jtfProdutosUnMed.setBounds(300, 80, 40, 23);

        jcbProdutosEstMin.setText("Estoque Min");
        jcbProdutosEstMin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbProdutosEstMinActionPerformed(evt);
            }
        });
        jPanel8.add(jcbProdutosEstMin);
        jcbProdutosEstMin.setBounds(370, 50, 110, 22);

        jtfProdutosEstMin.setMaximumSize(new java.awt.Dimension(35, 22));
        jtfProdutosEstMin.setMinimumSize(new java.awt.Dimension(35, 22));
        jtfProdutosEstMin.setPreferredSize(new java.awt.Dimension(35, 22));
        jtfProdutosEstMin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfProdutosEstMinKeyTyped(evt);
            }
        });
        jPanel8.add(jtfProdutosEstMin);
        jtfProdutosEstMin.setBounds(480, 50, 40, 23);

        jtfProdutosEstAtual.setMaximumSize(new java.awt.Dimension(35, 22));
        jtfProdutosEstAtual.setMinimumSize(new java.awt.Dimension(35, 22));
        jtfProdutosEstAtual.setPreferredSize(new java.awt.Dimension(35, 22));
        jtfProdutosEstAtual.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfProdutosEstAtualKeyTyped(evt);
            }
        });
        jPanel8.add(jtfProdutosEstAtual);
        jtfProdutosEstAtual.setBounds(480, 80, 40, 23);

        jcbProdutosEstAtual.setText("Estoque Atual");
        jcbProdutosEstAtual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbProdutosEstAtualActionPerformed(evt);
            }
        });
        jPanel8.add(jcbProdutosEstAtual);
        jcbProdutosEstAtual.setBounds(370, 80, 110, 23);

        jtfProdutosVlrVenda.setMaximumSize(new java.awt.Dimension(35, 22));
        jtfProdutosVlrVenda.setMinimumSize(new java.awt.Dimension(35, 22));
        jtfProdutosVlrVenda.setPreferredSize(new java.awt.Dimension(35, 22));
        jtfProdutosVlrVenda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfProdutosVlrVendaKeyTyped(evt);
            }
        });
        jPanel8.add(jtfProdutosVlrVenda);
        jtfProdutosVlrVenda.setBounds(480, 140, 40, 23);

        jtfProdutosVlrCusto.setMaximumSize(new java.awt.Dimension(35, 22));
        jtfProdutosVlrCusto.setMinimumSize(new java.awt.Dimension(35, 22));
        jtfProdutosVlrCusto.setPreferredSize(new java.awt.Dimension(35, 22));
        jtfProdutosVlrCusto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfProdutosVlrCustoKeyTyped(evt);
            }
        });
        jPanel8.add(jtfProdutosVlrCusto);
        jtfProdutosVlrCusto.setBounds(480, 110, 40, 23);

        jcbProdutosVlrCusto.setText("Valor Custo");
        jcbProdutosVlrCusto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbProdutosVlrCustoActionPerformed(evt);
            }
        });
        jPanel8.add(jcbProdutosVlrCusto);
        jcbProdutosVlrCusto.setBounds(370, 110, 110, 22);

        jcbProdutosVlrVenda.setText("Valor Venda");
        jcbProdutosVlrVenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbProdutosVlrVendaActionPerformed(evt);
            }
        });
        jPanel8.add(jcbProdutosVlrVenda);
        jcbProdutosVlrVenda.setBounds(370, 140, 100, 23);

        jtfProdutosMarca.setMaximumSize(new java.awt.Dimension(35, 22));
        jtfProdutosMarca.setMinimumSize(new java.awt.Dimension(35, 22));
        jtfProdutosMarca.setPreferredSize(new java.awt.Dimension(35, 22));
        jtfProdutosMarca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfProdutosMarcaKeyTyped(evt);
            }
        });
        jPanel8.add(jtfProdutosMarca);
        jtfProdutosMarca.setBounds(120, 110, 40, 23);

        jcbProdutosMarca.setText("Marca");
        jcbProdutosMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbProdutosMarcaActionPerformed(evt);
            }
        });
        jPanel8.add(jcbProdutosMarca);
        jcbProdutosMarca.setBounds(10, 110, 107, 23);

        jcbProdutosGrupo.setText("Grupo");
        jcbProdutosGrupo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbProdutosGrupoActionPerformed(evt);
            }
        });
        jPanel8.add(jcbProdutosGrupo);
        jcbProdutosGrupo.setBounds(190, 110, 107, 22);

        jtfProdutosGrupo.setMaximumSize(new java.awt.Dimension(35, 22));
        jtfProdutosGrupo.setMinimumSize(new java.awt.Dimension(35, 22));
        jtfProdutosGrupo.setPreferredSize(new java.awt.Dimension(35, 22));
        jtfProdutosGrupo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfProdutosGrupoKeyTyped(evt);
            }
        });
        jPanel8.add(jtfProdutosGrupo);
        jtfProdutosGrupo.setBounds(300, 110, 40, 23);

        jcbProdutosSitTrib.setText("Sit Trib");
        jcbProdutosSitTrib.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbProdutosSitTribActionPerformed(evt);
            }
        });
        jPanel8.add(jcbProdutosSitTrib);
        jcbProdutosSitTrib.setBounds(10, 140, 107, 22);

        jtfProdutosSitTrib.setMaximumSize(new java.awt.Dimension(35, 22));
        jtfProdutosSitTrib.setMinimumSize(new java.awt.Dimension(35, 22));
        jtfProdutosSitTrib.setPreferredSize(new java.awt.Dimension(35, 22));
        jtfProdutosSitTrib.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfProdutosSitTribKeyTyped(evt);
            }
        });
        jPanel8.add(jtfProdutosSitTrib);
        jtfProdutosSitTrib.setBounds(120, 140, 40, 23);

        jcbProdutosCFOP.setText("CFOP");
        jcbProdutosCFOP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbProdutosCFOPActionPerformed(evt);
            }
        });
        jPanel8.add(jcbProdutosCFOP);
        jcbProdutosCFOP.setBounds(190, 140, 107, 23);

        jtfProdutosCFOP.setMaximumSize(new java.awt.Dimension(35, 22));
        jtfProdutosCFOP.setMinimumSize(new java.awt.Dimension(35, 22));
        jtfProdutosCFOP.setPreferredSize(new java.awt.Dimension(35, 22));
        jtfProdutosCFOP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfProdutosCFOPKeyTyped(evt);
            }
        });
        jPanel8.add(jtfProdutosCFOP);
        jtfProdutosCFOP.setBounds(300, 140, 40, 23);

        jtfProdutosClassFiscal.setMaximumSize(new java.awt.Dimension(35, 22));
        jtfProdutosClassFiscal.setMinimumSize(new java.awt.Dimension(35, 22));
        jtfProdutosClassFiscal.setPreferredSize(new java.awt.Dimension(35, 22));
        jtfProdutosClassFiscal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfProdutosClassFiscalKeyTyped(evt);
            }
        });
        jPanel8.add(jtfProdutosClassFiscal);
        jtfProdutosClassFiscal.setBounds(120, 170, 40, 23);

        jcbProdutosClassFiscal.setText("Class Fiscal");
        jcbProdutosClassFiscal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbProdutosClassFiscalActionPerformed(evt);
            }
        });
        jPanel8.add(jcbProdutosClassFiscal);
        jcbProdutosClassFiscal.setBounds(10, 170, 93, 23);

        jcbHeaderExistsProducts.setText("Meu arquivo contém cabeçalho.");
        jPanel8.add(jcbHeaderExistsProducts);
        jcbHeaderExistsProducts.setBounds(10, 10, 270, 23);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 333, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jbImportProducts))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jtfPathProductsFile)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbSeachFileProducts, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbSeachFileProducts, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfPathProductsFile, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbImportProducts)
                .addContainerGap(47, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Produtos", jPanel3);

        jpFields.add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        jPanel1.add(jpFields, java.awt.BorderLayout.EAST);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jMenu1.setText("Arquivo");

        jMenu3.setText("Tema");

        buttonGroup1.add(jrbmDark);
        jrbmDark.setSelected(true);
        jrbmDark.setText("Escuro");
        jrbmDark.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbmDarkActionPerformed(evt);
            }
        });
        jMenu3.add(jrbmDark);

        buttonGroup1.add(jrbmLight);
        jrbmLight.setText("Claro");
        jrbmLight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbmLightActionPerformed(evt);
            }
        });
        jMenu3.add(jrbmLight);

        jMenu1.add(jMenu3);
        jMenu1.add(jSeparator1);

        jmiExit.setText("Sair");
        jmiExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiExitActionPerformed(evt);
            }
        });
        jMenu1.add(jmiExit);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Sobre");

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8_github_20px.png"))); // NOI18N
        jMenuItem1.setText("Github");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem1);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jbSearchFileClientsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSearchFileClientsActionPerformed
        getFileImport(jtfPathClientsFile);
    }//GEN-LAST:event_jbSearchFileClientsActionPerformed

    private void jbSeachFileProductsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSeachFileProductsActionPerformed
        getFileImport(jtfPathProductsFile);
    }//GEN-LAST:event_jbSeachFileProductsActionPerformed

    private void jbImportProductsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbImportProductsActionPerformed
        File file = new File(jtfPathProductsFile.getText());
        if (file.exists() && file.isFile()) {
            new Thread(() -> importProductsToFDB()).start();
        }
    }//GEN-LAST:event_jbImportProductsActionPerformed

    private void jbImportClientsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbImportClientsActionPerformed
        File file = new File(jtfPathClientsFile.getText());
        if (file.exists() && file.isFile()) {
            new Thread(() -> importClientToFDB()).start();
        }
    }//GEN-LAST:event_jbImportClientsActionPerformed

    private void jtfClientesEnderecoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfClientesEnderecoKeyTyped
        typed(evt, jtfClientesEndereco);
    }//GEN-LAST:event_jtfClientesEnderecoKeyTyped

    private void jtfClientesNumeroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfClientesNumeroKeyTyped
        typed(evt, jtfClientesNumero);
    }//GEN-LAST:event_jtfClientesNumeroKeyTyped

    private void jtfClientesIEIMKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfClientesIEIMKeyTyped
        typed(evt, jtfClientesIEIM);
    }//GEN-LAST:event_jtfClientesIEIMKeyTyped

    private void jtfClientesCPFCNPJKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfClientesCPFCNPJKeyTyped
        typed(evt, jtfClientesCPFCNPJ);
    }//GEN-LAST:event_jtfClientesCPFCNPJKeyTyped

    private void jtfClientesCelularKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfClientesCelularKeyTyped
        typed(evt, jtfClientesCelular);
    }//GEN-LAST:event_jtfClientesCelularKeyTyped

    private void jcbClientesCPFCNPJActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbClientesCPFCNPJActionPerformed
        if (jcbClientesCPFCNPJ.isSelected()) {
            jtfClientesCPFCNPJ.setVisible(true);
        } else {
            jtfClientesCPFCNPJ.setVisible(false);
        }
    }//GEN-LAST:event_jcbClientesCPFCNPJActionPerformed

    private void jcbClientesIEIMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbClientesIEIMActionPerformed
        if (jcbClientesIEIM.isSelected()) {
            jtfClientesIEIM.setVisible(true);
        } else {
            jtfClientesIEIM.setVisible(false);
        }
    }//GEN-LAST:event_jcbClientesIEIMActionPerformed

    private void jcbClientesEnderecoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbClientesEnderecoActionPerformed
        if (jcbClientesEndereco.isSelected()) {
            jtfClientesEndereco.setVisible(true);
        } else {
            jtfClientesEndereco.setVisible(false);
        }
    }//GEN-LAST:event_jcbClientesEnderecoActionPerformed

    private void jcbClientesNumeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbClientesNumeroActionPerformed
        if (jcbClientesNumero.isSelected()) {
            jtfClientesNumero.setVisible(true);
        } else {
            jtfClientesNumero.setVisible(false);
        }
    }//GEN-LAST:event_jcbClientesNumeroActionPerformed

    private void jcbClientesCelularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbClientesCelularActionPerformed
        if (jcbClientesCelular.isSelected()) {
            jtfClientesCelular.setVisible(true);
        } else {
            jtfClientesCelular.setVisible(false);
        }
    }//GEN-LAST:event_jcbClientesCelularActionPerformed

    private void jcbClientesTelefoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbClientesTelefoneActionPerformed
        if (jcbClientesTelefone.isSelected()) {
            jtfClientesTelefone.setVisible(true);
        } else {
            jtfClientesTelefone.setVisible(false);
        }
    }//GEN-LAST:event_jcbClientesTelefoneActionPerformed

    private void jtfClientesTelefoneKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfClientesTelefoneKeyTyped
        typed(evt, jtfClientesTelefone);
    }//GEN-LAST:event_jtfClientesTelefoneKeyTyped

    private void jcbClientesNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbClientesNomeActionPerformed
        if (jcbClientesNome.isSelected()) {
            jtfClientesNome.setVisible(true);
        } else {
            jtfClientesNome.setVisible(false);
        }
    }//GEN-LAST:event_jcbClientesNomeActionPerformed

    private void jtfClientesNomeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfClientesNomeKeyTyped
        typed(evt, jtfClientesNome);
    }//GEN-LAST:event_jtfClientesNomeKeyTyped

    private void jcbClientesContatoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbClientesContatoActionPerformed
        if (jcbClientesContato.isSelected()) {
            jtfClientesContato.setVisible(true);
        } else {
            jtfClientesContato.setVisible(false);
        }
    }//GEN-LAST:event_jcbClientesContatoActionPerformed

    private void jtfClientesContatoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfClientesContatoKeyTyped
        typed(evt, jtfClientesContato);
    }//GEN-LAST:event_jtfClientesContatoKeyTyped

    private void jcbClientesBairroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbClientesBairroActionPerformed
        if (jcbClientesBairro.isSelected()) {
            jtfClientesBairro.setVisible(true);
        } else {
            jtfClientesBairro.setVisible(false);
        }
    }//GEN-LAST:event_jcbClientesBairroActionPerformed

    private void jtfClientesBairroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfClientesBairroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfClientesBairroActionPerformed

    private void jtfClientesBairroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfClientesBairroKeyTyped
        typed(evt, jtfClientesBairro);
    }//GEN-LAST:event_jtfClientesBairroKeyTyped

    private void jcbClientesCidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbClientesCidadeActionPerformed
        if (jcbClientesCidade.isSelected()) {
            jtfClientesCidade.setVisible(true);
            jlInfoCidade.setVisible(true);
        } else {
            jtfClientesCidade.setVisible(false);
            jlInfoCidade.setVisible(false);
        }
    }//GEN-LAST:event_jcbClientesCidadeActionPerformed

    private void jtfClientesCidadeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfClientesCidadeKeyTyped
        typed(evt, jtfClientesCidade);
    }//GEN-LAST:event_jtfClientesCidadeKeyTyped

    private void jtfClientesDtNascKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfClientesDtNascKeyTyped
        typed(evt, jtfClientesDtNasc);
    }//GEN-LAST:event_jtfClientesDtNascKeyTyped

    private void jcbClientesDtNascActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbClientesDtNascActionPerformed
        if (jcbClientesDtNasc.isSelected()) {
            jtfClientesDtNasc.setVisible(true);
            jtfFormatDateNasc.setVisible(true);
            jlFormat.setVisible(true);
            jbInfo.setVisible(true);
        } else {
            jtfClientesDtNasc.setVisible(false);
            jtfFormatDateNasc.setVisible(false);
            jlFormat.setVisible(false);
            jbInfo.setVisible(false);
        }
    }//GEN-LAST:event_jcbClientesDtNascActionPerformed

    private void jcbClientesCEPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbClientesCEPActionPerformed
        if (jcbClientesCEP.isSelected()) {
            jtfClientesCEP.setVisible(true);
        } else {
            jtfClientesCEP.setVisible(false);
        }
    }//GEN-LAST:event_jcbClientesCEPActionPerformed

    private void jtfClientesCEPKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfClientesCEPKeyTyped
        typed(evt, jtfClientesCEP);
    }//GEN-LAST:event_jtfClientesCEPKeyTyped

    private void jcbClientesEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbClientesEmailActionPerformed
        if (jcbClientesEmail.isSelected()) {
            jtfClientesEmail.setVisible(true);
        } else {
            jtfClientesEmail.setVisible(false);
        }
    }//GEN-LAST:event_jcbClientesEmailActionPerformed

    private void jtfClientesEmailKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfClientesEmailKeyTyped
        typed(evt, jtfClientesEmail);
    }//GEN-LAST:event_jtfClientesEmailKeyTyped

    private void jtfClientesFantasiaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfClientesFantasiaKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfClientesFantasiaKeyTyped

    private void jcbClientesFantasiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbClientesFantasiaActionPerformed
        if (jcbClientesFantasia.isSelected()) {
            jtfClientesFantasia.setVisible(true);
        } else {
            jtfClientesFantasia.setVisible(false);
        }
    }//GEN-LAST:event_jcbClientesFantasiaActionPerformed

    private void jcbClientesComplementoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbClientesComplementoActionPerformed
        if (jcbClientesComplemento.isSelected()) {
            jtfClientesComplemento.setVisible(true);
        } else {
            jtfClientesComplemento.setVisible(false);
        }
    }//GEN-LAST:event_jcbClientesComplementoActionPerformed

    private void jtfClientesComplementoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfClientesComplementoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfClientesComplementoKeyTyped

    private void jrbmLightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbmLightActionPerformed
        changeTheme("Light");
    }//GEN-LAST:event_jrbmLightActionPerformed

    private void jrbmDarkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbmDarkActionPerformed
        changeTheme("Dark");
    }//GEN-LAST:event_jrbmDarkActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        try {
            URI uri = URI.create("https://github.com/rodrigocananea/simple-import-windel");
            java.awt.Desktop.getDesktop().browse(uri);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao abrir navegador, motivo:\n"
                    + ex.getMessage());
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jtfClientesNomeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfClientesNomeKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfClientesNomeKeyPressed

    private void jbInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbInfoActionPerformed
        StringBuilder msg = new StringBuilder();
        msg.append("Os formatos que podem ser utilizados são os seguintes:\n");
        msg.append("d -> 'Dia'\n");
        msg.append("M -> 'Mês'\n");
        msg.append("y -> 'Ano'\n\n");
        msg.append("Exemplos:\n");
        msg.append("'2021-12-31' -> 'yyyy-MM-dd'\n");
        msg.append("'31.12.2021' -> 'dd.MM.yyyy'\n");
        msg.append("'2021/31/12' -> 'yyyy/MM/dd'\n");
        msg.append("'31-nov-21' -> 'dd/MMM/yy'\n\n");
        msg.append("Somente informando o formato correto o sistema\n"
                + "vai preencher este campo, caso não esteja preenchido\n"
                + "corretamente ele vai ser ignorado.");
        JOptionPane.showMessageDialog(this, msg);
    }//GEN-LAST:event_jbInfoActionPerformed

    private void jlInfoCidadeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlInfoCidadeMouseClicked
        String msg = "Nesta coluna é aceito somente o nome da cidade seguido com a UF\n"
                + "ou o código IBGE da cidade, exemplos:\n\n"
                + "'IJUÍ-RS'  (caso não houver a UF por padrão será RS)\n"
                + "'4310207' (codigo ibge de ijuí)";

        JOptionPane.showMessageDialog(this, msg);
    }//GEN-LAST:event_jlInfoCidadeMouseClicked

    private void jcbProdutosCodBarrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbProdutosCodBarrasActionPerformed
        if (jcbProdutosCodBarras.isSelected()) {
            jtfProdutosCodBarras.setVisible(true);
        } else {
            jtfProdutosCodBarras.setVisible(false);
        }
    }//GEN-LAST:event_jcbProdutosCodBarrasActionPerformed

    private void jtfProdutosCodBarrasKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfProdutosCodBarrasKeyTyped
        String caracteres = "0987654321";
        if (!caracteres.contains(evt.getKeyChar() + "")) {
            evt.consume();
        }
    }//GEN-LAST:event_jtfProdutosCodBarrasKeyTyped

    private void jcbProdutosDescricaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbProdutosDescricaoActionPerformed
        if (jcbProdutosDescricao.isSelected()) {
            jtfProdutosDescricao.setVisible(true);
        } else {
            jtfProdutosDescricao.setVisible(false);
        }
    }//GEN-LAST:event_jcbProdutosDescricaoActionPerformed

    private void jtfProdutosDescricaoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfProdutosDescricaoKeyTyped
        typed(evt, jtfProdutosDescricao);
    }//GEN-LAST:event_jtfProdutosDescricaoKeyTyped

    private void jcbProdutosRefActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbProdutosRefActionPerformed
        if (jcbProdutosRef.isSelected()) {
            jtfProdutosRef.setVisible(true);
        } else {
            jtfProdutosRef.setVisible(false);
        }
    }//GEN-LAST:event_jcbProdutosRefActionPerformed

    private void jtfProdutosRefKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfProdutosRefKeyTyped
        typed(evt, jtfProdutosRef);
    }//GEN-LAST:event_jtfProdutosRefKeyTyped

    private void jcbProdutosUnMedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbProdutosUnMedActionPerformed
        if (jcbProdutosUnMed.isSelected()) {
            jtfProdutosUnMed.setVisible(true);
        } else {
            jtfProdutosUnMed.setVisible(false);
        }
    }//GEN-LAST:event_jcbProdutosUnMedActionPerformed

    private void jtfProdutosUnMedKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfProdutosUnMedKeyTyped
        typed(evt, jtfProdutosUnMed);
    }//GEN-LAST:event_jtfProdutosUnMedKeyTyped

    private void jcbProdutosEstMinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbProdutosEstMinActionPerformed
        if (jcbProdutosEstMin.isSelected()) {
            jtfProdutosEstMin.setVisible(true);
        } else {
            jtfProdutosEstMin.setVisible(false);
        }
    }//GEN-LAST:event_jcbProdutosEstMinActionPerformed

    private void jtfProdutosEstMinKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfProdutosEstMinKeyTyped
        typed(evt, jtfProdutosEstMin);
    }//GEN-LAST:event_jtfProdutosEstMinKeyTyped

    private void jtfProdutosEstAtualKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfProdutosEstAtualKeyTyped
        typed(evt, jtfProdutosEstAtual);
    }//GEN-LAST:event_jtfProdutosEstAtualKeyTyped

    private void jcbProdutosEstAtualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbProdutosEstAtualActionPerformed
        if (jcbProdutosEstAtual.isSelected()) {
            jtfProdutosEstAtual.setVisible(true);
        } else {
            jtfProdutosEstAtual.setVisible(false);
        }
    }//GEN-LAST:event_jcbProdutosEstAtualActionPerformed

    private void jtfProdutosVlrVendaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfProdutosVlrVendaKeyTyped
        typed(evt, jtfProdutosVlrVenda);
    }//GEN-LAST:event_jtfProdutosVlrVendaKeyTyped

    private void jtfProdutosVlrCustoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfProdutosVlrCustoKeyTyped
        typed(evt, jtfProdutosVlrCusto);
    }//GEN-LAST:event_jtfProdutosVlrCustoKeyTyped

    private void jcbProdutosVlrCustoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbProdutosVlrCustoActionPerformed
        if (jcbProdutosVlrCusto.isSelected()) {
            jtfProdutosVlrCusto.setVisible(true);
        } else {
            jtfProdutosVlrCusto.setVisible(false);
        }
    }//GEN-LAST:event_jcbProdutosVlrCustoActionPerformed

    private void jcbProdutosVlrVendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbProdutosVlrVendaActionPerformed
        if (jcbProdutosVlrVenda.isSelected()) {
            jtfProdutosVlrVenda.setVisible(true);
        } else {
            jtfProdutosVlrVenda.setVisible(false);
        }
    }//GEN-LAST:event_jcbProdutosVlrVendaActionPerformed

    private void jtfProdutosMarcaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfProdutosMarcaKeyTyped
        typed(evt, jtfProdutosMarca);
    }//GEN-LAST:event_jtfProdutosMarcaKeyTyped

    private void jcbProdutosMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbProdutosMarcaActionPerformed
        if (jcbProdutosMarca.isSelected()) {
            jtfProdutosMarca.setVisible(true);
        } else {
            jtfProdutosMarca.setVisible(false);
        }
    }//GEN-LAST:event_jcbProdutosMarcaActionPerformed

    private void jcbProdutosGrupoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbProdutosGrupoActionPerformed
        if (jcbProdutosGrupo.isSelected()) {
            jtfProdutosGrupo.setVisible(true);
        } else {
            jtfProdutosGrupo.setVisible(false);
        }
    }//GEN-LAST:event_jcbProdutosGrupoActionPerformed

    private void jtfProdutosGrupoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfProdutosGrupoKeyTyped
        typed(evt, jtfProdutosGrupo);
    }//GEN-LAST:event_jtfProdutosGrupoKeyTyped

    private void jcbProdutosSitTribActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbProdutosSitTribActionPerformed
        if (jcbProdutosSitTrib.isSelected()) {
            jtfProdutosSitTrib.setVisible(true);
        } else {
            jtfProdutosSitTrib.setVisible(false);
        }
    }//GEN-LAST:event_jcbProdutosSitTribActionPerformed

    private void jtfProdutosSitTribKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfProdutosSitTribKeyTyped
        typed(evt, jtfProdutosSitTrib);
    }//GEN-LAST:event_jtfProdutosSitTribKeyTyped

    private void jcbProdutosCFOPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbProdutosCFOPActionPerformed
        if (jcbProdutosCFOP.isSelected()) {
            jtfProdutosCFOP.setVisible(true);
        } else {
            jtfProdutosCFOP.setVisible(false);
        }
    }//GEN-LAST:event_jcbProdutosCFOPActionPerformed

    private void jtfProdutosCFOPKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfProdutosCFOPKeyTyped
        typed(evt, jtfProdutosCFOP);
    }//GEN-LAST:event_jtfProdutosCFOPKeyTyped

    private void jtfProdutosClassFiscalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfProdutosClassFiscalKeyTyped
        typed(evt, jtfProdutosClassFiscal);
    }//GEN-LAST:event_jtfProdutosClassFiscalKeyTyped

    private void jcbProdutosClassFiscalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbProdutosClassFiscalActionPerformed
        if (jcbProdutosClassFiscal.isSelected()) {
            jtfProdutosClassFiscal.setVisible(true);
        } else {
            jtfProdutosClassFiscal.setVisible(false);
        }
    }//GEN-LAST:event_jcbProdutosClassFiscalActionPerformed

    private void jmiExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiExitActionPerformed
        exitSystem();
    }//GEN-LAST:event_jmiExitActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        exitSystem();
    }//GEN-LAST:event_formWindowClosing

    private void importProductsToFDB() {
        jbImportProducts.setEnabled(false);

        try {
            logger.info("==================== Iniciando importação de Produtos ====================");

            logger.info("# Carregando unidades de medida do banco de dados, aguarde...");
            unidades = new ControllerData().getUnidades();
            logger.info("# Unidades de medida carregadas!");
            logger.info("# Carregando situações tributárias do banco de dados, aguarde...");
            sitTribs = new ControllerData().getSitTribs();
            logger.info("# Situações tributárias carregadas!");
            logger.info("# Carregando NCM's do banco de dados, aguarde...");
            classfiscals = new ControllerData().getClassFiscal();
            logger.info("# NCM's carregadas!");

            Connection conn = new Database().getConnection();
            conn.setAutoCommit(false);

            StringBuilder query = new StringBuilder();
            query.append("INSERT INTO PRODUTOS (IDEMPRESA, IDPRODUTO, DESCRICAO, TIPO, UN, "
                    + "BARRAS, REFERENCIA, VLR_VENDA, VLR_ULTCOMPRA, EST_MIN, EST_ATUAL, MARCA, GRUPO, "
                    + "SITTRIB, NATOPERPADRAOVENDAS, CLASSFISCAL, FORADELINHA)\n"
                    + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);");

            ModelEmpresa empresa = (ModelEmpresa) jcbEmpresa.getSelectedItem();
            int IDEMPRESA = empresa.getId();
            int IDPRODUTO = new ControllerData().getLastIdProduct(empresa.getId());
            String DESCRICAO = "";
            String TIPO = "C";
            String UN = "UN";
            String BARRAS = "";
            String REFERENCIA = "";
            double VLR_VENDA = 0.00;
            double VLR_ULTCOMPRA = 0.00;
            double EST_MIN = 0.00;
            double EST_ATUAL = 0.00;
            String MARCA = "";
            int GRUPO = 0;
            int SITTRIB = 0;
            int NATOPERPADRAOVENDAS = 0;
            int CLASSFISCAL = 0;
            String FORADELINHA = "N";

            int skip = jcbHeaderExistsProducts.isSelected() ? 1 : 0;
            List<String> fileImport = new BufferedReader(
                    new InputStreamReader(new FileInputStream(jtfPathProductsFile.getText()), Charset.forName("ISO-8859-1")))
                    .lines()
                    .skip(skip)
                    .collect(Collectors.toList());

            int countLines = 0;
            int lastLine = fileImport.size();
            jpbProgress.setValue(0);
            jpbProgress.setMaximum(lastLine);

            for (String line : fileImport) {

                List<String> columns = Stream.of(line.split(";"))
                        .map(StringUtils::trimToEmpty)
                        .collect(Collectors.toList());

                String indexDESCRICAO = jtfProdutosDescricao.getText();
                if (jcbProdutosDescricao.isSelected()
                        && StringUtils.isNotBlank(indexDESCRICAO)) {

                    if (Util.indexExists(columns, excelCols.indexOf(indexDESCRICAO))) {
                        DESCRICAO = columns.get(excelCols.indexOf(indexDESCRICAO));
                    }
                }

                String indexUN = jtfProdutosUnMed.getText();
                if (jcbProdutosUnMed.isSelected()
                        && StringUtils.isNotBlank(indexUN)) {

                    if (Util.indexExists(columns, excelCols.indexOf(indexUN))) {
                        UN = columns.get(excelCols.indexOf(indexUN));
                        UN = getUnidade(UN);
                    }
                }

                String indexBARRAS = jtfProdutosCodBarras.getText();
                if (jcbProdutosCodBarras.isSelected()
                        && StringUtils.isNotBlank(indexBARRAS)) {

                    if (Util.indexExists(columns, excelCols.indexOf(indexBARRAS))) {
                        BARRAS = columns.get(excelCols.indexOf(indexBARRAS));
                    }
                }

                String indexREFERENCIA = jtfProdutosRef.getText();
                if (jcbProdutosRef.isSelected()
                        && StringUtils.isNotBlank(indexREFERENCIA)) {

                    if (Util.indexExists(columns, excelCols.indexOf(indexREFERENCIA))) {
                        REFERENCIA = columns.get(excelCols.indexOf(indexREFERENCIA));
                    }
                }

                String indexVLR_VENDA = jtfProdutosVlrVenda.getText();
                if (jcbProdutosVlrVenda.isSelected()
                        && StringUtils.isNotBlank(indexVLR_VENDA)) {

                    if (Util.indexExists(columns, excelCols.indexOf(indexVLR_VENDA))) {
                        VLR_VENDA = Util.currency(columns.get(excelCols.indexOf(indexVLR_VENDA)));
                    }
                }

                String indexVLR_ULTCOMPRA = jtfProdutosVlrCusto.getText();
                if (jcbProdutosVlrCusto.isSelected()
                        && StringUtils.isNotBlank(indexVLR_ULTCOMPRA)) {

                    if (Util.indexExists(columns, excelCols.indexOf(indexVLR_ULTCOMPRA))) {
                        VLR_ULTCOMPRA = Util.currency(columns.get(excelCols.indexOf(indexVLR_ULTCOMPRA)));
                    }
                }

                String indexEST_MIN = jtfProdutosEstMin.getText();
                if (jcbProdutosEstMin.isSelected()
                        && StringUtils.isNotBlank(indexEST_MIN)) {

                    if (Util.indexExists(columns, excelCols.indexOf(indexEST_MIN))) {
                        EST_MIN = Util.currency(columns.get(excelCols.indexOf(indexEST_MIN)));
                    }
                }

                String indexEST_ATUAL = jtfProdutosEstAtual.getText();
                if (jcbProdutosEstAtual.isSelected()
                        && StringUtils.isNotBlank(indexEST_ATUAL)) {

                    if (Util.indexExists(columns, excelCols.indexOf(indexEST_ATUAL))) {
                        EST_ATUAL = Util.currency(columns.get(excelCols.indexOf(indexEST_ATUAL)));
                    }
                }

                String indexMARCA = jtfProdutosMarca.getText();
                if (jcbProdutosMarca.isSelected()
                        && StringUtils.isNotBlank(indexMARCA)) {

                    if (Util.indexExists(columns, excelCols.indexOf(indexMARCA))) {
                        MARCA = columns.get(excelCols.indexOf(indexMARCA));
                    }
                }

                String indexGRUPO = jtfProdutosGrupo.getText();
                if (jcbProdutosGrupo.isSelected()
                        && StringUtils.isNotBlank(indexGRUPO)) {

                    if (Util.indexExists(columns, excelCols.indexOf(indexGRUPO))) {
                        indexGRUPO = columns.get(excelCols.indexOf(indexGRUPO));
                        if (StringUtils.isNumeric(indexGRUPO)) {
                            GRUPO = Integer.valueOf(indexGRUPO);
                        }
                    }
                }

                String indexSITTRIB = jtfProdutosSitTrib.getText();
                if (jcbProdutosSitTrib.isSelected()
                        && StringUtils.isNotBlank(indexSITTRIB)) {

                    if (Util.indexExists(columns, excelCols.indexOf(indexSITTRIB))) {
                        indexSITTRIB = columns.get(excelCols.indexOf(indexSITTRIB));

                        SITTRIB = getSitTrib(indexSITTRIB);
                    }
                }

                String indexCLASSFISCAL = jtfProdutosClassFiscal.getText();
                if (jcbProdutosClassFiscal.isSelected()
                        && StringUtils.isNotBlank(indexCLASSFISCAL)) {

                    if (Util.indexExists(columns, excelCols.indexOf(indexCLASSFISCAL))) {
                        indexCLASSFISCAL = columns.get(excelCols.indexOf(indexCLASSFISCAL));

                        CLASSFISCAL = getClassFiscal(indexCLASSFISCAL);
                    }
                }

                logger.info("Importando: " + IDPRODUTO + " - DESC.: " + DESCRICAO + " - VLR. VENDA: " + VLR_VENDA);
                try (PreparedStatement pst = conn.prepareStatement(query.toString())) {
                    pst.setInt(1, IDEMPRESA);
                    pst.setString(2, String.format("%08d", IDPRODUTO));
                    pst.setString(3, DESCRICAO);
                    pst.setString(4, TIPO);
                    pst.setString(5, UN);
                    pst.setString(6, BARRAS);
                    pst.setString(7, REFERENCIA);
                    pst.setDouble(8, VLR_VENDA);
                    pst.setDouble(9, VLR_ULTCOMPRA);
                    pst.setDouble(10, EST_MIN);
                    pst.setDouble(11, EST_ATUAL);
                    pst.setString(12, MARCA);
                    pst.setInt(13, GRUPO);
                    pst.setInt(14, SITTRIB);
                    pst.setInt(15, NATOPERPADRAOVENDAS);
                    pst.setInt(16, CLASSFISCAL);
                    pst.setString(17, FORADELINHA);
                    pst.execute();
                }

                jpbProgress.setValue(countLines);
                jpbProgress.setString("Importando produtos " + countLines + " de " + lastLine);
                countLines++;
                IDPRODUTO++;
            }

            if (!conn.getAutoCommit()) {
                conn.commit();
            }

            jpbProgress.setMaximum(100);
            jpbProgress.setString("Finalizada importação com sucesso!");
            logger.info("==================== Finalizado importação de Produtos ====================");
            JOptionPane.showMessageDialog(this, "Finalizada importação de produtos com sucesso!");
            jbImportProducts.setEnabled(true);
        } catch (SQLException | ClassNotFoundException | FileNotFoundException ex) {
            logger.error("# Erro ao realizar importação, motivo:");
            logger.error(ExceptionUtils.getStackTrace(ex));
            jbImportProducts.setEnabled(true);
            JOptionPane.showMessageDialog(this, "Erro ao realizar importação, motivo:\n"
                    + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void importClientToFDB() {
        jbImportClients.setEnabled(false);

        try {
            logger.info("==================== Iniciando importação de Clientes ====================");

            logger.info("# Carregando cidades do banco de dados, aguarde...");
            cidades = new ControllerData().getCidades();
            logger.info("# Cidades carregadas!");

            Connection conn = new Database().getConnection();
            conn.setAutoCommit(false);

            StringBuilder query = new StringBuilder();
            query.append("INSERT INTO PESSOAS (IDEMPRESA, IDTIPO_PS, IDPESSOA, NOME, TIPO_FJ, CNPJCPF, "
                    + "INSCR_EST, DATANASCIMENTO, FANTASIA, FONE1, FONE2, EMAIL, DATACADASTRO, PRI_ENDERECO, "
                    + "PRI_COMPLEMENTO, PRI_CEP, PRI_CIDADE, PRI_BAIRRO, NUMEROENDER, SITUACAO)\n"
                    + " VALUES (?, 1, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 'A');");

            ModelEmpresa empresa = (ModelEmpresa) jcbEmpresa.getSelectedItem();
            int IDEMPRESA = empresa.getId();
            int IDPESSOA = new ControllerData().getLastIdClient(empresa.getId());
            String NOME = "";
            String TIPO_FJ = "F";
            String CNPJCPF = "";
            String INSCR_EST = "ISENTO";
            Date DATANASCIMENTO = null;
            String FANTASIA = "";
            String FONE1 = "";
            String FONE2 = "";
            String EMAIL = "";
            Date DATACADASTRO = new Date();
            String PRI_ENDERECO = "";
            String PRI_COMPLEMENTO = "";
            String PRI_CEP = "";
            int PRI_CIDADE = empresa.getIdCidade();
            String PRI_BAIRRO = "";
            String NUMEROENDER = "S/N";

            int skip = jcbHeaderExistsClient.isSelected() ? 1 : 0;
            List<String> fileImport = new BufferedReader(
                    new InputStreamReader(new FileInputStream(jtfPathClientsFile.getText()), Charset.forName("ISO-8859-1")))
                    .lines()
                    .skip(skip)
                    .collect(Collectors.toList());

            int countLines = 0;
            int lastLine = fileImport.size();
            jpbProgress.setValue(0);
            jpbProgress.setMaximum(lastLine);

            for (String line : fileImport) {

                List<String> columns = Stream.of(line.split(";"))
                        .map(StringUtils::trimToEmpty)
                        .collect(Collectors.toList());

                String indexNOME = jtfClientesNome.getText();
                if (jcbClientesNome.isSelected()
                        && StringUtils.isNotBlank(indexNOME)) {

                    if (Util.indexExists(columns, excelCols.indexOf(indexNOME))) {
                        NOME = columns.get(excelCols.indexOf(indexNOME));
                    }
                }

                String indexCNPJCPF = jtfClientesCPFCNPJ.getText();
                if (jcbClientesCPFCNPJ.isSelected()
                        && StringUtils.isNotBlank(indexCNPJCPF)) {
                    if (Util.indexExists(columns, excelCols.indexOf(indexCNPJCPF))) {
                        CNPJCPF = columns.get(excelCols.indexOf(indexCNPJCPF));
                    }
                }

                if (StringUtils.isNotBlank(CNPJCPF)
                        && CNPJCPF.replaceAll("\\D", "").length() == 14) {
                    TIPO_FJ = "J";
                }

                String indexINSCR_EST = jtfClientesIEIM.getText();
                if (jcbClientesIEIM.isSelected()
                        && StringUtils.isNotBlank(indexINSCR_EST)) {
                    if (Util.indexExists(columns, excelCols.indexOf(indexINSCR_EST))) {
                        INSCR_EST = columns.get(excelCols.indexOf(indexINSCR_EST));

                        if (INSCR_EST.replaceAll("\\D", "").equals("")) {
                            INSCR_EST = "ISENTO";
                        }
                    }
                }

                String indexDATANASCIMENTO = jtfClientesDtNasc.getText();
                if (jcbClientesDtNasc.isSelected()
                        && StringUtils.isNotBlank(indexDATANASCIMENTO)
                        && StringUtils.isNotBlank(jtfFormatDateNasc.getText())) {
                    if (Util.indexExists(columns, excelCols.indexOf(indexDATANASCIMENTO))) {
                        indexDATANASCIMENTO = columns.get(excelCols.indexOf(indexDATANASCIMENTO));
                        DATANASCIMENTO = Util.asDate(indexDATANASCIMENTO, jtfFormatDateNasc.getText());
                    }
                }

                String indexFANTASIA = jtfClientesFantasia.getText();
                if (jcbClientesFantasia.isSelected()
                        && StringUtils.isNotBlank(indexFANTASIA)) {
                    if (Util.indexExists(columns, excelCols.indexOf(indexFANTASIA))) {
                        FANTASIA = columns.get(excelCols.indexOf(indexFANTASIA));
                    }
                }

                String indexFONE1 = jtfClientesTelefone.getText();
                if (jcbClientesTelefone.isSelected()
                        && StringUtils.isNotBlank(indexFONE1)) {
                    if (Util.indexExists(columns, excelCols.indexOf(indexFONE1))) {
                        FONE1 = columns.get(excelCols.indexOf(indexFONE1));
                    }
                }

                String indexFONE2 = jtfClientesCelular.getText();
                if (jcbClientesCelular.isSelected()
                        && StringUtils.isNotBlank(indexFONE2)) {
                    if (Util.indexExists(columns, excelCols.indexOf(indexFONE2))) {
                        FONE2 = columns.get(excelCols.indexOf(indexFONE2));
                    }
                }

                String indexEMAIL = jtfClientesEmail.getText();
                if (jcbClientesEmail.isSelected()
                        && StringUtils.isNotBlank(indexEMAIL)) {
                    if (Util.indexExists(columns, excelCols.indexOf(indexEMAIL))) {
                        EMAIL = columns.get(excelCols.indexOf(indexEMAIL));
                    }
                }

                String indexPRI_ENDERECO = jtfClientesEndereco.getText();
                if (jcbClientesEndereco.isSelected()
                        && StringUtils.isNotBlank(indexPRI_ENDERECO)) {
                    if (Util.indexExists(columns, excelCols.indexOf(indexPRI_ENDERECO))) {
                        PRI_ENDERECO = columns.get(excelCols.indexOf(indexPRI_ENDERECO));
                    }
                }

                String indexNUMEROENDER = jtfClientesNumero.getText();
                if (jcbClientesNumero.isSelected()
                        && StringUtils.isNotBlank(indexNUMEROENDER)) {
                    if (Util.indexExists(columns, excelCols.indexOf(indexNUMEROENDER))) {
                        NUMEROENDER = columns.get(excelCols.indexOf(indexNUMEROENDER));
                    }
                }

                String indexPRI_COMPLEMENTO = jtfClientesComplemento.getText();
                if (jcbClientesComplemento.isSelected()
                        && StringUtils.isNotBlank(indexPRI_COMPLEMENTO)) {
                    if (Util.indexExists(columns, excelCols.indexOf(indexPRI_COMPLEMENTO))) {
                        PRI_COMPLEMENTO = columns.get(excelCols.indexOf(indexPRI_COMPLEMENTO));
                    }
                }

                String indexPRI_CEP = jtfClientesCEP.getText();
                if (jcbClientesCEP.isSelected()
                        && StringUtils.isNotBlank(indexPRI_CEP)) {
                    if (Util.indexExists(columns, excelCols.indexOf(indexPRI_CEP))) {
                        PRI_CEP = columns.get(excelCols.indexOf(indexPRI_CEP));
                    }
                }

                String indexPRI_BAIRRO = jtfClientesBairro.getText();
                if (jcbClientesBairro.isSelected()
                        && StringUtils.isNotBlank(indexPRI_BAIRRO)) {
                    if (Util.indexExists(columns, excelCols.indexOf(indexPRI_BAIRRO))) {
                        PRI_BAIRRO = columns.get(excelCols.indexOf(indexPRI_BAIRRO));
                    }
                }

                String indexPRI_CIDADE = jtfClientesCidade.getText();
                if (jcbClientesCidade.isSelected()
                        && StringUtils.isNotBlank(indexPRI_CIDADE)) {
                    if (Util.indexExists(columns, excelCols.indexOf(indexPRI_CIDADE))) {
                        String searchPRI_CIDADE = columns.get(excelCols.indexOf(indexPRI_CIDADE));
                        PRI_CIDADE = getCidade(searchPRI_CIDADE, empresa.getIdCidade());
                    }
                }

                logger.info("Importando: " + IDPESSOA + " - NOME: " + NOME + " - CNPJ/CPF: " + CNPJCPF);
                try (PreparedStatement pst = conn.prepareStatement(query.toString())) {
                    pst.setInt(1, IDEMPRESA);
                    pst.setInt(2, IDPESSOA);
                    pst.setString(3, NOME);
                    pst.setString(4, TIPO_FJ);
                    pst.setString(5, CNPJCPF);
                    pst.setString(6, INSCR_EST);
                    if (DATANASCIMENTO == null) {
                        pst.setDate(7, null);
                    } else {
                        pst.setDate(7, new java.sql.Date(DATANASCIMENTO.getTime()));
                    }
                    pst.setString(8, FANTASIA);
                    pst.setString(9, FONE1);
                    pst.setString(10, FONE2);
                    pst.setString(11, EMAIL);
                    pst.setDate(12, new java.sql.Date(DATACADASTRO.getTime()));
                    pst.setString(13, PRI_ENDERECO);
                    pst.setString(14, PRI_COMPLEMENTO);
                    pst.setString(15, PRI_CEP);
                    pst.setInt(16, PRI_CIDADE);
                    pst.setString(17, PRI_BAIRRO);
                    pst.setString(18, NUMEROENDER);
                    pst.execute();
                }

                jpbProgress.setValue(countLines);
                jpbProgress.setString("Importando clientes " + countLines + " de " + lastLine);
                countLines++;
                IDPESSOA++;
            }

            if (!conn.getAutoCommit()) {
                conn.commit();
            }

            jpbProgress.setMaximum(100);
            jpbProgress.setString("Finalizada importação com sucesso!");
            logger.info("==================== Finalizado importação de Clientes ====================");
            JOptionPane.showMessageDialog(this, "Finalizada importação de clientes com sucesso!");
            jbImportClients.setEnabled(true);
        } catch (SQLException | ClassNotFoundException | FileNotFoundException ex) {
            logger.error("# Erro ao realizar importação, motivo:");
            logger.error(ExceptionUtils.getStackTrace(ex));
            jbImportClients.setEnabled(true);
            JOptionPane.showMessageDialog(this, "Erro ao realizar importação, motivo:\n"
                    + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void getEmpresas() {
        new Thread(() -> {
            logger.info("# Carregando empresas no sistema, aguarde...");
            List<ModelEmpresa> empresas = new ControllerData().getEmpresas();
            jcbEmpresa.removeAllItems();
            empresas.stream().map(e -> {
                jcbEmpresa.addItem(e);
                return e;
            }).filter(e -> e.getId() == 1).forEach(e -> {
                jcbEmpresa.setSelectedItem(e);
            });
            logger.info("# Empresas carregadas com sucesso.");
        }).start();
    }

    private int getCidade(String nomeOuIBGE, int cidadeEmpresa) {
        int found = 0;
        ModelCidade cidade = new ModelCidade();
        cidade.setId(cidadeEmpresa);

        if (!nomeOuIBGE.replaceAll("\\D", "").equals("")) {
            cidade = cidades.stream()
                    .filter(c -> c.getIbge() == Integer.valueOf(nomeOuIBGE))
                    .findFirst().orElse(cidade);
            found = cidade.getId();
        } else {
            String[] cidadeComUF;

            if (nomeOuIBGE.contains("-")) {
                cidadeComUF = nomeOuIBGE.split("-");
            } else {
                cidadeComUF = new String[]{nomeOuIBGE, "RS"};
            }
            String uf = cidadeComUF[1].trim().toUpperCase();
            String bCidade = Util.normalizer(cidadeComUF[0]).toUpperCase().trim();

            cidade = cidades.stream()
                    .filter(c -> Util.normalizer(c.getNome()).equals(bCidade)
                    && c.getUF().toUpperCase().equals(uf))
                    .findFirst().orElse(cidade);

            found = cidade.getId();
        }

        return found;

    }

    private void exitSystem() {
        int confirm = JOptionPane.showConfirmDialog(this, "Deseja realmente sair do sistema?", "Sair", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private String getUnidade(final String UN) {
        boolean exists = unidades.stream()
                .filter(u -> u.getUnMed().toUpperCase().equals(UN.toUpperCase()))
                .findFirst().isPresent();

        if (!exists) {
            UnMedEnum unMed = null;
            try {
                unMed = UnMedEnum.valueOf(UN.toUpperCase());
                new ControllerData().saveUnMed(unMed.getunMed(), unMed.getNome());
                return unMed.getunMed();
            } catch (Exception ex) {
                return "UN";
            }
        } else {
            return UN.toUpperCase();
        }
    }

    private int getSitTrib(String indexSITTRIB) {
        int IDSITTRIB = 0;

        if (StringUtils.isNotBlank(indexSITTRIB)) {

            if (indexSITTRIB.length() == 2) {
                indexSITTRIB = "0" + indexSITTRIB;
            }
            final String searchSITTRIB = indexSITTRIB;
            ModelSitTrib sitTrib = sitTribs.stream().filter(s -> s.getCst().equals(searchSITTRIB)
                    || s.getCsosn().equals(searchSITTRIB)).findFirst().orElse(null);

            if (sitTrib != null) {
                return sitTrib.getId();
            }

        }

        return IDSITTRIB;
    }

    private int getClassFiscal(String CLASSFISCAL) {
        int IDCLASSFISCAL = 0;

        if (StringUtils.isNotBlank(CLASSFISCAL)) {

            final String searchNCM = CLASSFISCAL.replaceAll("\\D", "");
            ModelClassFiscal classFiscal = classfiscals.stream().filter(c -> {
                String ncm = c.getNcm().replaceAll("\\D", "");
                return ncm.equals(searchNCM);
            }).findFirst().orElse(null);

            if (classFiscal != null) {
                return classFiscal.getId();
            }

        }

        return IDCLASSFISCAL;
    }

    class UpperCase extends PlainDocument {

        @Override
        public void replace(int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (text != null) {
                text = text.toUpperCase();
            }
            super.replace(offset, length, text, attrs);
        }
    }

    private void changeTheme(String theme) {
        if (theme.equals("Dark")) {
            SIProp.setProp("tema", "Dark");
            FlatOneDarkIJTheme.install();
        } else if (theme.equals("Light")) {
            SIProp.setProp("tema", "Light");
            FlatIntelliJLaf.install();
        }

        for (Window window : Window.getWindows()) {
            SwingUtilities.updateComponentTreeUI(window);
        }
    }

    public void getFileImport(JTextField jtf) {
        JFileChooser fileChooser = new JFileChooser(".");
        FileFilter txtFilter = new FileTypeFilter(".txt", "Arquivo de Texto");
        FileFilter csvFilter = new FileTypeFilter(".csv", "Excel separado por ponto e virgula");
        fileChooser.addChoosableFileFilter(txtFilter);
        fileChooser.addChoosableFileFilter(csvFilter);
        fileChooser.setFileFilter(csvFilter);
        int status = fileChooser.showOpenDialog(null);
        if (status == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            jtf.setText(selectedFile.getPath());
            logger.info("Selecionado arquivo:");
            logger.info(selectedFile.getPath());
            logger.info("");
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton jbImportClients;
    private javax.swing.JButton jbImportProducts;
    private javax.swing.JButton jbInfo;
    private javax.swing.JButton jbSeachFileProducts;
    private javax.swing.JButton jbSearchFileClients;
    private javax.swing.JCheckBox jcbClientesBairro;
    private javax.swing.JCheckBox jcbClientesCEP;
    private javax.swing.JCheckBox jcbClientesCPFCNPJ;
    private javax.swing.JCheckBox jcbClientesCelular;
    private javax.swing.JCheckBox jcbClientesCidade;
    private javax.swing.JCheckBox jcbClientesComplemento;
    private javax.swing.JCheckBox jcbClientesContato;
    private javax.swing.JCheckBox jcbClientesDtNasc;
    private javax.swing.JCheckBox jcbClientesEmail;
    private javax.swing.JCheckBox jcbClientesEndereco;
    private javax.swing.JCheckBox jcbClientesFantasia;
    private javax.swing.JCheckBox jcbClientesIEIM;
    private javax.swing.JCheckBox jcbClientesNome;
    private javax.swing.JCheckBox jcbClientesNumero;
    private javax.swing.JCheckBox jcbClientesTelefone;
    private javax.swing.JComboBox<com.ravc.simpleimport.models.ModelEmpresa> jcbEmpresa;
    private javax.swing.JCheckBox jcbHeaderExistsClient;
    private javax.swing.JCheckBox jcbHeaderExistsProducts;
    private javax.swing.JCheckBox jcbProdutosCFOP;
    private javax.swing.JCheckBox jcbProdutosClassFiscal;
    private javax.swing.JCheckBox jcbProdutosCodBarras;
    private javax.swing.JCheckBox jcbProdutosDescricao;
    private javax.swing.JCheckBox jcbProdutosEstAtual;
    private javax.swing.JCheckBox jcbProdutosEstMin;
    private javax.swing.JCheckBox jcbProdutosGrupo;
    private javax.swing.JCheckBox jcbProdutosMarca;
    private javax.swing.JCheckBox jcbProdutosRef;
    private javax.swing.JCheckBox jcbProdutosSitTrib;
    private javax.swing.JCheckBox jcbProdutosUnMed;
    private javax.swing.JCheckBox jcbProdutosVlrCusto;
    private javax.swing.JCheckBox jcbProdutosVlrVenda;
    private javax.swing.JLabel jlFormat;
    private javax.swing.JLabel jlInfoCidade;
    private javax.swing.JMenuItem jmiExit;
    private javax.swing.JPanel jpFields;
    private javax.swing.JPanel jpLog;
    private javax.swing.JProgressBar jpbProgress;
    private javax.swing.JRadioButtonMenuItem jrbmDark;
    private javax.swing.JRadioButtonMenuItem jrbmLight;
    private javax.swing.JTextArea jtaLogs;
    private javax.swing.JTextField jtfClientesBairro;
    private javax.swing.JTextField jtfClientesCEP;
    private javax.swing.JTextField jtfClientesCPFCNPJ;
    private javax.swing.JTextField jtfClientesCelular;
    private javax.swing.JTextField jtfClientesCidade;
    private javax.swing.JTextField jtfClientesComplemento;
    private javax.swing.JTextField jtfClientesContato;
    private javax.swing.JTextField jtfClientesDtNasc;
    private javax.swing.JTextField jtfClientesEmail;
    private javax.swing.JTextField jtfClientesEndereco;
    private javax.swing.JTextField jtfClientesFantasia;
    private javax.swing.JTextField jtfClientesIEIM;
    private javax.swing.JTextField jtfClientesNome;
    private javax.swing.JTextField jtfClientesNumero;
    private javax.swing.JTextField jtfClientesTelefone;
    private javax.swing.JTextField jtfFormatDateNasc;
    private javax.swing.JTextField jtfPathClientsFile;
    private javax.swing.JTextField jtfPathProductsFile;
    private javax.swing.JTextField jtfProdutosCFOP;
    private javax.swing.JTextField jtfProdutosClassFiscal;
    private javax.swing.JTextField jtfProdutosCodBarras;
    private javax.swing.JTextField jtfProdutosDescricao;
    private javax.swing.JTextField jtfProdutosEstAtual;
    private javax.swing.JTextField jtfProdutosEstMin;
    private javax.swing.JTextField jtfProdutosGrupo;
    private javax.swing.JTextField jtfProdutosMarca;
    private javax.swing.JTextField jtfProdutosRef;
    private javax.swing.JTextField jtfProdutosSitTrib;
    private javax.swing.JTextField jtfProdutosUnMed;
    private javax.swing.JTextField jtfProdutosVlrCusto;
    private javax.swing.JTextField jtfProdutosVlrVenda;
    // End of variables declaration//GEN-END:variables

    private void typed(KeyEvent evt, JTextField jtf) {
        if (!StringUtils.isAlpha(evt.getKeyChar() + "")
                || jtf.getText().length() >= 2) {
            evt.consume();
        }
    }
}
