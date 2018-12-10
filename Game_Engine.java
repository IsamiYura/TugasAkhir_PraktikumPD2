/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Responsi2;

/**
 *
 * @author Asus
 */
import java.sql.*;
import java.util.*;

class Character implements Comparable<Character> { //class untuk membuat object character

    int index;
    int comp;

    public Character(int i, int c) {
        index = i;
        comp = c;

    }

    @Override
    public int compareTo(Character o) {
        int comparedSize = o.comp;
        if (this.comp > comparedSize) {
            return 1;
        } else if (this.comp == comparedSize) {
            return 0;
        } else {
            return -1;
        }
    }

    public int toInt() {
        return index;
    }

}

public class Game_Engine {

    int current;
    int selector;
    Scanner input = new Scanner(System.in);
    Hashtable charaHash = new Hashtable();
    int loopChance = 3;
    LinkedList<Character> charaStat = new LinkedList<>();
    char ubah;
    char ubah_main;
    int temp;
    int score;
    int kumpulanHP[] = new int[5];

    int selector2;
    String sql;
    ResultSet rs;
    int id_up;
    char dec;

    int isi = 1;

    int mov;
    int mov1;
    int mov2;
    int mov3;
    int mov4;

    int bantuan;

    boolean loop = true;

    int con_db;

    void menu() {
        try {
            charaDBtoHash();
            System.out.println("-----------------------");
            System.out.println("--- Zombie Survival ---");
            System.out.println("-----------------------");
            System.out.println("1. New Game");
            System.out.println("2. Survivor List");
            System.out.println("3. Rules and Gameplay");
            System.out.println("0. Exit");
            System.out.println("Pilih : ");
            selector = input.nextInt();
            if (selector == 1) {
                charaDBtoHash();
                characterSelect(); //masuk ke chara. select lalu start game
            } else if (selector == 2) {
                survivorDB();
            } else if (selector == 3) {
                rules(); //masuk ke fungsi rules yang isinya aturan + cara kerja game
            } else if (selector == 0) {
                System.exit(0);
            } else {
                System.out.println("Masukkan anda salah, silahkan isi sesuai pilihan yang ada!");
                System.out.println("--------------------");
                charaDBtoHash();
                menu();
                //untuk eksepsi apabila angka yang diinput tidak ada pada if
            }
        } catch (InputMismatchException e) {
            System.out.println("Format isian anda salah, pilih menu sesuai angka yang ada!");
            System.out.println("----------------------------------");
            charaDBtoHash();
            menu();
            //untuk eksepsi apabila yang diinput bukan angka
        }
    } // menu utama

    void printLinkedList() {
        if (loop) {
            for (int z = 1; z < 5; z++) {
                int indexnya = charaStat.element().index;
                int isinya = charaStat.element().comp;
                System.out.println("| " + charaHash.get(indexnya) + " | Comp. > " + isinya);
                if (z == 4) {
                    System.out.println("");
                    bantuan = indexnya;
                    hpDown();
                    System.out.println("HP dari " + charaHash.get(bantuan) + " berkurang");
                }

                charaStat.pop();

            }

            showHP();
            randomNumberMainChara();
        } else {

            System.out.println("Game Over!");
            System.out.println("Your score = " + score);
            updateSurvivorScore();
            confirmPlayAgain();
        }
    }

    void hpDown() {
        if (bantuan == 1) {

            kumpulanHP[1] -= 1;

        } else if (bantuan == 2) {
            kumpulanHP[2] -= 1;

        } else if (bantuan == 3) {
            kumpulanHP[3] -= 1;

        } else if (bantuan == 4) {
            kumpulanHP[4] -= 1;

        }
    }

