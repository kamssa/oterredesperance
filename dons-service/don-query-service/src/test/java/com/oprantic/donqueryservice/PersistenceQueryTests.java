package com.oprantic.donqueryservice;

import com.operantic.coreapi.ProjetData;
import com.operantic.coreapi.UserData;
import com.operantic.coreapi.coreapi.don.enumeted.DonStatus;
import com.oprantic.donqueryservice.query.document.Don;
import com.oprantic.donqueryservice.query.exception.DonNotFoundException;
import com.oprantic.donqueryservice.query.repository.DonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
public class PersistenceQueryTests {

    @Autowired
    private DonRepository donRepository;

    private String donId_1 = UUID.randomUUID().toString();
    private ProjetData projetId_1 = null;
    private UserData donateurId_1 =null;

    private String projetId_update_1 = UUID.randomUUID().toString();
    private String paiementId_update_1 = UUID.randomUUID().toString();
    private String donateurId_update_1 = UUID.randomUUID().toString();

    @BeforeEach
    void setupDb(){
        donRepository.deleteAll();
        Don newDon = new Don(UUID.randomUUID().toString(), donateurId_1, projetId_1, LocalDate.now(), DonStatus.CREATED);
        Don donSave = donRepository.save(newDon);
        assertEqualsDon(newDon, donSave);
    }

    private void assertEqualsDon(Don expectedEntity, Don actualEntity){
        assertEquals(expectedEntity.getId(), actualEntity.getId());
        assertEquals(expectedEntity.getUserData(), actualEntity.getUserData());
        assertEquals(expectedEntity.getProjetData(), actualEntity.getProjetData());
        assertEquals(expectedEntity.getStatus(), actualEntity.getStatus());
    }

    @Test
    void getDonList(){
        List<Don> dons = donRepository.findAll();
        assertTrue(!dons.isEmpty());
    }

    @Test
    void updateDon(){
        Don newDon = new Don(donId_1, donateurId_1, projetId_1, LocalDate.now(),DonStatus.CREATED);
        donRepository.save(newDon);
        Don updateDon = donRepository.findById(donId_1).orElseThrow(DonNotFoundException::new);
        updateDon.setUserData(donateurId_1);
        updateDon.setProjetData(projetId_1);
        updateDon.setStatus(DonStatus.UPDATED);
        assertNotEquals(updateDon, newDon);
    }
}
