/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvd.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author BlankSpace
 */
public class DateHandle {

    public static String createCurrentDate() {
	Date currentDate = new Date();
	return new SimpleDateFormat("YYYY-MM-dd").format(currentDate);
    }
    
    public static String createOrderDate() {
	Date currentDate = new Date();
	return new SimpleDateFormat("YYYYMMDD hh:mm:ss").format(currentDate);
    }
}
