/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvd.util;

/**
 *
 * @author BlankSpace
 */
public class PageCalculate {
    
    public static int calculateNumOfPage(int totalItems, int itemsPerPage) {
	int totalPage;

	if (totalItems % itemsPerPage != 0) {
	    totalPage = totalItems / itemsPerPage + 1;
	} else {
	    totalPage = totalItems / itemsPerPage;
	}

	return totalPage;
    }
}
