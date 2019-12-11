package com.indramahkota.footballmatchschedule.utilities

import com.indramahkota.footballmatchschedule.utilities.Utilities.compareDateAfter
import com.indramahkota.footballmatchschedule.utilities.Utilities.compareDateBeforeAndEqual
import com.indramahkota.footballmatchschedule.utilities.Utilities.formatDateFromString
import org.junit.Assert.assertEquals
import org.junit.Test

class UtilitiesTest {

    @Test
    fun `Check value Date from String with specific Date Format`() {
        assertEquals("Thursday, 12 Dec 2019", formatDateFromString("2019-12-12"))
    }

    @Test
    fun `Check value compare date is After Date Input`() {
        assertEquals(true, compareDateAfter("2030-12-12"))
    }

    @Test
    fun `Check value compare date is Before or Equal Date Input`() {
        assertEquals(true, compareDateBeforeAndEqual("2000-12-12"))
    }
}