    void stillPlay() {
        if (current == 1) {
            if (kumpulanHP[1] > 0) {
                loop = true;
            } else if (kumpulanHP[1] <= 0) {
                System.out.println("Game Over!");
                System.out.println("Your score = " + score);
                updateSurvivorScore();
                confirmPlayAgain();

            }
        } else if (current == 2) {
            if (kumpulanHP[2] > 0) {
                loop = true;
            } else if (kumpulanHP[2] <= 0) {
                System.out.println("Game Over!");
                System.out.println("Your score = " + score);
                updateSurvivorScore();
                confirmPlayAgain();

            }
        } else if (current == 3) {
            if (kumpulanHP[3] > 0) {
                loop = true;
            } else if (kumpulanHP[3] <= 0) {
                System.out.println("Game Over!");
                System.out.println("Your score = " + score);
                updateSurvivorScore();
                confirmPlayAgain();

            }
        } else if (current == 4) {
            if (kumpulanHP[4] > 0) {
                loop = true;
            } else if (kumpulanHP[4] <= 0) {
                System.out.println("Game Over!");
                System.out.println("Your score = " + score);
                updateSurvivorScore();
                confirmPlayAgain();
            }
        }
    }

    void calculateShow1() {
        if (current == 1) { //ketika berperan sebagai Player
            if (kumpulanHP[1] > 0) {
                System.out.println("Nyawa " + charaHash.get(1) + " = " + kumpulanHP[1]);
            } else if (kumpulanHP[1] <= 0) {
                System.out.println("Game Over!");
                System.out.println("Your score = " + score);
                updateSurvivorScore();
                confirmPlayAgain();

            }
        } else if (current != 1) { //ketika berperan sebagai AI
            if (kumpulanHP[1] > 0) {
                System.out.println("Nyawa " + charaHash.get(1) + " = " + kumpulanHP[1]);
            } else if (kumpulanHP[1] <= 0) {
                System.out.println(charaHash.get(1) + " wasted!");
            }
        }
    }

    void calculateShow2() {
        if (current == 2) { //ketika berperan sebagai Player
            if (kumpulanHP[2] > 0) {
                System.out.println("Nyawa " + charaHash.get(2) + " = " + kumpulanHP[2]);
            } else if (kumpulanHP[2] <= 0) {
                System.out.println("Game Over!");
                System.out.println("Your score = " + score);
                updateSurvivorScore();
                confirmPlayAgain();
            }
        } else if (current != 2) { //ketika berperan sebagai AI
            if (kumpulanHP[2] > 0) {
                System.out.println("Nyawa " + charaHash.get(2) + " = " + kumpulanHP[2]);
            } else if (kumpulanHP[2] <= 0) {
                System.out.println(charaHash.get(2) + " wasted!");
            }
        }
    }

    void calculateShow3() {
        if (current == 3) { //ketika berperan sebagai Player
            if (kumpulanHP[3] > 0) {
                System.out.println("Nyawa " + charaHash.get(3) + " = " + kumpulanHP[3]);
            } else if (kumpulanHP[3] <= 0) {
                System.out.println("Game Over!");
                System.out.println("Your score = " + score);
                updateSurvivorScore();
                confirmPlayAgain();
            }
        } else if (current != 3) { //ketika berperan sebagai AI
            if (kumpulanHP[3] > 0) {
                System.out.println("Nyawa " + charaHash.get(3) + " = " + kumpulanHP[3]);
            } else if (kumpulanHP[3] <= 0) {
                System.out.println(charaHash.get(3) + " wasted!");
            }
        }
    }

    void calculateShow4() {
        if (current == 4) { //ketika berperan sebagai Player
            if (kumpulanHP[4] > 0) {
                System.out.println("Nyawa " + charaHash.get(4) + " = " + kumpulanHP[4]);
            } else if (kumpulanHP[4] <= 0) {
                System.out.println("Game Over!");
                System.out.println("Your score = " + score);
                updateSurvivorScore();
                confirmPlayAgain();
            }
        } else if (current != 4) { //ketika berperan sebagai AI
            if (kumpulanHP[4] > 0) {
                System.out.println("Nyawa " + charaHash.get(4) + " = " + kumpulanHP[4]);
            } else if (kumpulanHP[4] <= 0) {
                System.out.println(charaHash.get(4) + " wasted!");
            }
        }
    }

    void showHP() {
        calculateShow1();
        calculateShow2();
        calculateShow3();
        calculateShow4();

    }

