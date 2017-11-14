package niko.ru.moneybox.model;

import com.orm.SugarRecord;


public class Task extends SugarRecord {

  public Task() {
  }

  public Task(String description, String rate, float total) {
    this.description = description;
    this.rate = rate;
    this.total = total;
  }

  public String description;
  public String rate;
  public float included;
  public float total;


  public int getProgress() {
    return (int) (included * 100 / total);
  }

}
