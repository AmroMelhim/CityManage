import java.util.*;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.*;

public class driver extends Application {
	public static AVLtree tree = new AVLtree();
	private TableView table = new TableView();
	public String chosenCity;
	public HashMap hash;

	public static void main(String[] args) throws IOException {

		cityFile(tree);

		launch(args);

	}

	public static void cityFile(AVLtree tree) throws IOException {
		File file = new File("cities.txt");
		// define a buffer reader to read text
		BufferedReader br = new BufferedReader(new FileReader(file));
		try {
			String line;

			while ((line = br.readLine()) != null) {
				String[] str = line.split("/");
				City city = new City(str[0], str[0] + ".txt");
				tree.insert(city);

			}
		}

		finally {
			br.close();
		}

	}

	public static boolean searchCity(String data) {
		return tree.search(data);

	}

	public static void insertCity(String data) {

		tree.insert(data);

	}

	public static void printCity() {
		tree.printTree();

	}

	public static void deleteCity(String data) {
		tree.delete(data);
	}

	// saves the cities from AVL tree to the cities file
	public static void saveCity() {
		try {
			FileWriter fileWriter = new FileWriter("cities.txt");
			fileWriter.write(tree.toStringFile());
			fileWriter.flush();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}

	}

	public static HashMap chooseCity(String city) throws IOException {
		return resFile(tree, city);

	}

	public static HashMap resFile(AVLtree tree, String city) throws IOException {
		File file1 = new File(tree.getFile(city));
		BufferedReader reader = new BufferedReader(new FileReader(file1));
		int count = 0;
		while (reader.readLine() != null)
			count++;
		reader.close();
		count = count*2;
		int size = nextPrime(count);
		HashMap hash = new HashMap(size);

		// define a buffer reader to read text
		BufferedReader br1 = new BufferedReader(new FileReader(file1));
		try {
			String line;
			while ((line = br1.readLine()) != null) {
				String[] str = line.split("/");
				Resident res = new Resident();
				res.setId(Integer.parseInt(str[0]));
				res.setName(str[1]);
				res.setAge(Integer.parseInt(str[2]));
				res.setGender(str[3]);
				hash.insert(res.getId(), res);

			}

		}

		finally {
			br1.close();
		}

		// printHash(hash);
		return hash;
	}

	// method to get the next prime number
	private static int nextPrime(int n) {
		if (n % 2 == 0)
			n++;
		for (; !isPrime(n); n += 2)
			;
		return n;
	}

	// method to check if the number is prime
	private static boolean isPrime(int n) {
		if (n == 2 || n == 3)
			return true;
		if (n == 1 || n % 2 == 0)
			return false;
		for (int i = 3; i * i <= n / 2; i += 2)
			if (n % i == 0)
				return false;
		return true;
	}

	// prints hash table inculding empty spots
	public static void printHash(HashMap hash) {
		System.out.print(hash.toString());

	}

	public static void saveHash(HashMap hash, String city) {
		try {
			FileWriter fileWriter = new FileWriter(tree.getFile(city));
			fileWriter.write(hash.toStringFile());
			fileWriter.flush();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}

	}

	public static void insertRes(String[] res, HashMap hash) {
		Resident value = new Resident(Integer.parseInt(res[0]), res[1], Integer.parseInt(res[2]), res[3]);
		hash.insert(Integer.parseInt(res[0]), value);
	}

	@Override
	public void start(Stage primaryStage) throws IOException {

		GridPane main1 = new GridPane();
		Scene scene1 = new Scene(main1, 1000, 280);
		TextArea ta1 = new TextArea();

		GridPane main = new GridPane();

		TextArea ta = new TextArea();
		ta.setText(tree.toString());
		main.add(ta, 0, 0);

		GridPane right = new GridPane();

		TextField tfinsert = new TextField();
		Button insert = new Button("INSERT");
		insert.setFont(new Font("New Times Roman", 15));
		insert.setPrefSize(100, 30);
		right.add(tfinsert, 0, 0);
		right.add(insert, 1, 0);

		insert.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				String insertStr = tfinsert.getText();
				tree.insert(insertStr);
				saveCity();
				ta.setText(tree.toString());

			}

		});

		TextField tfdelete = new TextField();
		Button delete = new Button("DELETE");
		delete.setFont(new Font("New Times Roman", 15));
		delete.setPrefSize(100, 30);
		right.add(tfdelete, 0, 1);
		right.add(delete, 1, 1);

		delete.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				String deleteStr = tfdelete.getText();
				tree.delete(deleteStr);
				saveCity();
				ta.setText(tree.toString());

			}

		});

		TextField tfsearch = new TextField();
		Button search = new Button("SEARCH");
		search.setFont(new Font("New Times Roman", 15));
		search.setPrefSize(100, 30);
		right.add(tfsearch, 0, 2);
		right.add(search, 1, 2);

		search.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				String searchStr = tfsearch.getText();
				VBox vert = new VBox();
				if (tree.search(searchStr) == true) {

					vert.getChildren().add(new Label("Found"));

				} else {
					vert.getChildren().add(new Label("Not Found"));

				}
				Scene scene = new Scene(vert);
				Stage stage = new Stage();
				stage.setScene(scene);
				stage.show();

			}
		});

		TextField tfchoose = new TextField();
		Button choose = new Button("CHOOSE");
		choose.setFont(new Font("New Times Roman", 15));
		choose.setPrefSize(100, 30);
		right.add(tfchoose, 0, 3);
		right.add(choose, 1, 3);

		choose.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				try {
					chosenCity = tfchoose.getText();
					hash = chooseCity(chosenCity);
					ta1.setText(hash.toString());

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

		Button next = new Button("NEXT");
		next.setFont(new Font("New Times Roman", 15));
		next.setPrefSize(100, 30);
		right.add(next, 1, 4);
		next.setOnAction(e -> primaryStage.setScene(scene1));

		Button calculate = new Button("CALCULATE");
		calculate.setFont(new Font("New Times Roman", 15));
		calculate.setPrefSize(100, 30);
		right.add(calculate, 0, 4);
		calculate.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				VBox vert = new VBox();
				vert.getChildren().add(new Label(String.valueOf(tree.height())));
				Scene scene = new Scene(vert);
				Stage stage = new Stage();
				stage.setScene(scene);
				stage.show();

			}

		});

		main.add(right, 1, 0);
