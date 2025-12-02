import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AbsensiSiswaApp extends JFrame {

    private JTextField txtNIS;
    private JTextField txtNama;
    private JTextField txtMapel;
    private JTextField txtKelas;

    private JTable table;
    private DefaultTableModel tableModel;

    private JButton btnTambah;
    private JButton btnHapus;
    private JButton btnClear;

    private JComboBox<String> cmbStatus;
    private JButton btnSetStatus;

    public AbsensiSiswaApp() {
        setTitle("Sistem Absensi Siswa");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // di tengah

        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        headerPanel.setBackground(new Color(255, 225, 125)); // masih sedikit warna biar nggak polos banget

        JLabel lblTitle = new JLabel("Sistem Absensi Siswa");
        lblTitle.setForeground(Color.BLACK);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 22));

        JLabel lblSubtitle = new JLabel("Test");
        lblSubtitle.setForeground(Color.DARK_GRAY);
        lblSubtitle.setFont(new Font("SansSerif", Font.PLAIN, 13));

        headerPanel.add(lblTitle, BorderLayout.NORTH);
        headerPanel.add(lblSubtitle, BorderLayout.SOUTH);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Panel tengah input + tabel
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));

        // Panel Input
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Form Data Siswa"));
        inputPanel.setBackground(new Color(255, 252, 224));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel lblNIS = new JLabel("NIS:");
        JLabel lblNama = new JLabel("Nama Lengkap:");
        JLabel lblMapel = new JLabel("Mata Pelajaran:");
        JLabel lblKelas = new JLabel("Kelas:");

        txtNIS = new JTextField(12);
        txtNama = new JTextField(18);
        txtMapel = new JTextField(12);
        txtKelas = new JTextField(8);

        Font labelFont = new Font("SansSerif", Font.PLAIN, 13);
        lblNIS.setFont(labelFont);
        lblNama.setFont(labelFont);
        lblMapel.setFont(labelFont);
        lblKelas.setFont(labelFont);

        // Baris 0 - NIS
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(lblNIS, gbc);

        gbc.gridx = 1;
        inputPanel.add(txtNIS, gbc);

        // Baris 1 - Nama
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(lblNama, gbc);

        gbc.gridx = 1;
        inputPanel.add(txtNama, gbc);

        // Baris 2 - Mapel
        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(lblMapel, gbc);

        gbc.gridx = 1;
        inputPanel.add(txtMapel, gbc);

        // Baris 3 - Kelas
        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(lblKelas, gbc);

        gbc.gridx = 1;
        inputPanel.add(txtKelas, gbc);

        // Panel tombol input
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnTambah = new JButton("Tambah Siswa");
        btnHapus = new JButton("Hapus Baris Terpilih");
        btnClear = new JButton("Clear Input");

        buttonPanel.add(btnTambah);
        buttonPanel.add(btnHapus);
        buttonPanel.add(btnClear);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        inputPanel.add(buttonPanel, gbc);

        // Panel Input kiri
        centerPanel.add(inputPanel, BorderLayout.WEST);

        // Tabel
        String[] columnNames = {"NIS", "Nama Lengkap", "Mata Pelajaran", "Kelas", "Status Kehadiran"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // tidak bisa edit langsung di tabel
            }
        };

        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(22);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Data Siswa & Absensi"));

        centerPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Absensi
        JPanel absensiPanel = new JPanel();
        absensiPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        absensiPanel.setBorder(BorderFactory.createTitledBorder("Panel Absensi (Guru/Admin)"));

        JLabel lblInfo = new JLabel("Pilih siswa di tabel, lalu set status kehadiran: ");
        String[] statusOptions = {"Pilih Status", "Hadir", "Izin", "Sakit", "Alpa"};
        cmbStatus = new JComboBox<>(statusOptions);
        btnSetStatus = new JButton("Set Status Kehadiran");

        absensiPanel.add(lblInfo);
        absensiPanel.add(cmbStatus);
        absensiPanel.add(btnSetStatus);

        mainPanel.add(absensiPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);

        // ================= EVENT LISTENER =================
        btnTambah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tambahDataSiswa();
            }
        });

        btnHapus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hapusBarisTerpilih();
            }
        });

        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearInput();
            }
        });

        btnSetStatus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setStatusKehadiran();
            }
        });
    }

    private void tambahDataSiswa() {
        String nis = txtNIS.getText().trim();
        String nama = txtNama.getText().trim();
        String mapel = txtMapel.getText().trim();
        String kelas = txtKelas.getText().trim();

        if (nis.isEmpty() || nama.isEmpty() || mapel.isEmpty() || kelas.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Semua field harus diisi!",
                    "Peringatan",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Object[] rowData = {nis, nama, mapel, kelas, "-"};
        tableModel.addRow(rowData);
        clearInput();
    }

    private void hapusBarisTerpilih() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Pilih baris yang ingin dihapus terlebih dahulu!",
                    "Informasi",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Yakin ingin menghapus data ini?",
                "Konfirmasi",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            tableModel.removeRow(selectedRow);
        }
    }

    private void clearInput() {
        txtNIS.setText("");
        txtNama.setText("");
        txtMapel.setText("");
        txtKelas.setText("");
        txtNIS.requestFocus();
    }

    private void setStatusKehadiran() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Pilih dulu siswa di tabel yang akan di-set absensinya!",
                    "Informasi",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String statusDipilih = (String) cmbStatus.getSelectedItem();
        if (statusDipilih == null || statusDipilih.equals("Pilih Status")) {
            JOptionPane.showMessageDialog(this,
                    "Silakan pilih status kehadiran: Hadir / Izin / Sakit / Alpa",
                    "Peringatan",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        tableModel.setValueAt(statusDipilih, selectedRow, 4);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AbsensiSiswaApp app = new AbsensiSiswaApp();
            app.setVisible(true);
        });
    }
}
