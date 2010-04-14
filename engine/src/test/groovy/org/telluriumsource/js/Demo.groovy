package org.telluriumsource.test;


/**
 * QUnit demo from
 *           http://docs.jquery.com/QUnit
 * 
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Dec 16, 2009
 */

class Demo {

  public static String js = """
    test("a basic test example", function() {
      ok( true, "this test is fine" );
      var value = "hello";
      equals( "hello", value, "We expect value to be hello" );
    });

    module("Module A");

    test("first test within module", function() {
      ok( true, "all pass" );
    });

    test("second test within module", function() {
      ok( true, "all pass" );
    });

    module("Module B");

    test("some other test", function() {
      expect(2);
      equals( true, false, "failing test" );
      equals( true, true, "passing test" );
    });
  """

  public static String body = """
     <h1 id="qunit-header">QUnit example</h1>
     <h2 id="qunit-banner"></h2>
     <h2 id="qunit-userAgent"></h2>
     <ol id="qunit-tests"></ol>
  """

}