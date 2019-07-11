package com.mservice.processor.pay;

import com.mservice.pay.models.MoMoJson;
import com.mservice.pay.models.POSPayRequest;
import com.mservice.pay.models.POSPayResponse;
import com.mservice.pay.processor.notallinone.POSPay;
import com.mservice.shared.constants.Parameter;
import com.mservice.shared.sharedmodels.Environment;
import com.mservice.shared.sharedmodels.PartnerInfo;
import com.mservice.shared.utils.Encoder;
import org.junit.jupiter.api.Test;

import static com.mservice.shared.sharedmodels.AbstractProcess.getGson;
import static org.junit.jupiter.api.Assertions.assertEquals;

class POSPayTest {

    PartnerInfo devInfo = new PartnerInfo("MOMOLRJZ20181206", "mTCKt9W3eU1m39TW", "KqBEecvaJf1nULnhPF5htpG3AMtDIOlD");
    Environment env = new Environment("https://test-payment.momo.vn/pay/pos", devInfo, "development");

    String publicKey = "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAlsL+G4UyFO0UQsQ4cAXuGYcn38d67PluKmeJqS2RcAqnNUFJjQieI5DSCyHVgAmPpUfDZ3CiSw+5NCfnjgChd/p4fq3bnGqSIw2JP78UROQDJYqfAc+WLvT29IgCH4O+P9+lOLUj2EWf8aqHxBwC1YPxtxK+8M+LKdVMAvZd3lXE3MBg9wTDYEcCNODXkNma/SfIKJCvmVWdKeKXd6IwW7yA0oTjdguAeqP8+O8jLjxJH57otRh63iX945vqAX2YAm9qzVoiDcWpv+UubRmbZ9l0moQwkdsDyCtCYPUtcW6kkdxuhlq8rg8RAVcinsz/843CBYHtqaUaAFQU1TO5EXiXT87zx/Oj2Bf4OC+iAJL/UQ4ASeL1vMoOfDSpSE8EnqKPyP+rM/H7oUaJrIin8KkrxmDLGQWKhNcTFO6UNPv3Hh13tEBv0GRy2vktL8+CWhrYHouXF2XwpS8uR/gH/Vl+5HT/HsTv/13gjSoGBQcdfyck9ZyHh5oBrQTds52C2vabCqWCEafRMbpj7lSrDWS2Df+XznR/hGkgewSdSZ/M0VK/DLadJ3x1Yhblv1HVw3jA3xzY1/zlNOZReLuvW6/kdRwJV/Zj5bd9eLJnz9jDPUcB0hAO+JuJYfTVuhZG9Beo1JbQ9+cFx+92ELn/yHDMod6rfrfBjikU9Gkxor0CAwEAAQ==";
    String privateKey = "MIIJQgIBADANBgkqhkiG9w0BAQEFAASCCSwwggkoAgEAAoICAQCWwv4bhTIU7RRCxDhwBe4Zhyffx3rs+W4qZ4mpLZFwCqc1QUmNCJ4jkNILIdWACY+lR8NncKJLD7k0J+eOAKF3+nh+rducapIjDYk/vxRE5AMlip8Bz5Yu9Pb0iAIfg74/36U4tSPYRZ/xqofEHALVg/G3Er7wz4sp1UwC9l3eVcTcwGD3BMNgRwI04NeQ2Zr9J8gokK+ZVZ0p4pd3ojBbvIDShON2C4B6o/z47yMuPEkfnui1GHreJf3jm+oBfZgCb2rNWiINxam/5S5tGZtn2XSahDCR2wPIK0Jg9S1xbqSR3G6GWryuDxEBVyKezP/zjcIFge2ppRoAVBTVM7kReJdPzvPH86PYF/g4L6IAkv9RDgBJ4vW8yg58NKlITwSeoo/I/6sz8fuhRomsiKfwqSvGYMsZBYqE1xMU7pQ0+/ceHXe0QG/QZHLa+S0vz4JaGtgei5cXZfClLy5H+Af9WX7kdP8exO//XeCNKgYFBx1/JyT1nIeHmgGtBN2znYLa9psKpYIRp9ExumPuVKsNZLYN/5fOdH+EaSB7BJ1Jn8zRUr8Mtp0nfHViFuW/UdXDeMDfHNjX/OU05lF4u69br+R1HAlX9mPlt314smfP2MM9RwHSEA74m4lh9NW6Fkb0F6jUltD35wXH73YQuf/IcMyh3qt+t8GOKRT0aTGivQIDAQABAoICAEUsVtddt+ruFJvF2Hdd4S+JWfxNVNOiZq/CLeGX7OFRogLHX+38BEMhUZ6V+ZXzzRry4G93uJ9kKYAKxy3akDuyitLlBpywMTkwHsdG7w/k5qS1A9wa2TfWYfE07nRzSDmabf6k7jtvRsHlksGicJJzJdbwHPf04k9hdKzvSYaYJ+l5w4PThamJKzQD7iSiE4EQXcBvEoLyS1hKhWD44+NTjHALVSp2gI7E6290mdm/A3YkvXVKNCX1nJZwlV/1tmd3o7NxJarYBNjtWO3qD+oIVn4HJRnnOccAQkameCFRuOTMFVJEE8pxU0YgKtxeBxpC8sEAKoHqJRH9gkmIn+LwaGbbzdrLoA3ZZqVrejb4u3yvnsen85n9DXqKBg9HcqtMo9g5RgAs7jVtzDKCuSx30fnevkWNg7IcpKp/BsSORSjSK292WrU8YbHBkI3CFEGnqaQScPRgBDOFxwL8gNZjlSwgdK5uvwbPApWjhKxjBr+RCplekmAwWaTm1Kk6cuNSxBPdWoFkI7EToXzBMtTcFPw88fx+gteJnpVMCiVk8gMhIJ28r5JtmKMZvFyapksJXdblz0U6VAaIj5NHa7l6yOd9Gbmm8+6aBL6Fv2vs/Pl/jfs74gXziHSAeOW8csPFZNhPgDqy02HKYNmw50ZcKTaRVzrebJ2d50E34fDBAoIBAQD73BgydFHGJ0IU717Bo6EebkzPLHjL9cKXxfNeuYoVb6hDSzthvybxEFWBQPVSm2d4aiRpLj2jBK2hlYJhpq5U3nsmli7bT7rV63/4M0M5uZSWfJR3n40jDD6G+z7m5Xy/WsvLffPrkDA29RV15Nj+02lUSQJV5cC30gYtkz2hjp8Al6hfERNFHkOrsYelBTRi9Qz5sU4PgqD585+EKysTtp2O0h9VRYpr4zk8phyGaX8wUabzMLvHpUXN38xk3cQfeSLZlAJDlPHTAX9cJIHJ1FS7s9/riEgPt7zpRzWW/7oUPps6bfqUxII7h8TW5gSHexwye/x31jIdjPdOXaDpAoIBAQCZPXILx6aW9hg9DuMMgCGfWewgN6+XdRssCfskShOv9B6c2C+4iIQZXdVHtqxJ/X2wVd8won2IwTMibkXHViSeq5qTaErbsxBwfDxYODtC3OqrpQwChcsOu9jFkPduYDNL1n1ynq89CZsIQmsrwhgqHLzKppn1q8hBrLQz/T5DOlGsXBsbfw015FCQYuHNVE4gm6lETV201TK96CEAGfnG2gufhimPM6BKNw89qArNtsMi82FWO1VR3IGf0wR/AsIX1MiqNc0EeR+rUDongeYSQ0osD2qO7wyUnk7l4ZcYnloBbz/hFww1/VRddm0b19w7DSceB4ca9VHo22CITS61AoIBAQC9ets1ci9nTXe4ulFmiw0XENJ2drW2dIFen801sY7CC8viPMJDCoVojOpXIKCv8jyI6gnUm7ZBY66hRxLdFoJHVU3/PLOpyTT95XvUJfTog8bW5Ijp6j5LZNXRxUmQeow8hjmWMX8F9OdhIUdw96eNJ04x2++G4h/362k8dm7CkcLrti00wGrn8BnnHNu4+c5suIF3hL3+iz9CbNfQno18+kFVBp4OEBBQlJUmHK6PLOVEW9mnUj2juKJofXuPUwxOxVrDqEd/wpmgLps7njeKuVS6FlxbHZ9hO9FDC5C0tlWvD17HOaBwtdpooP8k0c4Mg0E2dfL90UbI1pnj+iqpAoIBACUHyTB5jZM7Yv4WQ924dOeKFlpuunryLJ/Gxa6uS/WgG2mxgE6qhqmOmESdKyMyI5ZiGe4pxgYvEclHNouGEJFbTJ5EfJp8ugNEslxrMj7lHbxexPkzFa+4yxhrAK/mhV7VNTlmiVhJovIrArTvUWGT0jdybRAF8/2S23UYxFwAks4A7/gECtA0HRfXHVO+BKcyloPP4k4ZmwpzNs5MeKSJ+ncRN6fIRPrKNV+j5QBzItKmV6Y77lE65QQXPSXoJ7kUjW08VQ+4qWN/np3/cPaG1jyxeQGTWMntO4mnqxii44MnPBuLs0K0thdjz9gDoM+QTRyfmxFzqNiJctldNWUCggEAIlcav4hKTUo+mz4st3UIJ2SlT3v7+dHZgevPYOTfycT5t3b91cvhzgWwjzAR+pgMv9dPsqT3efxEo1nDGwT/pabq9yR0M1DsonAqbqv2Blis8X09rf+CkieOLjwprYCI5cVTGQEZ5mXa2t8DtBml2p18E2QaUN1pWdab/W9o/f2lZmm7j5IJ0EZjS6Ghy42P+Us1z/+wwqxln35ov8AGDUwF6oo822IqkyUfYzUGMkE2xkUwkoyMeU2jYh5pvP9RCzpUr0I3VaoHw+D6pYYyJv9zGCK2X892PIJY/GOPtjb+FQ/ewgQDrC45wXINZKkvxKpEXDr/sVzPgOK24WITBg==";
    POSPay posPay = new POSPay(env);
    long amount = 10000;

