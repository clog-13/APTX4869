/**
 * node: 区间结点号    le:该node的区间左边界   ri: 该node的区间右边界     
 * pos: 查询区间的点  start: 查询区间的左边界 end: 查询区间的右边界 
 * */ 
  
/**
 * 线段树:求和或最值 
 * 区间更新,区间查询(lazy标记表示本节点的信息已经根据标记更新过了，但是本节点的子节点仍需要进行更新。)
 * lazy初始为0,区间加上k给该区间管理的结点的lazy加k,push_down给子节点加(ri-le+1)*k
 * 
 * lson 2*node
 * rson 2*node+1
 * [le,ri]
 * [le,mid] [mid+1,ri] 其中mid为(le+ri)/2
 * */ 

#define lson (node<<1)
#define rson ((node<<1) | 1)
#define mid ((le+ri) >> 1)
#define maxn 100010

int segTree[maxn*4];
int lazy[maxn];
int arr[maxn];

void build(int node, int le, int ri) {  // 建树 
    lazy[node] = 0;
    if (le == ri) { // le==end表示管理的是结点 
        // scanf("%d", &segTree[node]);    // 按照顺序输入结点，由于建树类似于树的先根遍历，所以建立的线段树的叶子结点从左到右的值就是输入的顺序 
        segTree[node] = arr[le];    // 用于任意顺序输入,先将输入存入a数组,下标从1开始，begin = ri = index 
        return; // 输入完成后要return，否则会继续访问左右孩子，可能越界
    }
    build(lson, le, mid);
    build(rson, mid+1, ri);
    pushUp(node);
}

void pushUp(int node) {
    segTree[node] = segTree[lson] + segTree[rson];
}

void pushDown(int node, int le, int ri) {   // pushDown自顶向下更新lazy数组和给结点加上lazy数组的值 
    if (!lazy[node]) return;    // lazy[node]为0直接return 
    segTree[lson] += (mid-le+1) * lazy[node];
    segTree[rson] += (ri-mid) * lazy[node]; 
    lazy[lson] += lazy[node]; 
    lazy[rson] += lazy[node];   // 给左右孩子传递lazy,是+=不是=，因为孩子节点可能被多次延迟标记又没有向下传递 
    lazy[node] = 0; // 把父节点的lazy置为0 
}

void update(int node, int le, int ri, int pos, int k) { // 单点更新 
    if (pos < le || pos > ri) return;   // 管理的区间不包含pos,直接return 
    if (le == ri) segTree[node] += k;
    else {
        update(lson, le, mid, pos, k);
        update(rson, mid+1, ri, pos, k);
        pushUp(node);
    }
}

void update(int node, int le, int ri, int start, int end, int k){  // 区间更新 
    if (start > ri || end < le) return;    // 结点和更新区间没有公共点 
    if (start <= le && end >= ri) {    // 更新区间包含结点 
        segTree[node] += (ri-le+1) * k;
        lazy[node] += k;
        return;
    }
    pushDown(node, le, ri);
    update(lson, le, mid, start, end, k);
    update(rson, mid+1, ri, start, end, k);
    pushUp(node);
}

int query(int node, int le, int ri, int start, int end) {  //区间查询 
    if (start > ri || end < le) return 0;  // 查询结点和区间没有公共点 
    if (start <= le && end >= ri) return segTree[node];    // 查询区间包含查询结点 
    
    pushDown(node, le, ri);
    int res = 0;
    res += query(lson, le, mid, start, end);
    res += query(rson, mid+1, ri, start, end);
    return res;
}