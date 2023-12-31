public class Main {
    //commit changes
    public static void main(String[] args) {
        Game g = new Game(); // One of model
        // Model m = new Model();
        View v = new View(g); // View display the model in GUI
        Controller controller = new Controller(g, v); // Controller updates model after getting actions from view
        controller.initController();
    }
}
