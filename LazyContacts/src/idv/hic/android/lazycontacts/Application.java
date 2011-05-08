package idv.hic.android.lazycontacts;

import java.util.List;

import com.google.inject.AbstractModule;
import com.google.inject.Module;

import roboguice.application.RoboApplication;

public class Application extends RoboApplication {

	@Override
	protected void addApplicationModules(List<Module> modules) {
		// TODO Auto-generated method stub
		//super.addApplicationModules(modules);
		
		modules.add(new AbstractModule() {
			
			@Override
			protected void configure() {
				// TODO Auto-generated method stub
				
			}
		});
		
	}

}
