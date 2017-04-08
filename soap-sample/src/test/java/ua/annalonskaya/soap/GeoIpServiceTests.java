package ua.annalonskaya.soap;

import net.webservicex.GeoIP;
import net.webservicex.GeoIPService;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class GeoIpServiceTests {

  @Test
  public void testMyIp() {
    GeoIP geoIP = new GeoIPService().getGeoIPServiceSoap12().getGeoIP("94.179.42.138");
    assertEquals(geoIP.getCountryCode(), "UKR");
  }

  @Test
  public void testInvalidIp() {
    GeoIP geoIP = new GeoIPService().getGeoIPServiceSoap12().getGeoIP("94.179.42.xxx");
    assertEquals(geoIP.getCountryCode(), "UKR");
  }

}
