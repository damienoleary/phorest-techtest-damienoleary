package com.damienoleary.phorest.purchases;

import com.damienoleary.phorest.imports.csv.CSVParserTest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PurchaseCSVParserTest implements CSVParserTest {
    PurchaseCsvParser underTest = new PurchaseCsvParser();

    @Test
    public void testParse_nullArg_emptyListReturned() {
        List<Purchase> actual = underTest.parse(null);

        assertThat(actual).isEmpty();
    }

    @Test
    public void testParse_emptyArg_emptyListReturned() {
        List<Purchase> actual = underTest.parse("");

        assertThat(actual).isEmpty();
    }

    @Test
    public void testParse_singleRow_entityReturned() {
        String csv = loadTestFile("single_valid.csv");

        List<Purchase> actual = underTest.parse(csv);

        assertThat(actual).hasSize(1);
        Purchase purchase = actual.get(0);
        assertThat(purchase.getId()).isEqualTo("d2d3b92d-f9b5-48c5-bf31-88c28e3b73ac");
        assertThat(purchase.getName()).isEqualTo("Shampoo");
        assertThat(purchase.getPrice()).isEqualTo("19.5");
        assertThat(purchase.getLoyaltyPoints()).isEqualTo(20);
    }
}