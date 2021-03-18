package edu.duke.ece651.risk.client;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

public class ClientOrderHelperTest {
  @Test
  public void test_read_attack_order() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    BufferedReader input = new BufferedReader(new StringReader("AAA\nBBB\n21\n"));
    PrintStream output = new PrintStream(bytes, true);

    ClientOrderHelper helper = new ClientOrderHelper("Green Player", input, output, 10);

    helper.readAttackOrder();
  }

}
