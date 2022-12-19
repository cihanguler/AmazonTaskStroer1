package de.amazon.runners;

import java.security.Permission;

public class IgnoreExitCall extends SecurityManager {

    @Override
    public void checkExit (int status){
        throw new SecurityException();
    }

    @Override
    public void checkPermission (Permission perm) {
        //Allow other activities by default
    }
}