    void randomCondition() {
        if (current == 1) {
            score += 1;
            loopChance = 3;
            inputCompIndex2();
            inputCompIndex3();
            inputCompIndex4();
            Collections.sort(charaStat, Collections.reverseOrder());
            if (kumpulanHP[2] <= 0 && kumpulanHP[3] <= 0 && kumpulanHP[4] <= 0) {
                System.out.println("You win!");
                System.out.println("Score anda = " + score);
                updateSurvivorScore();
                confirmPlayAgain();
            }
            stillPlay();
            printLinkedList();

        } else if (current == 2) {
            score += 1;
            loopChance = 3;
            inputCompIndex1();
            inputCompIndex3();
            inputCompIndex4();
            Collections.sort(charaStat, Collections.reverseOrder());
            if (kumpulanHP[1] <= 0 && kumpulanHP[3] <= 0 && kumpulanHP[4] <= 0) {
                System.out.println("You win!");
                System.out.println("Score anda = " + score);
                updateSurvivorScore();
                confirmPlayAgain();
            }
            stillPlay();
            printLinkedList();

        } else if (current == 3) {
            score += 1;
            loopChance = 3;
            inputCompIndex1();
            inputCompIndex2();
            inputCompIndex4();
            Collections.sort(charaStat, Collections.reverseOrder());
            if (kumpulanHP[1] <= 0 && kumpulanHP[2] <= 0 && kumpulanHP[4] <= 0) {
                System.out.println("You win!");
                System.out.println("Score anda = " + score);
                updateSurvivorScore();
                confirmPlayAgain();
            }
            stillPlay();
            printLinkedList();

        } else if (current == 4) {
            score += 1;
            loopChance = 3;
            inputCompIndex1();
            inputCompIndex2();
            inputCompIndex3();
            Collections.sort(charaStat, Collections.reverseOrder());
            if (kumpulanHP[1] <= 0 && kumpulanHP[2] <= 0 && kumpulanHP[3] <= 0) {
                System.out.println("You win!");
                System.out.println("Score anda = " + score);
                updateSurvivorScore();
                confirmPlayAgain();
            }
            stillPlay();
            printLinkedList();

        }

    } //func. untuk inisialisasi status

    void inputCompIndex1() {
        randomNumberAI();
        mov1 = mov;
        charaStat.add(new Character(1, mov1));
    }

    void inputCompIndex2() {
        randomNumberAI();
        mov2 = mov;
        charaStat.add(new Character(2, mov2));
    }

    void inputCompIndex3() {
        randomNumberAI();
        mov3 = mov;
        charaStat.add(new Character(3, mov3));
    }

    void inputCompIndex4() {
        randomNumberAI();
        mov4 = mov;
        charaStat.add(new Character(4, mov4));
    }

    void randomNumberAI() {
        int max = 10;
        int min = 1;
        int randomize = (int) (Math.random() * ((max - min) + 1)) + min;
        mov = randomize;

    }

