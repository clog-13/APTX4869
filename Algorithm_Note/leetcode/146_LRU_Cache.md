# 146. LRU Cache

Design a data structure that follows the constraints of a **[Least Recently Used (LRU) cache](https://en.wikipedia.org/wiki/Cache_replacement_policies#LRU)**.

Implement the `LRUCache` class:

- `LRUCache(int capacity)` Initialize the LRU cache with **positive** size `capacity`.
- `int get(int key)` Return the value of the `key` if the key exists, otherwise return `-1`.
- `void put(int key, int value)` Update the value of the `key` if the `key` exists. Otherwise, add the `key-value` pair to the cache. If the number of keys exceeds the `capacity` from this operation, **evict** the least recently used key.

The functions `get` and `put` must each run in `O(1)` average time complexity.

 

**Example 1:**

```
Input
["LRUCache", "put", "put", "get", "put", "get", "put", "get", "get", "get"]
[[2], [1, 1], [2, 2], [1], [3, 3], [2], [4, 4], [1], [3], [4]]
Output
[null, null, null, 1, null, -1, null, -1, 3, 4]

Explanation
LRUCache lRUCache = new LRUCache(2);
lRUCache.put(1, 1); // cache is {1=1}
lRUCache.put(2, 2); // cache is {1=1, 2=2}
lRUCache.get(1);    // return 1
lRUCache.put(3, 3); // LRU key was 2, evicts key 2, cache is {1=1, 3=3}
lRUCache.get(2);    // returns -1 (not found)
lRUCache.put(4, 4); // LRU key was 1, evicts key 1, cache is {4=4, 3=3}
lRUCache.get(1);    // return -1 (not found)
lRUCache.get(3);    // return 3
lRUCache.get(4);    // return 4
```



## Go

```go
package main

import (
	"container/list"
	"runtime/debug"
)

func init() { debug.SetGCPercent(-1) } // !!!

type lruEntry struct {
	key, value int
}

type LRUCache struct {
	capacity int
	cache    map[int]*list.Element
	lst      *list.List
}

func Constructor(capacity int) LRUCache {
	return LRUCache{capacity, map[int]*list.Element{}, list.New()}
}

func (this *LRUCache) Get(key int) int {
	node := this.cache[key]
	if node == nil {
		return -1
	}
	this.lst.MoveToFront(node) // flush cache
	return node.Value.(lruEntry).value
}

func (this *LRUCache) Put(key, value int) {
	if node := this.cache[key]; node != nil {
		node.Value = lruEntry{key, value}
		this.lst.MoveToFront(node) // flush cache
		return
	}
	this.cache[key] = this.lst.PushFront(lruEntry{key, value})
	if len(this.cache) > this.capacity {
		delete(this.cache, this.lst.Remove(this.lst.Back()).(lruEntry).key)
	}
}
```

```go
package main

import "runtime/debug"

func init() { debug.SetGCPercent(-1) }  // !!!

type LRUCache struct {
	head     *Node
	tail     *Node
	cache    map[int]*Node
	capacity int
}

type Node struct {
	prev *Node
	next *Node
	key  int
	val  int
}

func Constructor(capacity int) LRUCache {
	return LRUCache{
		head:     nil,
		tail:     nil,
		cache:    make(map[int]*Node, capacity),
		capacity: capacity,
	}
}

func (this *LRUCache) Get(key int) int {
	node, ok := this.cache[key]
	if !ok {
		return -1
	}
	if this.tail == node {  // !!!
		return node.val
	}
	this.move2Tail(node)
	return node.val
}

func (this *LRUCache) Put(key int, value int) {
	node, ok := this.cache[key]
	if ok {
		node.val = value
		if this.tail == node {
			return
		}
		this.move2Tail(node)
		return
	}
	node = &Node{
		prev: nil,
		next: nil,
		key:  key,
		val:  value,
	}
	if this.capacity > len(this.cache) {
		if this.tail == nil || this.head == nil {
			this.tail = node
			this.head = node
		} else {
			this.PutTail(node)
		}
		this.cache[key] = node
		return
	}
    // capacity == len(cache)
	delete(this.cache, this.head.key)
	this.cache[key] = node
	if this.head == this.tail {  // len(cache) == 1
		this.head, this.tail = node, node
		return
	}
	this.head = this.head.next  // len(cache) > 1
	this.head.prev, this.head.prev.next = nil, nil
	this.cache[key] = node
	this.PutTail(node)
}

func (this *LRUCache) move2Tail(node *Node) {
	if this.head == node {
		this.head = node.next
		this.head.prev = nil
	} else {
		node.prev.next = node.next
		node.next.prev = node.prev
	}
	node.next = nil
	this.PutTail(node)
}

func (this *LRUCache) PutTail(node *Node) {
	this.tail.next = node
	node.prev = this.tail
	this.tail = node
}
```



## Java

```java
class LRUCache {
    private static final class Node {
        private final int key;
        private int val;
        private Node prev, next;

        public Node(int key, int val) {
            this.key = key;
            this.val = val;
        }
    }

    private final Node dummy = new Node(0, 0);
    private final int capacity;
    private int size;
    private final Node[] nodeMap = new Node[10001];

    public LRUCache(int capacity) {
        this.capacity = capacity;
        dummy.next = dummy.prev = dummy;
    }

    public int get(int key) {
        Node node = nodeMap[key];
        if (node == null) {
            return -1;
        }
        deleteNode(node);
        insertTail(node);
        return node.val; //******
    }

    public void put(int key, int value) {
        Node node = nodeMap[key];
        if (node != null) {
            deleteNode(node);
            insertTail(node);
            node.val = value;
        } else {
            if (size == capacity) {
                Node head = dummy.next;
                size--;
                deleteNode(head);
                nodeMap[head.key] = null;
            }
            size++;
            insertTail(nodeMap[key] = new Node(key, value));
        }
    }

    private void deleteNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
        node.next = node.prev = null;  // !!!
    }

    private void insertTail(Node node) {
        Node tail = dummy.prev;
        tail.next = node;
        node.prev = tail;
        node.next = dummy;
        dummy.prev = node;
    }
}
```

```java
public class LRUCache {
    HashMap<Integer, Node> map;
    DoubleLinkedList cache;
    int cap;
    public LRUCache(int capacity) {
        map = new HashMap<>();
        cache = new DoubleLinkedList();
        cap = capacity;
    }
    
    public void put(int key, int val) {
        Node newNode = new Node(key, val);
        if (map.containsKey(key)) { 
            cache.delete(map.get(key));
            cache.addFirst(newNode);
            map.put(key, newNode);
        } else {
            if (map.size() == cap) {
                int k = cache.deleteLast();
                map.remove(k);
            }
            cache.addFirst(newNode);
            map.put(key, newNode);
        }
    }
    
    public int get(int key) {
        if (!map.containsKey(key)) return -1;
        int val = map.get(key).val;
        put(key, val);
        return val;
    }
}

class DoubleLinkedList {
    Node head, tail;

    public DoubleLinkedList() {
        head = new Node(0,0);
        tail = new Node(0,0);
        head.next = tail;
        tail.prev = head;
    }

    void addFirst(Node node) {
        node.prev = head;
        head.next.prev = node;
        node.next = head.next;
        head.next = node;
    }

    int delete(Node n) {
        int key = n.key;
        n.next.prev = n.prev;
        n.prev.next = n.next;
        return key;
    }

    int deleteLast() {
        if (head.next == tail) return -1;
        return delete(tail.prev);
    }
}

class Node {
    int key, val;
    Node next, prev;

    public Node(int key, int val) {
        this.key = key;
        this.val = val;
    }
}
```

