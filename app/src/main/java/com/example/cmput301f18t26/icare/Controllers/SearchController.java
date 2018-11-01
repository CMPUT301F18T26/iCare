package com.example.cmput301f18t26.icare;

public class SearchController {
  private int searchType;

  public SearchController(int type) {
    this.searchType = type;
  }

  // method overloading doesnt work well here beacuse double, double :(
  // we can use a generic return type and create instances of the controller
  // with an explicit search value defined - 0 for problem, 1 for record
  public <T> List<T> searchByKeyword(String[] keywords) {

  }

  public <T> List<T> geoSearch(double[] geoLocation) {

  }

  public <T> List<T> bodySearch(double[] bodyLocation) {

  }

}