    void charaDBtoHash() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Scanner input2 = new Scanner(System.in);
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/zombie", "root", "461999");
            Statement stmt = conn.createStatement();
            sql = "SELECT name FROM survivor order by id";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                if (isi < 5) {
                    String name = rs.getString("name");
                    charaHash.put(isi, name);
                    isi = isi + 1;
                }
                //charaHash menyimpan angka sebagai index dari LinkedList nanti
            }
            isi = 1;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("terserah");
        }
    } //func. untuk simpan index player dan nama

    void characterSelect() {
        try {

            charaDBtoHash();
            System.out.println("");
            System.out.println("=============================");
            System.out.println("--- Select Your Character ---");
            System.out.println("=============================");
            for (int tampil = 1; tampil <= 4; tampil++) {
                Object dll = charaHash.get(tampil);
                System.out.println(" |" + tampil + "| " + dll);
            }
            System.out.println("5. Custom Character");
            System.out.println("0. Back to Main Menu");
            System.out.print("Pilih : ");
            selector = input.nextInt();
            current = selector;
            if (selector == 5) {
                changeNameMenu();
            } else if (selector == 0) {
                menu();
            } else if (selector < 0) {
                System.out.println("Pilihan anda tidak ada di daftar atau format salah. Silahkan pilih kembali!");
                characterSelect();
            } else if (selector > 5) {
                System.out.println("Pilihan anda tidak ada di daftar atau format salah. Silahkan pilih kembali!");
                characterSelect();
            } else {
                System.out.println("Anda akan bermain sebagai " + charaHash.get(selector) + " !");
                kumpulanHP[1] = 5;
                kumpulanHP[2] = 5;
                kumpulanHP[3] = 5;
                kumpulanHP[4] = 5;
                randomNumberMainChara();
                //pada saat index Hashtable dipanggil, maka index ini juga dapat menampilkan nama pemain
            }
        } catch (InputMismatchException e2) {
            System.out.println("Pilihan anda tidak ada di daftar atau format salah. Silahkan pilih kembali!");
        }
    } // func. untuk pilih karakter

    void rules() {
        System.out.println("==========================");
        System.out.println("--- Rules and Gameplay ---");
        System.out.println("==========================");
        System.out.println("1. Dalam game ini, anda akan bermain sebagai salah satu survivor");
        System.out.println("2. Satu round akan berisi 1 pemain dan 3 AI, beserta 1 AI Zombie");
        System.out.println("3. Tugas anda adalah bertahan hingga anda menjadi survivor terakhir");
        System.out.println("4. Setiap character memiliki 3 HP");
        System.out.println("4. Tiap giliran anda dapat mengacak angka antara 1 - 10 sebanyak 3 kali");
        System.out.println("5. Usahakan untuk mendapat angka tertinggi 5, agar terus berada di depan");
        System.out.println("6. Pemain dengan angka terkecil akan dipindah ke paling belakang");
        System.out.println("7. Pemain yang terletak paling belakang akan secara otomatis kehilangan 1 nyawa");
        System.out.println("8. Game berakhir apabila karakter yang dipilih pemain berhasil bertahan ataupun kehabisan HP");
        System.out.println("=====================================================================");
        menu();
    } // berisi peraturan permainan

    void survivorDB() {
        try {
            Scanner input2 = new Scanner(System.in);
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/zombie", "root", "461999");
            Statement stmt = conn.createStatement();
            sql = "SELECT name, score FROM survivorScore order by score";
            rs = stmt.executeQuery(sql);
            System.out.println("------------------------");
            System.out.println("--- Survivor History ---");
            System.out.println("------------------------");
            System.out.println(" | Name | Score |");
            while (rs.next()) {
                String name = rs.getString("name");
                int score = rs.getInt("score");
                System.out.println(" |" + name + "| " + score);
                System.out.println("-----------------------");
            }
            System.out.println("2. Clear Score");
            System.out.println("0. Back to Main Menu");
            System.out.println("Input : ");
            con_db = input.nextInt();
            if (con_db == 2) {
                sql = "delete from survivorscore";
                stmt.executeUpdate(sql);
                survivorDB();
            } else if (con_db == 0) {
                menu();
            }

            System.out.println("");
        } catch (Exception e4) {
            System.out.println(e4.getMessage());
        }
    }

    void randomNumberMainChara() {
        int roll;
        int min = 1;
        int max = 10;
        System.out.println("==============");
        try {
            do {
                if (loopChance != 0) {
                    System.out.println("--------------------------------------");
                    System.out.println("Anda punya " + loopChance + " kesempatan acak angka!");
                    temp = (int) (Math.random() * ((max - min) + 1)) + min;
                    charaStat.add(new Character(current, temp));
                    charaStat.element().comp = temp;
                    System.out.println("Angka " + charaHash.get(current) + " round ini adalah = " + temp);
                    System.out.println("--------------------------------------");
                    loopChance -= 1;
                    System.out.println("Acak lagi? Y/N");
                    ubah = input.next().charAt(0);
                    confirmLoop();
                } else if (loopChance == 0) {
                    System.out.println("Kesempatan acak ronde ini habis!");
                    temp = (int) (Math.random() * ((max - min) + 1)) + min;
                    charaStat.add(new Character(current, temp));
                    charaStat.element().comp = temp;
                    System.out.println("Angka " + charaHash.get(current) + " round ini adalah = " + temp);
                    System.out.println("--------------------------------------");
                    randomCondition();
                }
            } while (loopChance != 0);
        } catch (InputMismatchException e3) {
            System.out.println("Berhenti mengulang");
        }
    }

    void confirmLoop() {
        if (ubah == 'Y' || ubah == 'y') {
            charaStat.clear();
            randomNumberMainChara();
        } else if (ubah == 'n' || ubah == 'N') {
            randomCondition();
        } else {
            System.out.println("Masukkan salah, silahkan ");

        }
    }

    void changeNameMenu() {
        try {
            Scanner input2 = new Scanner(System.in);
            System.out.println("");
            System.out.println("------------------------");
            System.out.println("--- Custom Character ---");
            System.out.println("------------------------");
            System.out.println("1. Change Name");
            System.out.println("2. View Character List");
            System.out.println("0. Exit");
            System.out.println("Input : ");
            selector2 = input2.nextInt();

            if (selector2 == 1) {
                changeNameEngine();
            } else if (selector2 == 2) {
                viewNameEngine();
            } else if (selector2 == 0) {
                charaDBtoHash();
                menu();
            }

        } catch (Exception e) {
            System.out.println("Input anda salah, mohon coba kembali!");
        }

    }

    void changeNameEngine() {
        try {
            Scanner input2 = new Scanner(System.in);
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/zombie", "root", "461999");
            Statement stmt = conn.createStatement();
            sql = "SELECT id, name FROM survivor order by id";
            rs = stmt.executeQuery(sql);
            System.out.println("");
            System.out.println("-----------------------");
            System.out.println("--- Character List ---");
            System.out.println("-----------------------");

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                System.out.println(" |" + id + "| " + name);

            }
            System.out.println("-----------------------");
            System.out.println("");
            System.out.print("Masukkan ID dari character yang akan diubah namanya :");
            id_up = input2.nextInt();
            System.out.print("Nama baru : ");
            String sisa = input2.nextLine();
            String new_name = input2.nextLine();
            sql = "UPDATE survivor set name='" + new_name + "' WHERE id=" + id_up;
            stmt.executeUpdate(sql);
            System.out.println("Nama karakter berhasil diganti!");
            charaDBtoHash();
            System.out.println("Ubah nama lain? Y / N");
            dec = input2.next().charAt(0);
            if (dec == 'Y' || dec == 'y') {
                charaDBtoHash();
                changeNameEngine();
            } else if (dec == 'N' || dec == 'n') {
                charaDBtoHash();
                changeNameMenu();
            }
        } catch (Exception e2) {
            System.out.println(e2.getMessage());
        }

    }

    void viewNameEngine() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/zombie", "root", "461999");
            Statement stmt = conn.createStatement();
            System.out.println("-----------------------");
            System.out.println("--- Character List ---");
            System.out.println("-----------------------");
            sql = "SELECT id, name FROM survivor order by id";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                System.out.println(" |" + id + "| " + name);

            }
            System.out.println("-----------------------");
            charaDBtoHash();
            changeNameMenu();

        } catch (Exception e5) {
            System.out.println(e5.getMessage());

        }
    }

    void updateSurvivorScore() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/zombie", "root", "461999");
            Statement stmt = conn.createStatement();
            int isian_score = score;
            sql = "INSERT INTO survivorscore (name,score) VALUES ('" + charaHash.get(current) + "','" + isian_score + "')";
            stmt.executeUpdate(sql);

        } catch (Exception z) {
            System.out.println(z.getMessage());
        }
    }

    void confirmPlayAgain() {
        System.out.println("--------------");
        System.out.println("Main lagi? Y/N");
        ubah_main = input.next().charAt(0);
        if (ubah_main == 'Y' || ubah_main == 'y') {
            characterSelect();
        } else if (ubah == 'n' || ubah == 'N') {
            System.out.println("Terima kasih telah bermain!");
            System.exit(0);
        } else {
            System.out.println("Masukkan salah, silahkan masukkan lagi!");
            confirmPlayAgain();
        }
    }

}
