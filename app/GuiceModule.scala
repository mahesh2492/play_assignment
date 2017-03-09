import java.time.Clock

import com.google.inject.AbstractModule
import play.api.{Configuration, Environment}
import services.{CacheImplementation, CacheTrait}


class GuiceModule(environment: Environment,configuration: Configuration) extends AbstractModule{

  override def configure()={
    bind(classOf[CacheTrait]).to(classOf[CacheImplementation])
  }
}

