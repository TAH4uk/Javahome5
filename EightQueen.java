import java.util.Arrays;

class EightQueen {
    // Функция для проверки, угрожают ли два ферзя друг другу или нет
    private static boolean isSafe(char[][] mat, int r, int c) {
        // возвращаем false, если два ферзя делят один и тот же столбец
        for (int i = 0; i < r; i++) {
            if (mat[i][c] == 'Q') {
                return false;
            }
        }

        // вернуть false, если два ферзя делят одну и ту же диагональ ``
        for (int i = r, j = c; i >= 0 && j >= 0; i--, j--) {
            if (mat[i][j] == 'Q') {
                return false;
            }
        }

        // вернуть false, если два ферзя делят одну и ту же диагональ `/`
        for (int i = r, j = c; i >= 0 && j < mat.length; i--, j++) {
            if (mat[i][j] == 'Q') {
                return false;
            }
        }

        return true;
    }

    private static void printSolution(char[][] mat) {
        for (char[] chars : mat) {
            System.out.println(Arrays.toString(chars).replaceAll(",", ""));
        }
        System.out.println();
    }

    private static void nQueen(char[][] mat, int r) {
        // если `N` ферзей расставлены успешно, выводим решение
        if (r == mat.length) {
            printSolution(mat);
            return;
        }

        // помещаем ферзя на каждую клетку в текущем ряду `r`
        // и повторяться для каждого допустимого движения
        for (int i = 0; i < mat.length; i++) {
            // если никакие два ферзя не угрожают друг другу
            if (isSafe(mat, r, i)) {
                // ставим ферзя на текущую клетку
                mat[r][i] = 'Q';

                // повторить для следующей строки
                nQueen(mat, r + 1);

                // возвращаемся назад и удаляем ферзя с текущей клетки
                mat[r][i] = '–';
            }
        }
    }

    public static void main(String[] args) {
        // Шахматная доска `N × N`
        int N = 8;

        // `mat[][]` отслеживает положение дам в
        // текущая конфигурация
        char[][] mat = new char[N][N];

        // инициализируем `mat[][]` с помощью `-`
        for (int i = 0; i < N; i++) {
            Arrays.fill(mat[i], '–');
        }

        nQueen(mat, 0);
    }
}