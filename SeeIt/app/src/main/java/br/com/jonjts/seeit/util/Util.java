package br.com.jonjts.seeit.util;

import com.facebook.Session;
import com.facebook.model.GraphUser;

/**
 * Created by Jonas on 16/01/2015.
 */
public class Util {

    private static Session session;
    private static GraphUser user;

    public static Session getSession() {
        return session;
    }

    public static void setSession(Session session) {
        Util.session = session;
    }

    public static GraphUser getUser() {
        return user;
    }

    public static void setUser(GraphUser user) {
        Util.user = user;
    }
}
