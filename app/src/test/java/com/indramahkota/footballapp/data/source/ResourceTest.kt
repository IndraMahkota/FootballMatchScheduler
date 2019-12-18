package com.indramahkota.footballapp.data.source

import org.junit.Assert
import org.junit.Test

class ResourceTest {

    @Test
    fun success() {
        val resource = Resource.success("test")
        Assert.assertEquals("test", resource.data)
        Assert.assertEquals(Status.SUCCESS, resource.status)
    }

    @Test
    fun exception() {
        val exception = Exception("test")
        val resource = Resource.error(exception.message, exception)
        Assert.assertEquals("test", resource.message)
        Assert.assertEquals(Status.ERROR, resource.status)
    }
}