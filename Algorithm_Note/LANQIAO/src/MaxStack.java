class MaxStack {
    Node head;

    public MaxStack() { }

    void push(int x) {
        if (head == null) {
            head = new Node(x, x);
        } else {
            head.next = new Node(x, Math.max(x, head.max), null, head);
            head = head.next;
        }
    }

    int pop() {
        int res = head.val;
        if (head.prev != null) {
            head = head.prev;
            head.next.prev = null;
            head.next = null;
        } else {
            head = null;
        }
        return res;
    }

    int top() {
        return head.val;
    }

    int peekMax() {
        return head.max;
    }

    int popMax() {
        Node key = head;
        while (key.val < key.max && key.prev != null)
            key = key.prev;
        int res = key.val;

        if (key.next == null)
            return pop();

        if (key.prev == null) {
            Node drop = key;
            key = key.next;
            key.max = key.val;
            drop.next.prev = null;
            drop.next = null;
            while (key.next != null) {
                key.next.max = Math.max(key.next.val, key.max);
                key = key.next;
            }
            return res;
        }

        Node drop = key;
        key = key.next;
        key.max = Math.max(drop.prev.max, key.val);
        drop.next.prev = drop.prev;
        drop.prev.next = drop.next;
        drop.prev = null;
        drop.next = null;
        while (key.next != null) {
            key.next.max = Math.max(key.next.val, key.max);
            key = key.next;
        }
        return res;
    }

    private class Node {
        int val;
        int max;
        Node next;
        Node prev;
        private Node(int val, int max) {
            this.val = val;
            this.max = max;
        }
        private Node(int val, int max, Node next, Node prev) {
            this.val = val;
            this.max = max;
            this.next = next;
            this.prev = prev;
        }
    }
}