/////////////////////////////////////////////////////////

		main1.add(ta1, 0, 0);

		GridPane right1 = new GridPane();

		Button printsize = new Button("Print table size");
		printsize.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {

				VBox vert1 = new VBox();

				vert1.getChildren().add(new Label(String.valueOf(hash.getTableSize())));

				Scene scene = new Scene(vert1);
				Stage stage = new Stage();
				stage.setScene(scene);
				stage.show();

			}
		});

		right1.add(printsize, 0, 0);

		Button printout = new Button("Print hash function");
		printout.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {

				VBox vert1 = new VBox();

				vert1.getChildren().add(new Label(String.valueOf("Key = ID + (i*i++) % " + hash.getTableSize())));

				Scene scene = new Scene(vert1);
				Stage stage = new Stage();
				stage.setScene(scene);
				stage.show();

			}
		});

		right1.add(printout, 0, 1);

		HBox hb1 = new HBox();
		TextField tf1 = new TextField();
		TextField tf2 = new TextField();
		hb1.getChildren().addAll(tf1, tf2);
		right1.add(hb1, 0, 2);

		HBox hb2 = new HBox();
		TextField tf3 = new TextField();
		TextField tf4 = new TextField();
		hb2.getChildren().addAll(tf3, tf4);
		right1.add(hb2, 1, 2);

		Button insert1 = new Button("INSERT");
		insert1.setFont(new Font("New Times Roman", 15));
		insert1.setPrefSize(100, 30);
		right1.add(insert1, 2, 2);
		insert1.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				String[] str = { "0", "empty", "0", "empty" };
				str[0] = tf1.getText();
				str[1] = tf2.getText();
				str[2] = tf3.getText();
				str[3] = tf4.getText();
				System.out.print(str[0]);
				insertRes(str, hash);
				ta1.setText(hash.toString());
				System.out.print(hash.toString());
			}

		});

		TextField tfsearch1 = new TextField();
		Button search1 = new Button("SEARCH");
		search1.setFont(new Font("New Times Roman", 15));
		search1.setPrefSize(100, 30);
		right1.add(tfsearch1, 0, 3);
		right1.add(search1, 1, 3);
		search1.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				String searchStr = tfsearch1.getText();
				VBox vert1 = new VBox();
				if (hash.contains(Integer.parseInt(searchStr)) == true) {

					vert1.getChildren().add(new Label("Found"));

				} else {
					vert1.getChildren().add(new Label("Not Found"));

				}

				Scene scene = new Scene(vert1);
				Stage stage = new Stage();
				stage.setScene(scene);
				stage.show();

			}
		});

		TextField tfdelete1 = new TextField();
		Button delete1 = new Button("DELETE");
		delete1.setFont(new Font("New Times Roman", 15));
		delete1.setPrefSize(100, 30);
		right1.add(tfdelete1, 0, 4);
		right1.add(delete1, 1, 4);
		delete1.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				String deleteStr = tfdelete1.getText();
				hash.remove(Integer.parseInt(deleteStr));
				ta1.setText(hash.toString());
				System.out.print(hash.toString());

			}
		});

		Button save = new Button("SAVE");
		save.setFont(new Font("New Times Roman", 15));
		save.setPrefSize(100, 30);
		right1.add(save, 0, 5);
		save.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				saveHash(hash, chosenCity);

			}
		});
		main1.add(right1, 1, 0);

		primaryStage.setTitle("Project");
		Scene scene = new Scene(main, 650, 280);
		main.prefHeightProperty().bind(scene.heightProperty());
		main.prefWidthProperty().bind(scene.widthProperty());
		primaryStage.setScene(scene);
		primaryStage.show();

	}

}
