package com.example.cmput301f18t26.icare;

import java.util.ArrayList;
import java.util.List;

public class SearchController {
  private int searchType;

  public SearchController(int type) {
    this.searchType = type;
  }

  // method overloading doesnt work well here beacuse double, double :(
  // we can use a generic return type and create instances of the controller
  // with an explicit search value defined - 0 for problem, 1 for record
  public <T> List<T> searchByKeyword(String[] keywords) {
    return new ArrayList<T>();
  }

  public <T> List<T> geoSearch(double[] geoLocation) {
    return new ArrayList<T>();
  }

  public <T> List<T> bodySearch(double[] bodyLocation) {
    return new ArrayList<T>();
  }

}
