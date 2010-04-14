package org.telluriumsource.entity

/**
 * Enum for Cache Policies
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Nov 26, 2009
 *
 */
public enum CachePolicy {
  DISCARD_NEW,
  DISCARD_OLD,
  DISCARD_LEAST_USED,
  DISCARD_INVALID  
}