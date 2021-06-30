package de.throsenheim.application.connections;

import de.throsenheim.domain.models.Aktor;
import de.throsenheim.persistence.repositories.AktorsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringJUnitConfig
@WebMvcTest(AktorConnection.class)
class AktorConnectionTest {


    @MockBean
    AktorsRepository aktorsRepository;

    @Autowired
    private AktorConnection aktorConnection;

    @Test
    void testAktorConnectiongetAktorsShouldReturnListOdAktors() throws Exception{
        Aktor aktor = new Aktor();
        aktor.setId(42);
        when(aktorsRepository.findById(42)).thenReturn(Optional.of(aktor));
        assertEquals(aktor, aktorConnection.getAktor(42));
    }

    @Test
    void shouldReturnAListOfAktors() throws Exception {
        Aktor aktor1 = new Aktor();
        aktor1.setId(42);
        Aktor aktor2 = new Aktor();
        aktor2.setId(1337);

        List<Aktor> aktorList = new ArrayList<>();
        aktorList.add(aktor1);
        aktorList.add(aktor2);
        when(aktorsRepository.findAll()).thenReturn(aktorList);

        assertEquals(aktorList, aktorConnection.getAktorList());
    }


}