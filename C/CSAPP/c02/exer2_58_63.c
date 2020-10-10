#include <stdio.h>
#include <assert.h>

typedef unsigned char *byte_pointer;
int resi;

int exer_58() {
    int test_num = 0xff;
    byte_pointer bytePointer = (byte_pointer) &test_num;

    if(bytePointer[0] == 0xff) {
        return 1;
    } else {
        return 0;
    }
}

int exer_59(int a, int b) {
    int mask = 0xff;
    return (a & mask) | (b & ~mask);
}

int exer_60(unsigned x, int i, unsigned char b) {
    // 1 byte has 8 bits, << 3 means * 8
    unsigned mask = ((unsigned) 0xFF) << (i << 3);
    unsigned pos_byte = ((unsigned) b) << (i << 3);

    return (x & ~mask) | pos_byte;
}

int exer_61(char c, int x) {
    switch(c) {
        case 'A':
            return !~x;
        case 'B':
            return !x;
        case 'C':
            return !~(x | ~0xff);
        case 'D':
            return !((x >> ((sizeof(int)-1) << 3)) & 0xff);
    }
    return -1;
}

int exer_62() {
    int num = -1;   // ~0 == -1 == 1111 ... 1111
    return !(num ^ (num >> 1));
}

unsigned exer_63_srl_with_arithemetic(unsigned x ,int k) {
    unsigned xsra = (int) x >> k;

    int w = sizeof(int) << 3;
    int mask = (int) -1 << (w - k);
    return xsra & ~mask;
}

unsigned exer_63_sra_with_logic(int x ,int k) {
    int xrsl = (unsigned) x >> k;

    int w = sizeof(int) << 3;
    int mask = (int) -1 << (w - k);
    int m = 1 << (w - 1);   
    mask &= !(x & m) - 1;   // 当x的第一个位为1时，让掩码保持不变，否则为0。
    return xrsl | mask;
}

int main() {
    // 58
    resi = exer_58();
    if (resi) printf("little endian\n");
    else printf("big endian\n");

    // 59
    int a = 0x89ABCDEF, b = 0x76543210;
    resi =  exer_59(a, b);
    assert(resi == 0x765432EF);

    // 60
    unsigned rep_0 = exer_60(0x12345678, 0, 0xAB);
    unsigned rep_3 = exer_60(0x12345678, 3, 0xAB);
    assert(rep_0 == 0x123456AB);
    assert(rep_3 == 0xAB345678);

    // 62
    resi = exer_62();
    if (resi) printf("sra");
    else printf("srl");

    return 0;
}