package ru.unn.particleanalysis.output;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CSV {
    private List<String> columnNames;
    private List<Row> rows;

    public CSV(List<String> columnNames) {
        this.columnNames = new ArrayList<>(columnNames);
        this.rows = new ArrayList<>();
    }

    public void addRow(Object... values) {
        rows.add(new Row(values));
    }

    public Row getRow(int index) {
        return rows.get(index);
    }

    public void to_file(String path) throws IOException {
        try(FileWriter fos = new FileWriter(path);
            BufferedWriter bos = new BufferedWriter(fos)) {
            String header = String.join(";", columnNames);
            bos.write(header);
            for (Row row : rows) {
                bos.newLine();
                bos.write(row.toString());
            }
        }
    }

    public class Row {
        private Object[] values;

        public Row(Object... values) {
            if (values.length != columnNames.size()) {
                throw new IllegalArgumentException(String.format("Wrong arguments count: %d, should be %d",
                        values.length, rows.size()));
            }
            this.values = Arrays.copyOf(values, values.length);
        }

        public int check_index(int i) {
            if (i < 0 || i >= values.length) {
                throw new IndexOutOfBoundsException(String.format("Index %d is out of range %d", i, values.length));
            }
            return i;
        }

        public int get_index_of_col(String name) {
            int i = columnNames.indexOf(name);
            if (i == -1) {
                throw new IllegalArgumentException(String.format("There is no column %s, only: (%s)", name,
                        columnNames.toString()));
            }
            return i;
        }

        public Object get(int i) {
            return values[check_index(i)];
        }

        public Object get(String name) {
            return values[get_index_of_col(name)];
        }

        public void set(int i, Object value) {
            values[check_index(i)] = value;
        }

        public void set(String name, Object value) {
            values[get_index_of_col(name)] = value;
        }

        @Override
        public String toString() {
            return Arrays.stream(values).map(String::valueOf).collect(Collectors.joining(";"));
        }
    }
}
