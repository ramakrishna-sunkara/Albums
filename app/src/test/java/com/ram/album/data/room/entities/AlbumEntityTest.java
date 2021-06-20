package com.ram.album.data.room.entities;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class AlbumEntityTest{

    @Test
    public void testAlbumEntity(){
        AlbumEntity albumEntity = new AlbumEntity(1,1,"Album1");
        assertEquals(1, albumEntity.getId());
        assertEquals(1, albumEntity.getUserId());
        assertEquals("Album1", albumEntity.getTitle());
    }
}