    @Test
    void shouldFail() throws Exception {
        String partnerRefId = String.valueOf(System.currentTimeMillis());
        POSPayResponse posPayResponse = POSPay.process(env, partnerRefId, amount, "", "", publicKey, "", "MM943358184685515708", Parameter.VERSION, Parameter.APP_PAY_TYPE);

        assertEquals(1014, posPayResponse.getStatus(), "Wrong Response Body");
        assertEquals(amount, posPayResponse.getMessage().getAmount(), "Wrong Response Body");
    }

    @Test
    void testCreateNewRequest() {
        POSPayRequest posPayRequest = posPay.createPOSPayProcessingRequest("1562301678702", amount, "", "", publicKey, "943358184685515708", "MM943358184685515708", Parameter.VERSION, Parameter.APP_PAY_TYPE);
        String data = Encoder.decryptRSA(posPayRequest.getHash(), privateKey);
        MoMoJson obj = getGson().fromJson(data, MoMoJson.class);
        assertEquals("1562301678702", obj.getPartnerRefId(), "Wrong Hash");
        assertEquals(env.getPartnerInfo().getPartnerCode(), obj.getPartnerCode(), "Wrong Hash");

        assertEquals("1562301678702", posPayRequest.getPartnerRefId());
        assertEquals(env.getPartnerInfo().getPartnerCode(), posPayRequest.getPartnerCode());
        assertEquals(Parameter.APP_PAY_TYPE, posPayRequest.getPayType());
    }
}