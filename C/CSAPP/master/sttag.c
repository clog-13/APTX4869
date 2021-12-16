#include <stdio.h>

typedef struct LNode{
    int data;
    struct LNode *next;
}LNode, *LinkedList;

void main() {
    LinkedList list = (LinkedList)malloc(sizeof(LNode));
    list->data = 12;
    (*list).data = 23;
    printf("%d\n", list->data);
    
    LNode *nd = (LNode *)malloc(sizeof(LNode));
    nd->data = 34;
    (*nd).data = 43;
    printf("%d\n", nd->data);

    list->next = nd;

    // ERROR
    // LNode d = (LNode)malloc(sizeof(LNode));
    // d.data = 32;
    // printf("%d", d.data);
}