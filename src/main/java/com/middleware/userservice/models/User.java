package com.middleware.userservice.models;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Class to construct the User model.
 */
@Entity
public class User {

  private @GeneratedValue Long id;
  private @Id String email;
  private String password;
  private String firstName;
  private String lastName;  

  public User() {}

  /**
   * Constructor method to build a map based on the input provided.
   * @param email email address of user as string.
   * @param password password of user as string.
   * @param firstName first name of user as string.
   * @param lastName last name of user as string.
   */
  public User(String email, String password, String firstName, String lastName) {
    this.email = email;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  @Override
  public String toString() {
    String string = String.format("{\"email\":\"%s\",\"password\":\"%s\",\"firstName\":\"%s\",\"lastName\":\"%s\"}", 
      this.email, this.password, this.firstName, this.lastName);
    return string;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return this.lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * Obtains current user model information.
   * @return a map object containing user data.
   */
  public HashMap<String, String> userMap() {
    HashMap<String, String> userObj = new HashMap<String, String>();
    userObj.put("email", this.email);
    userObj.put("password", this.password);
    userObj.put("firstName", this.firstName);
    userObj.put("lastName", this.lastName);
    return userObj;
  }

  /**
   * Obtains required fields for user model
   * @return an array of string
   */
  public ArrayList<String> userRequiredFields() {
    ArrayList<String> requiredFields = new ArrayList<String>(){
      {
        add("email");
        add("password");
      }
    };
    return requiredFields;
  }

  /**
   * Obtains a key-value from current user model information.
   * @return a string value corresponding to user key or false.
   */
  public String getUserValue(String key) {
    if (this.userMap().containsKey(key)) {
      return this.userMap().get(key);
    }
    return "";
  }
  
  /**
   * Verifies if user has required key-value pairs.
   * @return a boolean on whether the user has required key-value pairs.
   */
  public Boolean containsRequired() {
    Integer count = 0;
    for (Map.Entry<String,String> entry : this.userMap().entrySet()) {
      String key = entry.getKey(); 
      if (this.userRequiredFields().contains(key)) {
        count++;
      }
    }
    return count >= this.userRequiredFields().size();
  }

  /**
   * Verifies user model with a given user. 
   * If the given user contains keys that are not in the user model, it is considered as
   * an invalid user.
   * @param newUser a User object for comparison
   * @return a boolean on whether the update is valid or not.
   */
  public Boolean validateUser(User newUser) {
    Boolean valid = true;
    for (Map.Entry<String,String> entry : newUser.userMap().entrySet()) {
      String key = entry.getKey(); 
      if (!this.userMap().containsKey(key)) {
        valid = false;
        break;
      }
    }
    return valid;
  }

  /**
   * Updates user model with a given user. 
   * If the given user contains keys that are not in the user model, it is considered as
   * an invalid user.
   * @param newUser a User object for comparison
   * @return a boolean on whether the update is valid or not.
   */
  public HashMap<String, String> updateUser(User newUser) {
    for (Map.Entry<String,String> entry : newUser.userMap().entrySet()) {
      String key = entry.getKey(); 
      String value = entry.getValue();
      if (this.userMap().containsKey(key)) {
        this.userMap().put(key, value);
        switch (key) {
          case "email": 
            this.setEmail(value);
            break;
          case "password": 
            this.setPassword(value);
            break;
          case "firstName": 
            this.setFirstName(value);
            break;
          case "lastName": 
            this.setLastName(value);
            break;
          default: break;
        }
      }
    }
    return this.userMap();
  }

}