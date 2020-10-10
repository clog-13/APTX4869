unsigned srl_with_arithemetic(unsigned x ,int k) {
    unsigned xsra = (int) x >> k;

    int w = sizeof(int) << 3;
    int mask = (int) -1 << (w - k);
//    int mask = (int) ~0 << (w - k);
    return xsra & ~mask;
}

unsigned sra_with_logic(int x ,int k) {
    int xrsl = (unsigned) x >> k;

    int w = sizeof(int) << 3;
    int mask = (int) -1 << (w - k); // mask = 111.k.11000...0
    // 当x的第一个位为1时，让掩码保持不变，否则为0。
    int m = 1 << (w - 1);
    mask &= !(x & m) - 1;
    return xrsl | mask;
}