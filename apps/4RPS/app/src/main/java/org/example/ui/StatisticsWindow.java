package result;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.*;

public class StatisticsWindow extends JFrame {
    private final JFrame menuWindow;
    private static final String STATISTICS_DIR_PATH = System.getProperty("user.home") + "/Gits/C3S2/apps/4RPS/results";

    public StatisticsWindow(JFrame menuWindow) {
        this.menuWindow = menuWindow;

        setTitle("Статистика");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Список файлів
        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> fileList = new JList<>(listModel);
        JScrollPane listScrollPane = new JScrollPane(fileList);
        listScrollPane.setPreferredSize(new Dimension(200, 0));
        loadFileList(listModel);

        // Область для тексту
        JTextArea statisticsText = new JTextArea("Виберіть файл зі списку зліва");
        statisticsText.setEditable(false);
        statisticsText.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane textScrollPane = new JScrollPane(statisticsText);

        // Обробка вибору файла
        fileList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String fileName = fileList.getSelectedValue();
                if (fileName != null) {
                    Path filePath = Paths.get(STATISTICS_DIR_PATH, fileName);
                    try {
                        String content = Files.readString(filePath);
                        statisticsText.setText(content);
                    } catch (IOException ex) {
                        statisticsText.setText("Не вдалося завантажити файл:\n" + ex.getMessage());
                    }
                }
            }
        });

        // Кнопка Назад
        JButton backButton = new JButton("Назад в меню");
        backButton.addActionListener(e -> {
            menuWindow.setVisible(true);
            dispose();
        });

        // Збірка панелей
        mainPanel.add(listScrollPane, BorderLayout.WEST);
        mainPanel.add(textScrollPane, BorderLayout.CENTER);
        mainPanel.add(backButton, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void loadFileList(DefaultListModel<String> model) {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(STATISTICS_DIR_PATH), "*.txt")) {
            for (Path entry : stream) {
                model.addElement(entry.getFileName().toString());
            }
        } catch (IOException e) {
            model.addElement("Помилка при завантаженні файлів: " + e.getMessage());
        }
    }
}
