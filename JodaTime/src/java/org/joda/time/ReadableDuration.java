/*
 * Joda Software License, Version 1.0
 *
 *
 * Copyright (c) 2001-2004 Stephen Colebourne.  
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer. 
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:  
 *       "This product includes software developed by the
 *        Joda project (http://www.joda.org/)."
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The name "Joda" must not be used to endorse or promote products
 *    derived from this software without prior written permission. For
 *    written permission, please contact licence@joda.org.
 *
 * 5. Products derived from this software may not be called "Joda",
 *    nor may "Joda" appear in their name, without prior written
 *    permission of the Joda project.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE JODA AUTHORS OR THE PROJECT
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Joda project and was originally 
 * created by Stephen Colebourne <scolebourne@joda.org>. For more
 * information on the Joda project, please see <http://www.joda.org/>.
 */
package org.joda.time;

/**
 * Defines an exact duration of time in milliseconds.
 * <p>
 * The implementation of this interface may be mutable or immutable. This
 * interface only gives access to retrieve data, never to change it.
 *
 * @see ReadableInterval
 * @see ReadableTimePeriod
 * @author Brian S O'Neill
 * @author Stephen Colebourne
 * @since 1.0
 */
public interface ReadableDuration extends Comparable {

    /**
     * Gets the total length of this duration in milliseconds.
     *
     * @return the total length of the time duration in milliseconds.
     */
    long getMillis();

    //-----------------------------------------------------------------------
    /**
     * Converts this duration to a Duration instance.
     * This can be useful if you don't trust the implementation of the interface
     * to be well-behaved, or to get a guaranteed immutable object.
     * 
     * @return a Duration created using the millisecond duration from this instance
     */
    Duration toDuration();

    //-----------------------------------------------------------------------
    /**
     * Converts this duration to a TimePeriod instance using the All type.
     * <p>
     * Only precise fields in the period type will be used and the calculation will use UTC.
     * <p>
     * If the duration is small, less than one day, then this method will perform
     * as you might expect and split the fields evenly. The situation is more complex
     * for larger durations.
     * <p>
     * If the duration is larger then the years and months fields will remain as zero,
     * with the duration allocated to the weeks field.
     * Normally, the weeks and days fields are imprecise, but this method
     * calculates using the UTC time zone making weeks and days precise.
     * The effect is that a large duration of several years or months will be converted
     * to a period including a large number of weeks and zero years and months.
     * For example, a duration equal to (365 + 60 + 5) days will be converted to
     * 61 weeks and 3 days.
     * <p>
     * For more control over the conversion process, you should convert the duration
     * to an interval by referencing a fixed instant and then obtain the period.
     * 
     * @return a TimePeriod created using the millisecond duration from this instance
     */
    TimePeriod toTimePeriod();

    /**
     * Converts this duration to a TimePeriod instance specifying a period type
     * to control how the duration is split into fields.
     * <p>
     * The exact impact of this method is determined by the period type.
     * Only precise fields in the period type will be used and the calculation will use UTC.
     * <p>
     * If the duration is small, less than one day, then this method will perform
     * as you might expect and split the fields evenly. The situation is more complex
     * for larger durations.
     * <p>
     * If the period type is PreciseAll then all fields can be set.
     * For example, a duration equal to (365 + 60 + 5) days will be converted to
     * 1 year, 2 months and 5 days using the PreciseAll type.
     * <p>
     * If the period type is All then the years and months fields will remain as zero,
     * with the duration allocated to the weeks and days fields.
     * The effect is that a large duration of several years or months will be converted
     * to a period including a large number of weeks and zero years and months.
     * For example, a duration equal to (365 + 60 + 5) days will be converted to
     * 61 weeks and 3 days.
     * <p>
     * For more control over the conversion process, you should convert the duration
     * to an interval by referencing a fixed instant and then obtain the period.
     * 
     * @param type  the period type determining how to split the duration into fields
     * @return a TimePeriod created using the millisecond duration from this instance
     */
    TimePeriod toTimePeriod(PeriodType type);

    //-----------------------------------------------------------------------
    /**
     * Compares this duration with the specified duration based on length.
     *
     * @param obj  a duration to check against
     * @return negative value if this is less, 0 if equal, or positive value if greater
     * @throws NullPointerException if the object is null
     * @throws ClassCastException if the given object is not supported
     */
    int compareTo(Object obj);

    /**
     * Is the length of this duration equal to the duration passed in.
     *
     * @param duration  another duration to compare to, null means zero milliseconds
     * @return true if this duration is equal to than the duration passed in
     */
    boolean isEqual(ReadableDuration duration);

    /**
     * Is the length of this duration longer than the duration passed in.
     *
     * @param duration  another duration to compare to, null means zero milliseconds
     * @return true if this duration is equal to than the duration passed in
     */
    boolean isLongerThan(ReadableDuration duration);

    /**
     * Is the length of this duration shorter than the duration passed in.
     *
     * @param duration  another duration to compare to, null means zero milliseconds
     * @return true if this duration is equal to than the duration passed in
     */
    boolean isShorterThan(ReadableDuration duration);

    //-----------------------------------------------------------------------
    /**
     * Compares this object with the specified object for equality based
     * on the millisecond length. All ReadableDuration instances are accepted.
     *
     * @param readableDuration  a readable duration to check against
     * @return true if the length of the duration is equal
     */
    boolean equals(Object readableDuration);

    /**
     * Gets a hash code for the duration that is compatable with the 
     * equals method.
     * The following formula must be used:
     * <pre>
     *  long len = getMillis();
     *  return (int) (len ^ (len >>> 32));
     * </pre>
     *
     * @return a hash code
     */
    int hashCode();

    //-----------------------------------------------------------------------
    /**
     * Gets the value as a String in the ISO8601 duration format.
     * <p>
     * For example, "P6H3M7S" represents 6 hours, 3 minutes, 7 seconds.
     * The field values are determined using the PreciseAll period type.
     *
     * @return the value as an ISO8601 string
     */
    String toString();

}
