package niko.ru.kopilka.model;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;


public class Task extends SugarRecord<Task> {

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