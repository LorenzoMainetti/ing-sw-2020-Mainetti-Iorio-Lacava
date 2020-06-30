package it.polimi.ingsw.PSP41;

import it.polimi.ingsw.PSP41.model.Color;
import it.polimi.ingsw.PSP41.model.GodPowerFactory;
import it.polimi.ingsw.PSP41.model.Player;
import it.polimi.ingsw.PSP41.model.godCards.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for GodPowerFactory.
 */
public class GodPowerFactoryTest {
    GodPowerFactory godPowerFactory;
    String nickname;
    Color color;
    String godName;

    @Before
    public void setup() {
        nickname = "Olimpia";
        color = Color.RED;
        godPowerFactory = new GodPowerFactory();
    }

    @Test
    public void testCreateGodPower() {
        godName = "APOLLO";
        Player p1 = godPowerFactory.createGodPower(nickname, color, godName);
        assertTrue(p1.getGodCard() instanceof Apollo);
        godName = "ARTEMIS";
        Player p2 = godPowerFactory.createGodPower(nickname, color, godName);
        assertTrue(p2.getGodCard() instanceof Artemis);
        godName = "ATHENA";
        Player p3 = godPowerFactory.createGodPower(nickname, color, godName);
        assertTrue(p3.getGodCard() instanceof Athena);
        godName = "ATLAS";
        Player p4 = godPowerFactory.createGodPower(nickname, color, godName);
        assertTrue(p4.getGodCard() instanceof Atlas);
        godName = "DEMETER";
        Player p5 = godPowerFactory.createGodPower(nickname, color, godName);
        assertTrue(p5.getGodCard() instanceof Demeter);
        godName = "HEPHAESTUS";
        Player p6 = godPowerFactory.createGodPower(nickname, color, godName);
        assertTrue(p6.getGodCard() instanceof Hephaestus);
        godName = "MINOTAUR";
        Player p7 = godPowerFactory.createGodPower(nickname, color, godName);
        assertTrue(p7.getGodCard() instanceof Minotaur);
        godName = "PAN";
        Player p8 = godPowerFactory.createGodPower(nickname, color, godName);
        assertTrue(p8.getGodCard() instanceof Pan);
        godName = "PROMETHEUS";
        Player p9 = godPowerFactory.createGodPower(nickname, color, godName);
        assertTrue(p9.getGodCard() instanceof Prometheus);
        godName = "HESTIA";
        Player p10 = godPowerFactory.createGodPower(nickname, color, godName);
        assertTrue(p10.getGodCard() instanceof Hestia);
        godName = "ZEUS";
        Player p11 = godPowerFactory.createGodPower(nickname, color, godName);
        assertTrue(p11.getGodCard() instanceof Zeus);
        godName = "TRITON";
        Player p12 = godPowerFactory.createGodPower(nickname, color, godName);
        assertTrue(p12.getGodCard() instanceof Triton);
        godName = "POSEIDON";
        Player p13 = godPowerFactory.createGodPower(nickname, color, godName);
        assertTrue(p13.getGodCard() instanceof Poseidon);
        godName = "ARES";
        Player p14 = godPowerFactory.createGodPower(nickname, color, godName);
        assertTrue(p14.getGodCard() instanceof Ares);
        godName = "DEFAULT";
        Player p15 = godPowerFactory.createGodPower(nickname, color, godName);
        assertTrue(p15.getGodCard() instanceof Default);
    }

}
