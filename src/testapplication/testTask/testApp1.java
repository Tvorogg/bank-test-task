package testapplication.testTask;

public class testApp1 {
    public static void main(String[] args) {
        Parent parent = new Parent();
        parent.print();
        Parent child = new Child();
        child.print();
    }

    static class Parent {
        protected String fio;

        public Parent() {
            this.fio = "анна мария оглы";
        }

        public void print() {
            System.out.println(formatFio());
        }

        private String formatFio() {
            return fio + "!";
        }
    }

    static class Child extends Parent {
        public Child() {
            this.fio = "АН'НА-МАРИЯ оглы";
        }

        private String formatFio() {
            String[] words = fio.split(" |-|'");
            StringBuilder formattedFio = new StringBuilder();
            for (String word : words) {
                formattedFio.append(Character.toUpperCase(word.charAt(0)));
                formattedFio.append(word.substring(1).toLowerCase());
                formattedFio.append(" ");
            }
            return formattedFio.toString().trim();
        }
    }
}