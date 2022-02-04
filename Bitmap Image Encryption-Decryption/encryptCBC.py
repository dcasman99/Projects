input_filename = 'encrypted_Image5.bmp'
output_filename = "encrypted_" + input_filename


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

keyparts = splitKey(0x74184202)

i = 0
vector = splitKey(0x13579bde)

while byte:
    if i <= 36:
        output_file.write(byte)

    if i > 36:
        vectors = vector[i % 4]
        get_byte2 = hex(int.from_bytes(byte, "big") ^ vectors)
        get_byte2 = int(get_byte2, 16)
        keypart = keyparts[i % 4]
        cout = hex(get_byte2^keypart)
        cout = int(cout, 16)
        vector[i % 4] = cout

        output_file.write(bytes([cout]))
    byte = input_file.read(1)
    i += 1

input_file.close()
output_file.close()