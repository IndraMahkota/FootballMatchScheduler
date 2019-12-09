package com.indramahkota.footballmatchschedule.utilities

import com.indramahkota.footballmatchschedule.utilities.Utilities.compareDateAfter
import com.indramahkota.footballmatchschedule.utilities.Utilities.compareDateBeforeAndEqual
import com.indramahkota.footballmatchschedule.utilities.Utilities.formatDateFromString
import org.junit.Assert.assertEquals
import org.junit.Test

class UtilitiesTest {

    @Test
    fun testFormatDateFromString() {
        assertEquals("Thursday, 12 Dec 2019", formatDateFromString("2019-12-12"))
    }

    @Test
    fun testCompareDateAfter() {
        assertEquals(true, compareDateAfter("2030-12-12"))
    }

    @Test
    fun testCompareDateBeforeAndEqual() {
        assertEquals(true, compareDateBeforeAndEqual("2000-12-12"))
    }
}