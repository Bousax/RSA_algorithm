# RSA Encryption/Decryption Project

This project demonstrates the RSA algorithm by implementing both encryption and decryption in Java. The project consists of two Java files:

- **Main.java**: The main application file, which provides two modes: **encryption** and **decryption**.
- **RSA.java**: Contains all the methods necessary to perform RSA encryption and decryption.

## Project Structure

### Encryption Mode
- **plainMessage.txt**: This file stores the non-encrypted message to be encrypted (resources folder).
- The encryption process:
  1. **Generate** the necessary RSA values (using Java's `BigInteger` for large numbers).
  2. **Write** the private key ("d n") on the first line of `output.txt`.
  3. **Encrypt** the message, and store the result (a series of `BigInteger` values representing each character) on the second line of `output.txt`.

### Decryption Mode
- **encryptedMessage.txt**: This file stores the encrypted message to be decrypted (resources folder).
- The decryption process:
  1. **Read** the private key and the encrypted message from `encryptedMessage.txt`.
  2. **Decrypt** the message using the private key.
  3. **Write** the decrypted message to `output.txt`.

## Notes
- The project uses **log4j** for logging.
- The file management has been done in the simplest way I could.
- **Fastest way to Test the program**:
  1. Write a plain message in `plainMessage.txt`.
  2. Run the program in encryption mode to generate `output.txt` with the encrypted message.
  3. Copy the contents of `output.txt` to `encryptedMessage.txt`.
  4. Run the program in decryption mode to verify the message is correctly decrypted back to its original form.
