package hometask_3;

public class Pair<T, U> {
  private T first;
  private U second;

  public Pair(T first, U second) {
    this.first = first;
    this.second = second;
  }

  public T getFirst() {
    return first;
  }

  public U getSecond() {
    return second;
  }

  @Override
  public String toString() {
    return "(" + first + ", " + second + ")";
  }

  public static void main(String[] args) {
    Pair<Integer, String> pair1 = new Pair<>(42, "Hello");
    Pair<Double, Boolean> pair2 = new Pair<>(3.14, true);

    System.out.println("Pair 1: " + pair1.toString());
    System.out.println("First value of Pair 2: " + pair2.getFirst());
    System.out.println("Second value of Pair 2: " + pair2.getSecond());
  }
}


