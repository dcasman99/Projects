input_filename = 'encrypted_Image1.bmp'
output_filename = "decrypted_" + input_filename


def splitKey(key):
    # breaks key into four bytes

    hex_data = [(key & 0xff000000) >> 24, (key & 0x00ff0000) >> 16, (key & 0x0000ff00) >> 8, (key & 0x000000ff)]
    # print(hex(hex_data[0])+" "+hex(hex_data[1])+" "+hex(hex_data[2])+" "+hex(hex_data[3]))

    return (hex_data)


input_file = open(input_filename, "rb")
output_file = open(output_filename, "wb")
print("Reading...")
byte = input_file.read(1)
print("Read file from " + input_filename)

keyparts = splitKey(0x12345678)

i = 0

while byte:
    if i <= 36:
        # print(byte.hex())
        output_file.write(byte)

    if i > 36:
        # print(f'{byte.hex()} : {hex(keyparts[i%4])} : {hex(int.from_bytes(byte, "big")^keyparts[i%4])}')

        get_byte2 = hex(int.from_bytes(byte, "big") ^ keyparts[i % 4])
        digit_byte2 = int(get_byte2, 16)  # int
        output_file.write(bytes([digit_byte2]))
    byte = input_file.read(1)
    i += 1

input_file.close()
output_file.close()