package cue.edu.co.singleton;

import cue.edu.co.repositories.impl.SaleRepositoryJDBCImpl;
import cue.edu.co.repositories.impl.ToyRepositoryJDBCImpl;
import cue.edu.co.repositories.impl.UserRepositoryJDBCImpl;
import cue.edu.co.services.SaleService;
import cue.edu.co.services.ToysService;
import cue.edu.co.services.UserService;
import cue.edu.co.services.impl.SaleServiceImpl;
import cue.edu.co.services.impl.ToysServiceImpl;
import cue.edu.co.services.impl.UserServiceImpl;

public class ServicesSingleton {
  private static ServicesSingleton instance;
  public final UserService userService;
  public final ToysService toysService;

  public final SaleService saleService;

  private ServicesSingleton() {
    UserRepositoryJDBCImpl userRepositoryJDBC = new UserRepositoryJDBCImpl();
    ToyRepositoryJDBCImpl toyRepositoryJDBC = new ToyRepositoryJDBCImpl();

    this.userService =  new UserServiceImpl(userRepositoryJDBC);
    this.toysService = new ToysServiceImpl(toyRepositoryJDBC);
    this.saleService = new SaleServiceImpl(new SaleRepositoryJDBCImpl(toyRepositoryJDBC, userRepositoryJDBC));
  }

  public static ServicesSingleton getInstance() {
    if(instance ==null){
      instance = new ServicesSingleton();
      return instance;
    }
    return instance;
  }
}
