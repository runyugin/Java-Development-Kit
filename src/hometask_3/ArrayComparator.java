package hometask_3;

public class ArrayComparator {
  public static <T> boolean compareArrays(T[] array1, T[] array2) {
    if (array1.length != array2.length) {
      return false; // Разные длины массивов
    }

    for (int i = 0; i < array1.length; i++) {
      if (!array1[i].equals(array2[i])) {
        return false; // Не равные элементы
      }
    }

    return true; // Все элементы одинаковы
  }

  public static void main(String[] args) {
    Integer[] intArray1 = {1, 2, 3, 4, 5};
    Integer[] intArray2 = {1, 2, 3, 4, 5};
    Integer[] intArray3 = {1, 2, 3, 4, 6};

    String[] strArray1 = {"apple", "banana", "cherry"};
    String[] strArray2 = {"apple", "banana", "cherry"};
    String[] strArray3 = {"apple", "banana", "grape"};

    boolean intArraysEqual = compareArrays(intArray1, intArray2);
    boolean strArraysEqual = compareArrays(strArray1, strArray2);
    boolean intArraysNotEqual = compareArrays(intArray1, intArray3);
    boolean strArraysNotEqual = compareArrays(strArray1, strArray3);

    System.out.println("intArray1 и intArray2 равны: " + intArraysEqual); // true
    System.out.println("strArray1 и strArray2 равны: " + strArraysEqual); // true
    System.out.println("intArray1 и intArray3 равны: " + intArraysNotEqual); // false
    System.out.println("strArray1 и strArray3 равны: " + strArraysNotEqual); // false
  }
}

