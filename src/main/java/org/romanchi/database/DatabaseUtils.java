/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.romanchi.database;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
/**
 *
 * @author Роман
 */
public class DatabaseUtils {
    /*private final static Logger logger = Logger.getLogger(DatabaseUtils.class.getName());
    private DatabaseUtils(){}
   
    public static DataSource getDataSource() {
        Context initContext;
        try {
            initContext = new InitialContext();
            
            return (DataSource) initContext
                            .lookup("java:/comp/env/jdbc/Epam");
        } catch (NamingException e) {
                logger.info("Cannot get JNDI DataSource");
                return null;
        }finally{
            logger.info("hello");
        }
    }*/
    private static Context context;
    static{
        try {
            context = new InitialContext();
        } catch (NamingException ex) {
            Logger.getLogger(DatabaseUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private DatabaseUtils(){}
    private static class DataSourceHolder{
        private static DataSource DATA_SOURCE;

        static{
            try {
                DATA_SOURCE = (DataSource) context.lookup("java:/comp/env/jdbc/Epam");
            } catch (NamingException ex) {
                Logger.getLogger(DatabaseUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
    public static DataSource getDataSource(){
        return DataSourceHolder.DATA_SOURCE;
    }
}
