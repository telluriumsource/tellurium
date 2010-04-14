package org.telluriumsource.test.ddt.mapping.type
/**
 *  Type handler, users can it to define custom type conversion in object mapping
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 23, 2008
 *
 */
interface TypeHandler {
  def valueOf(String s)
}