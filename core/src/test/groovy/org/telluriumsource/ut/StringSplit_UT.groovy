package org.telluriumsource.ut
/**
 * Test to show the String split for pipe format problem
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 28, 2008
 *
 */
class StringSplit_UT extends GroovyTestCase {
 	protected final static String FIELD_DELIMITER = "\\|"

    	public void testSplitWithPipe() {
		String input1 = "|||||||||"
		String[] fields1 = input1.split(FIELD_DELIMITER)
        //problem with not spaces between |
        assertFalse(10 == fields1.length)
		String input2 = " | | | | | | | | | "
		String[] fields2 = input2.split(FIELD_DELIMITER)
		assertEquals(10, fields2.length);
		String input3 = "\\Q|||||||||\\E"
        //solution is to escape the whole string so that it will be treated as literals
        String[] fields3 = input3.split(FIELD_DELIMITER)
		assertEquals(10, fields3.length)
	}

}